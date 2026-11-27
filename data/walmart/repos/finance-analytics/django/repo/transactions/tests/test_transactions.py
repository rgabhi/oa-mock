"""The integrated tests for the finance API. Run them with:  python manage.py test"""
from .base import NOT_FOUND, FinanceTestCase


class TransactionTests(FinanceTestCase):
    def test_returns_the_debit_and_credit_totals_of_a_city(self):
        res = self.city_totals("Ahmedabad")
        self.assertEqual(res.status_code, 200, res.content)
        self.assertEqual(res.json(), {"totalDebit": 500, "totalCredit": 1200})
        self.assertEqual(self.city_totals("Mumbai").json(), {"totalDebit": 1250, "totalCredit": 1500})

    def test_returns_the_totals_of_one_user_in_a_city(self):
        res = self.user_totals(1, "Ahmedabad")
        self.assertEqual(res.status_code, 200, res.content)
        self.assertEqual(res.json(), {"totalDebit": 500, "totalCredit": 200})
        self.assertEqual(self.user_totals(1, "Mumbai").json(), {"totalDebit": 300, "totalCredit": 0})

    def test_summarises_every_user_in_a_city(self):
        res = self.summary("Mumbai")
        self.assertEqual(res.status_code, 200, res.content)
        self.assertEqual(res.json(), [{"userId": 1, "totalDebit": 300, "totalCredit": 0},
                                      {"userId": 3, "totalDebit": 700, "totalCredit": 1500},
                                      {"userId": 4, "totalDebit": 250, "totalCredit": 0}])

    def test_saves_the_summary_to_a_JSON_file(self):
        res = self.summary("Delhi")
        self.assertEqual(res.status_code, 200, res.content)
        self.assertEqual(self.saved_report("Delhi"), [{"userId": 5, "totalDebit": 0, "totalCredit": 2450},
                                                      {"userId": 6, "totalDebit": 800, "totalCredit": 0}])
        self.assertEqual(self.saved_report("Delhi"), res.json())

    def test_unknown_cities_and_users_get_a_404(self):
        city = self.city_totals("Chennai")
        self.assertEqual((city.status_code, city.json()), (404, NOT_FOUND))
        self.assertEqual(self.user_totals(9, "Ahmedabad").status_code, 404)
        elsewhere = self.user_totals(3, "Ahmedabad")          # user 3 only has transactions in Mumbai
        self.assertEqual((elsewhere.status_code, elsewhere.json()), (404, NOT_FOUND))
        post = self.summary("Chennai")
        self.assertEqual((post.status_code, post.json()), (404, NOT_FOUND))
