const fs = require('fs');
const path = require('path');

const OUTPUT_DIR = path.join(__dirname, '..', 'output');

// Saves an API response as output/<city>.json (pretty-printed), replacing any earlier file for that city.
const saveReport = (city, data) => {
  // TODO: write `data` as JSON to output/<city>.json (create the folder if it's missing)
};

module.exports = { saveReport, OUTPUT_DIR };
