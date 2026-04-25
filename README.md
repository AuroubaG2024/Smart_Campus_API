# Smart Campus API Coursework

This is a project for the 5COSC022W.2 Client-Server Architectures coursework at Univeristy of Westminster

## Overview
The Smart Campus API is a RESTful web service developed with JAX-RS (Java EE 8) and Apache Tomcat. It provides a full architecture for managing campus rooms and sensors, as well as nested resources for capturing sensor readings. The API uses RESTful design principles such as resource hierarchies, meaningful HTTP status codes, and versioning via /api/v1. Logging is part of the structure.

### Resource Hierarchy
/api/v1
/rooms
/sensors
/sensors/{sensorId}/readings

## How to Build and Run

### Prerequisites
-Apache NetBeans IDE
-Apache Tomcat 9
-Maven
-Java 8 (or newer)

### Steps
1) Clone the repository:
git clone https://github.com/AuroubaG2024/Smart_Campus_API.git
2) Open the folder in Apache NetBeans
3) Right-click the project → Clean and Build
4) Right-click the project → Run
5) The server will start on:
http://localhost:8080/SmartCampusAPI/api/v1

## Five Sample Curl Commands
### Get all rooms
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/rooms

### Create a new room
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/rooms \ -H "Content-Type: application/json" \
-d '{
"id":"room2",
"name":"Lab 1",
"capacity":30
}'

#### Response (201 Created)
{
"id":"room2",
"name":"Lab 1",
"capacity":30,
"sensorIds":[]
}


### Get sensors filtered by type
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/sensors?type=temperature

### Response (200 OK)
[ { "id":"sensor1", "type":"temperature", "status":"ACTIVE", "currentValue":22.5, "roomId":"room1", } ]

### Register a new sensor
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{
"id":"sensor3",
"type":"humidity",
"status":"ACTIVE",
"roomId":"room2"
}'

#### Response (201 Created)
{
"id":"sensor3",
"type":"humidity",
"status":"ACTIVE",
"roomId":"room2"
}

### Post a reading to a sensor
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors/sensor3/readings \
-H "Content-Type: application/json" \
-d '{
"value":48.5
}'

#### Response (201 Created)
{
"id":"reading1",
"sensorId":"sensor3",
"value":48.5,
"timestamp":"2026-01-10T14:22:31Z"
}

## Part 1: Service Architecture & Setup (10 Marks)
### 1. Project & Application Configuration (5 Marks):
####  Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.
The API is configured using JAX-RS for the path of: /api/v1
JAX-RS resource classes have a per-request lifespan by default. As every incoming HTTP request creates a new resource instance. As a result, shared data should not be stored in instance variables inside resource classes. Static HashMap collections are used in this project's DataStore to store shared data. This maintains data accessibility while the program is operating. However, in order to prevent race situations when several clients request the API simultaneously, thread-safe collections or synchronisation would be required in a real system.

### 2. The ”Discovery” Endpoint (5 Marks):
#### Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?
Hypermedia is considered a hallmark of advanced RESTful design (HATEOAS) as API responses includes linked to related source. By supporting (HAETEOAS) clients can use the API response to find accessible actions rather than depending just on static documentation. The discovery endpoint in this project makes it easier for client developers to traverse the API by returning links to /rooms and /sensors.

## Part 2: Room Management (20 Marks)
### 1.Room Resource Implementation (10 Marks): 
#### When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.
Returning only IDs will require less network bandwidth as clients would need to make further enquiries to get the complete information about each room. But returning full room objects where the client would gain more useful information about the room by the room name, capacity and the assigned sensors. Due to the dataset being small, the API is easier to test and understand it.

### Room Deletion & Safety Logic (10 Marks):
#### 2.Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.
The DELETE operation is idempotent in my implementation as if a room is deleted once, it allows to send the same DELETE request again by not changing the system as the room would no longer exist. For example, if the request has sensor, the DELETE request is constantly denied with a conflict response. Avoiding singular sensor data and the final state to be consistent.

## Part 3: Sensor Operations & Linking (20 Marks)
### 1.Sensor Resource & Integrity (10 Marks):
####  We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

JAX-RS may reject a request if a client submits it in a different format, like text/plain or application/xml, since it is unable to transform the body into the specified Java object. This generally leads to a bad request response or an error like 415 Unsupported Media Type. By doing this, the API is shielded from the input formats that are not supported.

