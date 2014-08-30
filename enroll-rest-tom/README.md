This is a simple REST service using Jersey on Tomcat 8.0.9. It accepts and returns JSON. This is the same service as in the project
enroll-rest but it has been modified to work on Tomcat instead of Glassfish.

To 'POST' data using JSON, curl can be used in the following way -
curl -H "Content-Type: application/json" -d '{"message":"Some message"}' http://localhost:8080/enroll-rest-tom-1.0-SNAPSHOT/rest/public/enroll

To 'GET' data in JSON format, call the URL-
http://localhost:8080/enroll-rest-tom-1.0-SNAPSHOT/rest/public?username=John

