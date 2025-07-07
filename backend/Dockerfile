# ========================================
# 👷 Stage 1: Build with Gradle + Temurin
# ========================================
FROM eclipse-temurin:21-jdk-alpine AS builder

# ติดตั้ง Gradle ด้วย SDKMAN หรือแจกจ่ายด้วย wrapper
# กรณีนี้ใช้ Gradle Wrapper ในโปรเจกต์
WORKDIR /app

# คัดลอก Gradle wrapper ก่อน (จะได้ cache ได้)
COPY gradlew .
COPY gradle ./gradle

# คัดลอกไฟล์ build และ dependency ต่าง ๆ
COPY build.gradle settings.gradle ./

# Download dependencies ล่วงหน้า (optional optimization)
RUN ./gradlew dependencies --no-daemon || return 0

# คัดลอก source code ที่เหลือ
COPY . .

# สร้าง jar
RUN ./gradlew bootJar --no-daemon

# ========================================
# 🚀 Stage 2: Run with Temurin JDK only
# ========================================
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
