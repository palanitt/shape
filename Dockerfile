FROM java:8-jdk-alpine

COPY ./target/shape-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch shape-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","shape-0.0.1-SNAPSHOT.jar"]