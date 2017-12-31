FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/vielheit.jar /vielheit/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/vielheit/app.jar"]
