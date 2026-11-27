from rest_framework.decorators import api_view
from rest_framework.response import Response

from .data import TRANSACTIONS
from .report import save_report

NOT_FOUND = {"message": "No transactions found"}


def totals(items):
    """Total DEBIT and total CREDIT amounts of a list of transactions."""
    # TODO: add up the DEBIT amounts and the CREDIT amounts of `items`
    return {"totalDebit": 0, "totalCredit": 0}


def city_totals(city):
    """API 1. GET /transactions/location/<city>"""
    # TODO: 200 with the city's totals, or 404 with NOT_FOUND
    return Response({"message": "Not implemented"}, status=501)


def city_user_summary(city):
    """API 3. POST /transactions/location/<city>"""
    # TODO: one {userId, totalDebit, totalCredit} per user in <city>, sorted by userId;
    #       save it with save_report and return it, or 404 with NOT_FOUND
    return Response({"message": "Not implemented"}, status=501)


@api_view(["GET", "POST"])
def city(request, city):
    """/transactions/location/<city>: GET is API 1, POST is API 3."""
    if request.method == "POST":
        return city_user_summary(city)
    return city_totals(city)


@api_view(["GET"])
def user_city_totals(request, user_id, city):
    """API 2. GET /transaction/<id>/location/<city>"""
    # TODO: 200 with the totals of user <user_id> in <city>, or 404 with NOT_FOUND
    return Response({"message": "Not implemented"}, status=501)
