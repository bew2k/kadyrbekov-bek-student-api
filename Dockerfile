# ---------- Stage 1: сборка (JDK + Gradle) ----------
FROM gradle:8.10-jdk17 AS build
WORKDIR /home/gradle/project

# Сначала файлы сборки — чтобы Docker кэшировал слой с зависимостями
COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon > /dev/null 2>&1 || true

# Затем исходники и сборка JAR (тесты запускаются отдельно в Jenkins)
COPY src ./src
RUN gradle bootJar -x test --no-daemon

# ---------- Stage 2: запуск (только JRE) ----------
FROM eclipse-temurin:17-jre
LABEL maintainer="Bek Kadyrbekov <IT2-2306, ID 35534>"
WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/student-api.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
