# Web Database
In memory database for prototyping web applications

## Features
- display namespaces
    - GET /
- create entry
    - POST /(the-namespace-name)
    - body is a json object
    - returns the generated id as a string
- list entries
    - GET /(the-namespace-name)
    - returns a json list of all entries in the namespace
- get entry
    - GET /(the-namespace-name)/(the-entry-id)
    - returns the json object in the namespace with the id
- delete entry
    - DELETE /(the-namespace-name)/(the-entry-id)
    - deletes the entry with the namespace and id
- update entry
    - POST /(the-namespace-name)/(the-entry-id)
    - body is a json object
    - merges the body with the existing entry, leaves unspecified fields unchanged

## Scripts
- `./build.sh`
    - build the application
- `./run.sh`
    - run the application
- `./sample-data.sh`
    - create sample data for the application

## Verifying the application

Run the application with `./run.sh`, then, in another terminal, create sample data for the application with `./sample-data.sh`

This demonstrates creating, updating, deleting, reading, and listing entries in the 'task' namespace

Navigating to http://localhost:8080 will list the namespaces

```json
[ "task" ]
```

Navigating to http://localhost:8080/task will list the entries in the 'task' namespace

```json
[ {
  "name" : "page",
  "complete" : false,
  "id" : "task-1"
}, {
  "name" : "styling",
  "complete" : false,
  "id" : "task-2"
}, {
  "name" : "api",
  "complete" : false,
  "id" : "task-4"
}, {
  "name" : "bug",
  "complete" : true,
  "id" : "task-5"
} ]
```

Navigating to http://localhost:8080/task/task-4 will list the entry in the 'task' namespace with the id 'task-4'

```json
{
  "name" : "api",
  "complete" : false,
  "id" : "task-4"
}
```
