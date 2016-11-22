# nfllook-tools-gcdata-download
Download nfl.com Game Center Data

## Prerequisite:
1. Java
2. Directory for data: $NFLGameData_Dir

## Build app
mvnw clean install

## Run app
###Download specified week
java -jar target\gcdata-download-0.0.1-SNAPSHOT.jar --season=2016 --week=1 --path=$NFLGameData_Dir

### Download full regular season
java -jar target\gcdata-download-0.0.1-SNAPSHOT.jar --season=2016 --path=$NFLGameData_Dir