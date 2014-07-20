#!/bin/bash 
         COUNTER=0
         date1=$(date +"%s")
         while [  $COUNTER -lt 1000 ]; do
curl -H "Content-Type: application/json" -d '{"message":"Some message"}' http://localhost:8080/enroll-rest-1.0-SNAPSHOT/helloworld/enroll           
             let COUNTER=COUNTER+1 
         done
         date2=$(date +"%s")
         diff=$(($date2-$date1))
		echo "$(($diff / 60)) minutes and $(($diff % 60)) seconds elapsed."
