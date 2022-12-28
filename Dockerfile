FROM openjdk:17
ADD target/JavaHoneyPot-0.0.1-SNAPSHOT.jar honeypot.jar
ENTRYPOINT ["java", "-jar", "honeypot.jar"]