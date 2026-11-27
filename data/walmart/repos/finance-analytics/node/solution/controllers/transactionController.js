const transactions = require('../data/transactions');
const { saveReport } = require('../utils/report');

const NOT_FOUND = { message: 'No transactions found' };

// Total DEBIT and total CREDIT amounts of a list of transactions.
const totals = (list) => ({
  totalDebit: list.filter((t) => t.type === 'DEBIT').reduce((sum, t) => sum + t.amount, 0),
  totalCredit: list.filter((t) => t.type === 'CREDIT').reduce((sum, t) => sum + t.amount, 0),
});

// API 1. GET /transactions/location/:city
const getCityTotals = (req, res) => {
  const inCity = transactions.filter((t) => t.city === req.params.city);
  if (inCity.length === 0) {
    return res.status(404).json(NOT_FOUND);
  }
  res.status(200).json(totals(inCity));
};

// API 2. GET /transaction/:id/location/:city
const getUserCityTotals = (req, res) => {
  const userId = Number(req.params.id);
  const matching = transactions.filter((t) => t.userId === userId && t.city === req.params.city);
  if (matching.length === 0) {
    return res.status(404).json(NOT_FOUND);
  }
  res.status(200).json(totals(matching));
};

// API 3. POST /transactions/location/:city
const postCityUserSummary = (req, res) => {
  const { city } = req.params;
  const inCity = transactions.filter((t) => t.city === city);
  if (inCity.length === 0) {
    return res.status(404).json(NOT_FOUND);
  }
  const userIds = [...new Set(inCity.map((t) => t.userId))].sort((a, b) => a - b);
  const summary = userIds.map((userId) => ({ userId, ...totals(inCity.filter((t) => t.userId === userId)) }));
  saveReport(city, summary);
  res.status(200).json(summary);
};

module.exports = { getCityTotals, getUserCityTotals, postCityUserSummary };
