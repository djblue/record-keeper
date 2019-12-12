# record-keeper

[![Build Status](https://travis-ci.org/djblue/record-keeper.svg?branch=master)](https://travis-ci.org/djblue/record-keeper)

## Assumptions

- No records are duplicated.
- No record fields need to be escaped.

## CLI

To run the cli, do:

    clojure -A:cli <gender|birthdate|name> [...files]

For example, at the root of this project, do:

    clojure -A:cli gender resources/example.comma resources/example.pipe resources/example.space

## test

    clojure -A:test