### 2.Filtered Retrieval & Search (10 Marks):
#### You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

Since the client is still requesting the collection of sensors, but with an optional criteria applied, it is preferable to use a query parameter like /sensors?type=CO2 for filtering. Although the filter is only a search condition, a path like /sensors/type/CO2 makes it appear to be a part of the resource hierarchy. Because other filters, such status or room ID, can be added later, query parameters are more adaptable.

## Part 4: Deep Nesting with Sub - Resources (20 Marks)
### 1. The Sub-Resource Locator Pattern (10 Marks):
#### Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?

The Sub-Resource Locator pattern helps to organise nested resoruces. This API's /sensors/{sensorId}/readings displays the relationship between readings and a particular sensor. The sub-resource locator pattern facilitates the unambiguous organization of nested resources. This API's /sensors/{sensorId}/readings displays the relationship between readings and a particular sensor. SensorResource is kept smaller and simpler to manage by assigning reading logic to SensorReadingResource. This is better than placing every nested endpoint inside one large controller class.

### 2. Historical Data Management (10 Marks):
#### Within SensorReadingResource, implement GET / to fetch history and POST / to append new readings for that specific sensor context.

Code implementation in the program within the SensorReadingResource Java file.

## Part 5: Advanced Error Handling, Exception Mapping & Logging (30 Marks)
### 1. Resource Conflict (409) (5 Marks):
#### Create a custom RoomNotEmptyException. Implement an Exception Mapper that returns an HTTP 409 Conflict with a JSON body explaining that the room is currently occupied by active hardware.

Code implementation in the program.

### 2. Dependency Validation (422 Unprocessable Entity) (10 Marks):
#### Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

HTTP 404 usually indicates that the requested URL does not exist. However, even if the client makes an accurate JSON request with an invalid roomId, the URL remains valid. HTTP 422 is more accurate since the server recognises the request but is unable to process it due to an invalid linked resource.

### 3. State Constraint (403 Forbidden) (5 Marks):
#### Create a SensorUnavailableException. Map this to an HTTP 403 Forbidden status when a POST reading is attempted.

Code implmentation in the program.

### 4. The Global Safety Net (500) (5 Marks):
#### From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

Internal Java stack traces should not be exposed since they could reveal class names, package names, framework details, server paths, and internal logic.
This information could be used by an attacker to target system flaws. The global exception mapper avoids this by returning a generic error message rather than providing technical information.


### 5. API Request & Response Logging Filters (5 Marks):
#### Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?

It is advantageous to use JAX-RS filters for cross-cutting concerns as JAX-RS filters are beneficial for logging because they are automatically applied to all requests and responses. This prevents repeating logging statements within each resource method. Using a filter keeps resource classes cleaner and ensures consistent reporting across the entire API.

## POSTMAN TESTS requests
Postman Tests
For the coursework

﻿GET 
http://localhost:8080/SmartCampusAPI/api/v1

GET
http://localhost:8080/SmartCampusAPI/api/v1/rooms

POST
http://localhost:8080/SmartCampusAPI/api/v1/rooms﻿

Body
raw (json)
json
{
  "id": "room1",
  "name": "Computer Lab",
  "capacity": 40,
  "sensorIds": []
}

GET
http://localhost:8080/SmartCampusAPI/api/v1/rooms/room1﻿

POST
http://localhost:8080/SmartCampusAPI/api/v1/sensors﻿

Body
raw (json)
json
{
  "id": "sensor1",
  "type": "CO2",
  "status": "ACTIVE",
  "currentValue": 400.0,
  "roomId": "room1"
}

GET
http://localhost:8080/SmartCampusAPI/api/v1/sensors

GET
http://localhost:8080/SmartCampusAPI/api/v1/sensors

GET 
http://localhost:8080/SmartCampusAPI/api/v1/sensors?type=CO2

Query Params
type
CO2

GET
http://localhost:8080/SmartCampusAPI/api/v1/sensors/sensor1/readings
﻿
POST
http://localhost:8080/SmartCampusAPI/api/v1/sensors/sensor1/readings


Body
raw (json)
json
{
  "value": 410.2
}

GET
http://localhost:8080/SmartCampusAPI/api/v1/sensors/sensor1/readings

GET
http://localhost:8080/SmartCampusAPI/api/v1/sensors

GET
http://localhost:8080/SmartCampusAPI/api/v1



﻿

GET
http://localhost:8080/SmartCampusAPI/api/v1


