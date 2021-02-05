FROM openjdk:8-jre-alpine

EXPOSE 8080
COPY target/food-deli-0.0.1-SNAPSHOT.jar /app

CMD java -jar /app/food-deli-0.0.1-SNAPSHOT.jar
