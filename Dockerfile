FROM openjdk:11
MAINTAINER tempo.exercise
COPY /target/tempo-0.0.1-SNAPSHOT.jar tempo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tempo-0.0.1-SNAPSHOT.jar"]