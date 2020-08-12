This is Assignment project for Optus.
Spring boot restful APIs with spring security, JUnit and Swagger.

Swagger UI: http://localhost:8080/swagger-ui.html

Endpoints are:
curl http://localhost:8080/counter-api/top/20 -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -X GET -H"Accept: text/csv"
curl http://localhost:8080/counter-api/search -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -X POST -H "Content-Type: application/json" -d '{"searchText":["Duis", "Sed"]}'