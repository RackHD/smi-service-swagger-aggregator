FROM openjdk:8-jre
VOLUME /tmp
ADD build/libs/service-swagger-aggregator*.jar app.jar
#COPY pkg/asciidoc/* /asciidoc/include/
#COPY application.yml /application.yml
EXPOSE 46021
#RUN mkdir -p /security/certs/
#RUN mkdir -p /security/private/
#RUN mkdir -p /security/cacerts/
#RUN bash -c 'apt-get update && apt-get -y install expect'
RUN bash -c 'touch /app.jar'
#RUN chmod +x /scripts/certificate_tool.sh
#RUN ["/bin/bash", "-c", "/scripts/certificate_tool.sh -s"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

