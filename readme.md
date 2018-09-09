### steps to run
```
cd sensor-stats/
mvn clean compile test assembly:single
cd target
java -jar sensor-stats-1.0-SNAPSHOT-jar-with-dependencies.jar /mnt/12C6D428C6D40DBB/projects/sensor-stats/src/test/resources/csv-data-1
```