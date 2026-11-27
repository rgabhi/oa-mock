from rest_framework.decorators import api_view
from rest_framework.response import Response

from .data import TRANSACTIONS
from .report import save_report

NOT_FOUND = {"message": "No transactions found"}


def totals(items):
    """Total DEBIT and total CREDIT amounts of a list of transactions."""
    return {
        "totalDebit": sum(t["amount"] for t in items if t["type"] == "DEBIT"),
        "totalCredit": sum(t["amount"] for t in items if t["type"] == "CREDIT"),
    }


def city_totals(city):
    """API 1. GET /transactions/location/<city>"""
    in_city = [t for t in TRANSACTIONS if t["city"] == city]
    if not in_city:
        return Response(NOT_FOUND, status=404)
    return Response(totals(in_city), status=200)


def city_user_summary(city):
    """API 3. POST /transactions/location/<city>"""
    in_city = [t for t in TRANSACTIONS if t["city"] == city]
    if not in_city:
        return Response(NOT_FOUND, status=404)
    summary = [dict({"userId": user_id}, **totals([t for t in in_city if t["userId"] == user_id]))
               for user_id in sorted({t["userId"] for t in in_city})]
    save_report(city, summary)
    return Response(summary, status=200)


@api_view(["GET", "POST"])
def city(request, city):
    """/transactions/location/<city>: GET is API 1, POST is API 3."""
    if request.method == "POST":
        return city_user_summary(city)
    return city_totals(city)


@api_view(["GET"])
def user_city_totals(request, user_id, city):
    """API 2. GET /transaction/<id>/location/<city>"""
    matching = [t for t in TRANSACTIONS if user_id.isdigit() and t["userId"] == int(user_id) and t["city"] == city]
    if not matching:
        return Response(NOT_FOUND, status=404)
    return Response(totals(matching), status=200)
