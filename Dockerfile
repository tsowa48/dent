FROM openjdk:11.0 as build
WORKDIR /app
COPY . /app
RUN ./gradlew -x test build

FROM openjdk:11.0
COPY --from=build /app/build/libs/dent-*-all.jar dent.jar
EXPOSE 8090
CMD java -Dfile.encoding=UTF-8 -Dcom.sun.management.jmxremote -Dhibernate.types.print.banner=false -noverify ${JAVA_OPTS} -jar dent.jar
