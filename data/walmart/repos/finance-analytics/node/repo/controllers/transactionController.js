const transactions = require('../data/transactions');
const { saveReport } = require('../utils/report');

const NOT_FOUND = { message: 'No transactions found' };

// Total DEBIT and total CREDIT amounts of a list of transactions.
const totals = (list) => ({
  // TODO: add up the DEBIT amounts and the CREDIT amounts of `list`
  totalDebit: 0,
  totalCredit: 0,
});

// API 1. GET /transactions/location/:city
const getCityTotals = (req, res) => {
  // TODO: 200 with the city's totals, or 404 with NOT_FOUND
  res.status(501).json({ message: 'Not implemented' });
};

// API 2. GET /transaction/:id/location/:city
const getUserCityTotals = (req, res) => {
  // TODO: 200 with the totals of user :id in :city, or 404 with NOT_FOUND
  res.status(501).json({ message: 'Not implemented' });
};

// API 3. POST /transactions/location/:city
const postCityUserSummary = (req, res) => {
  // TODO: one { userId, totalDebit, totalCredit } per user in :city, sorted by userId; save it with saveReport
  //       and return it, or 404 with NOT_FOUND
  res.status(501).json({ message: 'Not implemented' });
};

module.exports = { getCityTotals, getUserCityTotals, postCityUserSummary };
