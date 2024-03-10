#!/bin/bash

# Receive locations
curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier001","latitude":40.9923400,"longitude":29.1244229,"time":"2024-03-10T12:00:00"}' http://localhost:8080/couriers/location
echo
curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier001","latitude":40.9923410,"longitude":29.1244229,"time":"2024-03-10T12:00:50"}' http://localhost:8080/couriers/location
echo
curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier001","latitude":40.9923420,"longitude":29.1244229,"time":"2024-03-10T12:01:02"}' http://localhost:8080/couriers/location
echo


curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier002","latitude":40.9632463,"longitude":29.0630908,"time":"2024-03-10T12:00:00"}' http://localhost:8080/couriers/location
echo
curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier002","latitude":40.9632473,"longitude":29.0630908,"time":"2024-03-10T12:00:50"}' http://localhost:8080/couriers/location
echo
curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier002","latitude":40.9632483,"longitude":29.0630908,"time":"2024-03-10T12:01:02"}' http://localhost:8080/couriers/location
echo
curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier002","latitude":40.9923400,"longitude":29.1244229,"time":"2024-03-10T12:30:00"}' http://localhost:8080/couriers/location
echo
curl -X POST -H "Content-Type: application/json" -d '{"courierId":"courier002","latitude":40.9923410,"longitude":29.1244229,"time":"2024-03-10T12:30:50"}' http://localhost:8080/couriers/location
echo

# Total travel distance
response=$(curl -s 'http://localhost:8080/couriers/total-distance?courierId=courier001')

if [ "$response" = "0.22238985346112466" ]; then
  echo "Success: Correct distance for courier001"
else
  echo "Failed: Wrong distance for courier001"
fi

# Total travel distance
response=$(curl -s 'http://localhost:8080/couriers/total-distance?courierId=courier002')

if [ "$response" = "6080.919373351094" ]; then
  echo "Success: Correct distance for courier002"
else
  echo "Failed: Wrong distance for courier002"
fi
