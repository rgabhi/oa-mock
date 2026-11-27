"""Shared helpers for the finance API tests. The dataset is transactions/data.py; each test starts with no saved
reports."""
import json
import shutil
from urllib.parse import quote

from django.test import SimpleTestCase

from transactions.report import OUTPUT_DIR

NOT_FOUND = {"message": "No transactions found"}


class FinanceTestCase(SimpleTestCase):
    def setUp(self):
        shutil.rmtree(OUTPUT_DIR, ignore_errors=True)

    def tearDown(self):
        shutil.rmtree(OUTPUT_DIR, ignore_errors=True)

    def city_totals(self, city):
        return self.client.get("/transactions/location/%s" % quote(city))

    def user_totals(self, user_id, city):
        return self.client.get("/transaction/%s/location/%s" % (user_id, quote(city)))

    def summary(self, city):
        return self.client.post("/transactions/location/%s" % quote(city))

    def saved_report(self, city):
        path = OUTPUT_DIR / ("%s.json" % city)
        return json.loads(path.read_text(encoding="utf-8")) if path.exists() else None
