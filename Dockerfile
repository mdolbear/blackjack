FROM openjdk:11

ARG PROJECT_NAME
ARG PROJECT_DESCRIPTION
ARG PROJECT_VERSION
ARG BUILD_TIMESTAMP
ARG BUILD_NUMBER
ARG JAR_FILE


# Add some labels to this image
LABEL com.mjdsoftware.app.name="${PROJECT_NAME}" \
      com.mjdsoftware.app.description="${PROJECT_DESCRIPTION}" \
      com.mjdsoftware.app.version="${PROJECT_VERSION}" \
      com.mjdsoftware.app.build.timestamp="${BUILD_TIMESTAMP}" \
      com.mjdsoftware.app.build.commit="${BUILD_NUMBER}"

VOLUME /tmp
ARG JAR_FILE

ADD ${JAR_FILE} blackjack-1.0-SNAPSHOT.jar

EXPOSE 4000 8000
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,address=4000,suspend=n"

ENTRYPOINT java $JAVA_OPTS $JAVA_ARGS -Dspring.profiles.active=prod -jar /blackjack-1.0-SNAPSHOT.jar
