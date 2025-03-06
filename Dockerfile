FROM tomcat:9.0

WORKDIR /usr/local/tomcat/webapps/

# Install curl and download MySQL Connector
RUN apt-get update && apt-get install -y curl && \
    curl -o /usr/local/tomcat/lib/mysql-connector-java-8.0.33.jar \
    https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar

# Change Tomcat default port from 8080 to 9090
RUN sed -i 's/8080/9090/g' /usr/local/tomcat/conf/server.xml

# Copy project files to Tomcat
COPY . /usr/local/tomcat/webapps/OnlineBanking/

EXPOSE 9090

CMD ["catalina.sh", "run"]

