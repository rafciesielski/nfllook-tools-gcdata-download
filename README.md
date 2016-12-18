# nfllook-tools-gcdata-download
Download nfl.com Game Center Data

## Prerequisites:
1. Java
2. Directory for data: $NFLGameData_Dir
3. MongoDB(with NFL schedule, see: https://github.com/rafciesielski/nfllook-tools-schedule). 
   $MongoDBUri format: mongodb://user:password@host:port/database

## Build app
mvnw clean install

## Run app
### Download one week
java -jar target\gcdata-download-0.0.1-SNAPSHOT.jar --season=2016 --week=1 --path=$NFLGameData_Dir --uri=$MongoDBUri

### Download full regular season
java -jar target\gcdata-download-0.0.1-SNAPSHOT.jar --season=2016 --path=$NFLGameData_Dir --uri=$MongoDBUri
