const express = require('express');
const transactionRoutes = require('./routes/transactionRoutes');

const app = express();

app.use(express.json());
app.use('/', transactionRoutes);
app.use((req, res) => res.status(404).json({ message: 'Not found' }));

module.exports = app;
