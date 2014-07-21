#!/bin/bash 
         COUNTER=0
         date1=$(date +"%s")
         while [  $COUNTER -lt 1 ]; do
         randomName=$(tr -dc "[:alpha:]" < /dev/urandom | head -c 8)
curl -H "Content-Type: application/json" -d '{"name":"'"$randomName"'"}' http://localhost:8080/enroll-rest-1.0-SNAPSHOT/api/public/enroll           
             let COUNTER=COUNTER+1 
         done
         date2=$(date +"%s")
         diff=$(($date2-$date1))
		echo "$(($diff / 60)) minutes and $(($diff % 60)) seconds elapsed."
