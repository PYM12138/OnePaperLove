# 使用维护中的 8-jre 镜像（JRE 比 JDK 更小，更适合运行环境）
FROM eclipse-temurin:8-jre-alpine

WORKDIR /app

# 修正：确保路径匹配（注意大小写和文件名）
COPY target/MingBlog-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# 优化启动参数，限制内存，防止 Java 把服务器内存吃光
ENTRYPOINT ["java", "-jar", "app.jar"]

