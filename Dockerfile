FROM openjdk:17
VOLUME /tmp
EXPOSE 8082:8082
ADD ./target/test-1.0.0-SNAPSHOT.jar test-neoris.jar
ADD ./target/data.sql data.sql
ENTRYPOINT ["java","-jar","/test-neoris.jar"]