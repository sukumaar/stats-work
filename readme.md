### Steps to run

```
git clone https://github.com/sukumaar/stats-work.git
cd stats-work/
mvn clean compile test assembly:single
cd target
java -jar sensor-stats-1.0-SNAPSHOT-jar-with-dependencies.jar ../src/test/resources/csv-data-1

```

**Frameworks / Languages Used :**

- Scala 2.11.12
- Java 1.8 
- Apache Maven 3.5.3