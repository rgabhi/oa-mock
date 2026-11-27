const { clean, savedReport, cityTotals, userTotals, summary } = require('./helpers');

const NOT_FOUND = { message: 'No transactions found' };

describe('TransactionTests', () => {
  beforeEach(clean);
  afterAll(clean);

  test('returns the debit and credit totals of a city', async () => {
    const res = await cityTotals('Ahmedabad');
    expect(res.status).toBe(200);
    expect(res.body).toEqual({ totalDebit: 500, totalCredit: 1200 });
    expect((await cityTotals('Mumbai')).body).toEqual({ totalDebit: 1250, totalCredit: 1500 });
  });

  test('returns the totals of one user in a city', async () => {
    const res = await userTotals(1, 'Ahmedabad');
    expect(res.status).toBe(200);
    expect(res.body).toEqual({ totalDebit: 500, totalCredit: 200 });
    expect((await userTotals(1, 'Mumbai')).body).toEqual({ totalDebit: 300, totalCredit: 0 });
  });

  test('summarises every user in a city', async () => {
    const res = await summary('Mumbai');
    expect(res.status).toBe(200);
    expect(res.body).toEqual([
      { userId: 1, totalDebit: 300, totalCredit: 0 },
      { userId: 3, totalDebit: 700, totalCredit: 1500 },
      { userId: 4, totalDebit: 250, totalCredit: 0 },
    ]);
  });

  test('saves the summary to a JSON file', async () => {
    const res = await summary('Delhi');
    expect(res.status).toBe(200);
    expect(savedReport('Delhi')).toEqual([
      { userId: 5, totalDebit: 0, totalCredit: 2450 },
      { userId: 6, totalDebit: 800, totalCredit: 0 },
    ]);
    expect(savedReport('Delhi')).toEqual(res.body);
  });

  test('unknown cities and users get a 404', async () => {
    const city = await cityTotals('Chennai');
    expect(city.status).toBe(404);
    expect(city.body).toEqual(NOT_FOUND);
    expect((await userTotals(9, 'Ahmedabad')).status).toBe(404);
    const elsewhere = await userTotals(3, 'Ahmedabad');      // user 3 only has transactions in Mumbai
    expect(elsewhere.status).toBe(404);
    expect(elsewhere.body).toEqual(NOT_FOUND);
    const post = await summary('Chennai');
    expect(post.status).toBe(404);
    expect(post.body).toEqual(NOT_FOUND);
  });
});
