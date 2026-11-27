# Finance Transaction Analytics API (Node.js / Express)

## Overview
A dataset of financial transactions recorded across several cities is already loaded into this application
(`data/transactions.js`). Each transaction has a `transactionId`, a `userId`, a `city`, a `type` (`DEBIT` or `CREDIT`) and an
`amount`. No database is needed.

Write **three REST APIs** that analyse these transactions. The routes are already wired up; the code behind them
isn't written yet, so every integrated test fails. Implement it until all the tests pass.

## API 1: totals for a city
`GET /transactions/location/{city}`

The total amount of the `DEBIT` transactions and the total amount of the `CREDIT` transactions in that city.

```
GET /transactions/location/Ahmedabad
200 {"totalDebit": 500, "totalCredit": 1200}
```

## API 2: totals for one user in a city
`GET /transaction/{id}/location/{city}`

The same two totals, for the transactions of user `id` in that city only.

```
GET /transaction/1/location/Ahmedabad
200 {"totalDebit": 500, "totalCredit": 200}
```

## API 3: totals for every user in a city, saved to a file
`POST /transactions/location/{city}` (no request body)

One summary object per user who has transactions in the city, sorted by `userId`. Each object has `userId`,
`totalDebit` and `totalCredit` (0 when the user has none of that type).

```
POST /transactions/location/Ahmedabad
200 [{"userId": 1, "totalDebit": 500, "totalCredit": 200},
     {"userId": 2, "totalDebit": 0, "totalCredit": 1000}]
```

The response is also **saved as a JSON file** on the server: `output/{city}.json` in the project folder
(create the folder if it's missing; a later request for the same city replaces the file).

## Rules
- Return HTTP 200 on success.
- When no transaction matches (an unknown city, or a user with no transactions in that city), all three APIs
  return **HTTP 404** with `{"message": "No transactions found"}`. API 3 then saves no file.
- City names match exactly, letter case included. The user id in the path is a number.
- Although API 3 is a POST, it takes no request body; implement the method exactly as specified.

## Getting started
```
npm install          # already done in this environment
npm test             # run the tests
npm start            # http://localhost:5000
```

## Project structure
```
app.js, server.js
data/transactions.js                 the dataset
routes/transactionRoutes.js          the three routes
controllers/transactionController.js the three APIs (write them here)
utils/report.js                      saving a response as a JSON file
tests/                               the integrated tests (read-only)
```

## Additional Information
- In the real assessment the 404 had an exact error message that wasn't reported, and neither was the file name;
  the ones above are this mock's.
- Use the dataset as it is; the tests are written against it.
