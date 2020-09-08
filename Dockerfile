FROM 10.9.0.172:5000/openjdk:centos8
LABEL "Maintainer"="Axis"
USER 0
RUN groupadd demo && adduser -d /opt/app -g  demo  demo
WORKDIR /opt/apps
COPY target/demo-0.0.1.jar /opt/apps
RUN chown -R demo:demo /opt/apps
#
# RUNTIME
#
USER demo
ENTRYPOINT ["java","-jar","demo-0.0.1.jar"]
