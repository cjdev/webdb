#!/usr/bin/env bash

set -e
set -x

task_page_id=$(curl -X POST -d '{ "name":"page"    , "complete":false }' http://localhost:8080/task)
task_styling_id=$(curl -X POST -d '{ "name":"styling" , "complete":false }' http://localhost:8080/task)
task_database_id=$(curl -X POST -d '{ "name":"database", "complete":false }' http://localhost:8080/task)
task_api_id=$(curl -X POST -d '{ "name":"api"     , "complete":false }' http://localhost:8080/task)
task_bug_id=$(curl -X POST -d '{ "name":"bug"     , "complete":false }' http://localhost:8080/task)

curl -X DELETE "http://localhost:8080/task/${task_database_id}"
curl -X POST -d '{ "complete":true }' "http://localhost:8080/task/${task_bug_id}"
curl "http://localhost:8080/task/${task_api_id}"
curl http://localhost:8080/task
curl http://localhost:8080
