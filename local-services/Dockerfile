FROM openjdk:13
ARG JAR_FILE=./target/game-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} mlpg.jar
ENTRYPOINT ["java","-jar","/mlpg.jar"]