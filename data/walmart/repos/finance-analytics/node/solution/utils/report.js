const fs = require('fs');
const path = require('path');

const OUTPUT_DIR = path.join(__dirname, '..', 'output');

// Saves an API response as output/<city>.json (pretty-printed), replacing any earlier file for that city.
const saveReport = (city, data) => {
  fs.mkdirSync(OUTPUT_DIR, { recursive: true });
  fs.writeFileSync(path.join(OUTPUT_DIR, `${city}.json`), JSON.stringify(data, null, 2));
};

module.exports = { saveReport, OUTPUT_DIR };
