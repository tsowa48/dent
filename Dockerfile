FROM openjdk:8-alpine
COPY build/libs/dent-*-all.jar dent.jar
EXPOSE 8090
CMD java -Dfile.encoding=UTF-8 -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar dent.jar
