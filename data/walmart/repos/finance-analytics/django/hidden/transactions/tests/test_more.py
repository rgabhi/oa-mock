"""More integrated tests (hidden in the test: they run when you submit)."""
from .base import FinanceTestCase


class MoreTransactionTests(FinanceTestCase):
    def test_city_names_match_exactly(self):
        self.assertEqual(self.city_totals("ahmedabad").status_code, 404)
        self.assertEqual(self.city_totals("Bengaluru").json(), {"totalDebit": 1250, "totalCredit": 5000})
        self.assertEqual(self.city_totals("Pune").json(), {"totalDebit": 200, "totalCredit": 0})

    def test_the_user_id_in_the_path_is_a_number(self):
        self.assertEqual(self.user_totals("abc", "Pune").status_code, 404)
        self.assertEqual(self.user_totals(2, "Pune").json(), {"totalDebit": 200, "totalCredit": 0})
        self.assertEqual(self.user_totals(5, "Delhi").json(), {"totalDebit": 0, "totalCredit": 2450})

    def test_the_example_from_the_task_Ahmedabad(self):
        res = self.summary("Ahmedabad")
        self.assertEqual(res.json(), [{"userId": 1, "totalDebit": 500, "totalCredit": 200},
                                      {"userId": 2, "totalDebit": 0, "totalCredit": 1000}])
        self.assertEqual(self.saved_report("Ahmedabad"), res.json())

    def test_a_404_saves_no_file_and_a_city_gets_its_own_file(self):
        self.summary("Chennai")
        self.assertIsNone(self.saved_report("Chennai"))
        self.summary("Pune")
        self.summary("Bengaluru")
        self.assertEqual(self.saved_report("Pune"), [{"userId": 2, "totalDebit": 200, "totalCredit": 0}])
        self.assertEqual(self.saved_report("Bengaluru"), [{"userId": 7, "totalDebit": 1250, "totalCredit": 5000}])
