FROM gradle:latest
USER root
COPY . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle --info --stacktrace clean build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/numberConverter-0.0.1-SNAPSHOT.jar"]