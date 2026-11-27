const { clean, savedReport, cityTotals, userTotals, summary } = require('./helpers');

describe('MoreTransactionTests', () => {
  beforeEach(clean);
  afterAll(clean);

  test('city names match exactly', async () => {
    expect((await cityTotals('ahmedabad')).status).toBe(404);
    expect((await cityTotals('Bengaluru')).body).toEqual({ totalDebit: 1250, totalCredit: 5000 });
    expect((await cityTotals('Pune')).body).toEqual({ totalDebit: 200, totalCredit: 0 });
  });

  test('the user id in the path is a number', async () => {
    expect((await userTotals('abc', 'Pune')).status).toBe(404);
    expect((await userTotals(2, 'Pune')).body).toEqual({ totalDebit: 200, totalCredit: 0 });
    expect((await userTotals(5, 'Delhi')).body).toEqual({ totalDebit: 0, totalCredit: 2450 });
  });

  test('the example from the task: Ahmedabad', async () => {
    const res = await summary('Ahmedabad');
    expect(res.body).toEqual([
      { userId: 1, totalDebit: 500, totalCredit: 200 },
      { userId: 2, totalDebit: 0, totalCredit: 1000 },
    ]);
    expect(savedReport('Ahmedabad')).toEqual(res.body);
  });

  test('a 404 saves no file, and a city gets its own file', async () => {
    await summary('Chennai');
    expect(savedReport('Chennai')).toBeUndefined();
    await summary('Pune');
    await summary('Bengaluru');
    expect(savedReport('Pune')).toEqual([{ userId: 2, totalDebit: 200, totalCredit: 0 }]);
    expect(savedReport('Bengaluru')).toEqual([{ userId: 7, totalDebit: 1250, totalCredit: 5000 }]);
  });
});
