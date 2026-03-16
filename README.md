# OnePaperLove
自建博客网站
# OnePaperLove - 简约个人博客系统

基于 Spring Boot + Mybatis + Docker + Nginx 构建的容器化博客系统。

## 🚀 技术栈
* **后端**: Spring Boot 2.x, MyBatis, PageHelper
* **数据库**: MySQL 5.7, Redis 5
* **部署**: Docker Compose, Nginx
* **编辑器**: Editor.md (Markdown 支持)

## 🛠 本地开发环境配置
1.  修改 `src/main/resources/application-dev.yml` 中的数据库连接。
2.  运行 `mvn clean package` 生成 JAR 包。

## 📦 生产环境部署 (Docker)
项目采用全容器化部署，确保环境一致性。
