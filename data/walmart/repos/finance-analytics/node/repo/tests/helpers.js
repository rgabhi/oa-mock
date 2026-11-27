// Shared helpers for the finance API tests. The dataset is data/transactions.js; each test starts with no saved
// reports.
const fs = require('fs');
const path = require('path');
const request = require('supertest');
const app = require('../app');

const OUTPUT_DIR = path.join(__dirname, '..', 'output');

const clean = () => fs.rmSync(OUTPUT_DIR, { recursive: true, force: true });
const savedReport = (city) => {
  const file = path.join(OUTPUT_DIR, `${city}.json`);
  return fs.existsSync(file) ? JSON.parse(fs.readFileSync(file, 'utf8')) : undefined;
};

const cityTotals = (city) => request(app).get(`/transactions/location/${encodeURIComponent(city)}`);
const userTotals = (id, city) => request(app).get(`/transaction/${id}/location/${encodeURIComponent(city)}`);
const summary = (city) => request(app).post(`/transactions/location/${encodeURIComponent(city)}`);

module.exports = { clean, savedReport, cityTotals, userTotals, summary };
