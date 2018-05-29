FROM williamyeh/java8

RUN apt-get -y update && apt-get install -y maven

WORKDIR /rock-paper-scissors

ADD settings.xml /root/.m2/settings.xml
ADD pom.xml /rock-paper-scissors
ADD src /rock-paper-scissors/src

RUN ["mvn", "clean", "install"]

RUN cp /rock-paper-scissors/target/rock-paper-scissors-1.0-SNAPSHOT.jar /rock-paper-scissors/app.jar
RUN sh -c 'touch /rock-paper-scissors/app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /rock-paper-scissors/app.jar" ]