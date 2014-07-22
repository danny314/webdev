This is a simple REST service using JEE7. It accepts and returns JSON. gson library is used for serialization and deserialization of Java into JSON and vice versa. This service has been tested on Glassfish 4. It does not work on Widlfly 8.1. It requires a little tweaking in web.xml.

To 'POST' data using JSON, curl can be used in the following way -
curl -H "Content-Type: application/json" -d '{"message":"Some message"}' http://localhost:8080/enroll-rest-1.0-SNAPSHOT/helloworld/enroll

To 'GET' data in JSON format, call the URL-
http://localhost:8080/enroll-rest-1.0-SNAPSHOT/helloworld?username=John

