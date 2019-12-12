# record-keeper

[![Build Status](https://travis-ci.org/djblue/record-keeper.svg?branch=master)](https://travis-ci.org/djblue/record-keeper)

## Assumptions

- No records are duplicated.
- No record fields need to be escaped.
- Assume birthdays are always formatted MM/DD/YYYY

## CLI

To run the cli, do:

    clojure -A:cli <gender|birthdate|name> [...files]

For example, at the root of this project, do:

    clojure -A:cli gender resources/example.comma resources/example.pipe resources/example.space

## REST API

Alongside the CLI is a JSON REST API with the following methods:

- POST /records - Post a single data line in any of the 3 formats
  supported by your existing code
- [GET /records/gender](http://localhost:3000/records/gender) - returns
  records sorted by gender
- [GET /records/birthdate](http://localhost:3000/records/birthdate) -
  returns records sorted by birthdate
- [GET /records/name](http://localhost:3000/records/name) - returns
  records sorted by name

To run the server, do:

    clojure -A:server

To post data to the API, do:

    curl --data-binary @path/to/file -X POST http://localhost:3000/records

For example, at the root of this project, do:

    curl --data-binary @resources/example.comma -X POST http://localhost:3000/records

## test

To run project tests, do:

    clojure -A:test
