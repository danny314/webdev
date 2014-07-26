#!/bin/bash 
         COUNTER=0
         date1=$(date +"%s")
         while [  $COUNTER -lt 10000 ]; do
curl -H "Content-Type: application/json" -d '{ 
								"name":"'"$(tr -dc "[:alpha:]" < /dev/urandom | head -c 8)"'" 
								,"email":"'"$(tr -dc "[:alpha:]" < /dev/urandom | head -c 8)"'" 
								,"username":"'"$(tr -dc "[:alpha:]" < /dev/urandom | head -c 8)"'" 
								,"gender":"F" 
								}' http://localhost:8080/enroll-rest-1.0-SNAPSHOT/api/public/enroll           
             let COUNTER=COUNTER+1 
         done
         date2=$(date +"%s")
         diff=$(($date2-$date1))
		echo "$(($diff / 60)) minutes and $(($diff % 60)) seconds elapsed."
