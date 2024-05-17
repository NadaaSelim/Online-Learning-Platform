#FROM ubuntu
#RUN apt update && \
#    apt install -y wget unzip pwgen expect && \
#    apt install -y openjdk-11-jdk openjdk-11-jre && \
#    wget https://download.eclipse.org/ee4j/glassfish/web-6.2.5.zip && \
#    unzip web-6.2.5.zip -d /opt && \
#    rm web-6.2.5.zip && \
#    apt clean && \
#    rm -rf /var/lib/apt/lists/*
#
#ENV PATH /opt/glassfish6/bin:$PATH
#
#COPY /target/demo-1.0-SNAPSHOT.war /opt/glassfish6/autodeploy/
#
#
#EXPOSE 4848 8080 8181
#
#CMD ["/opt/glassfish6/bin/asadmin", "start-domain", "domain1"]
## docker run -d -p 4848:4848 -p 8084:8084 -p 8181:8181 glassfish6
FROM ubuntu

RUN apt update && \
    apt install -y wget unzip pwgen expect && \
    apt install -y openjdk-17-jdk openjdk-17-jre && \
    wget https://download.eclipse.org/ee4j/glassfish/web-6.2.5.zip && \
    unzip web-6.2.5.zip -d /opt && \
    rm web-6.2.5.zip && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

ENV PATH /opt/glassfish6/bin:$PATH

#ADD run.sh /run.sh
#ADD change_admin_password.sh /change_admin_password.sh
#ADD change_admin_password_func.sh /change_admin_password_func.sh
#ADD enable_secure_admin.sh /enable_secure_admin.sh
#RUN chmod +x /*.sh

# 4848 (administration), 8080 (HTTP listener), 8181 (HTTPS listener)
EXPOSE 4848 8080 8181
COPY /target/demo-1.0-SNAPSHOT.war /opt/glassfish6/autodeploy/

CMD ["/run.sh"]

