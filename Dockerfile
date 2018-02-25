# BUILD
FROM clojure:lein

COPY . /build
WORKDIR /build

RUN lein uberjar

# RUN
FROM openjdk:8

COPY --from=0 --chown=1001 /build/target/uberjar/vielheit.jar /vielheit/app.jar

EXPOSE 3000

USER 1001

CMD ["java", "-jar", "/vielheit/app.jar"]
