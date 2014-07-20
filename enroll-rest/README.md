Tnis is a simple REST service using JEE7.
To 'POST' data using JSON, curl can be used in the following way -
curl -H "Content-Type: application/json" -d '{"message":"Some message"}' http://localhost:8080/enroll-rest-1.0-SNAPSHOT/helloworld/enroll

To 'GET' data in JSON format, call the URL-
http://localhost:8080/enroll-rest-1.0-SNAPSHOT/helloworld?username=John

