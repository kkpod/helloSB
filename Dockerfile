FROM quay.io/eclipse/che-java8-maven:nightly

MAINTAINER kkpod

USER root

COPY src /home/app/src
COPY pom.xml /home/app

#ERROR
#RUN nocmd

RUN mkdir -p /var/local/SP

RUN mvn -f /home/app/pom.xml clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/home/app/target/hello_spring_boot_j-0.0.1-SNAPSHOT.jar"]
