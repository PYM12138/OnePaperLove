# 使用维护中的 8-jre 镜像（JRE 比 JDK 更小，更适合运行环境）
FROM eclipse-temurin:8-jre-alpine

WORKDIR /app

# 修正：确保路径匹配（注意大小写和文件名）
COPY target/MingBlog-0.0.1-SNAPSHOT.jar.jar app.jar

# 设置时区为亚洲/上海（解决 Java 程序日志时间不准的问题）
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8080

# 优化启动参数，限制内存，防止 Java 把服务器内存吃光
ENTRYPOINT ["java", "-jar", "app.jar"]