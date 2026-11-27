const app = require('./app');

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => console.log(`Finance transaction analytics API running on port ${PORT}`));
