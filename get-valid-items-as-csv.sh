#!/bin/bash
curl -X GET 'http://localhost:8080/password-rest-module/rest/valid-passwords' -H 'Content-Type: application/json' -H 'Accept: text/csv'
