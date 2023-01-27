FROM openjdk:11
#VOLUME /tmp
EXPOSE 8083
COPY "./target/report-0.0.1-SNAPSHOT.jar" "report.jar"
ENTRYPOINT ["java","-jar","report.jar"]