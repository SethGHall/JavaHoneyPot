FROM openjdk:17
LABEL maintainer="javaguides.net"
ADD target/JavaHoneyPot-0.0.1-SNAPSHOT.jar honeypot.jar
ENTRYPOINT ["java", "-jar", "honeypot.jar"]