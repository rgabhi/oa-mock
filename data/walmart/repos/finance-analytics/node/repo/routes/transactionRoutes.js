const express = require('express');
const c = require('../controllers/transactionController');

const router = express.Router();

router.get('/transactions/location/:city', c.getCityTotals);
router.get('/transaction/:id/location/:city', c.getUserCityTotals);
router.post('/transactions/location/:city', c.postCityUserSummary);

module.exports = router;
