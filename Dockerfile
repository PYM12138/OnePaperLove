FROM openjdk:8-jdk-alpine

#设置工作目录
WORKDIR /app

COPY target/MingBlog-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
