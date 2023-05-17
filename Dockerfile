# Stage 1: Build React application
FROM node:14.17.5 as frontend-build
WORKDIR /app
COPY city-web/package.json city-web/package-lock.json ./
RUN npm install
COPY city-web/public ./public
COPY city-web/src ./src
RUN npm run build

# Stage 2: Build Spring Boot application
FROM adoptopenjdk/openjdk11:latest as backend-build
WORKDIR /app
COPY city-service/gradle gradle
COPY city-service/gradlew .
COPY city-service/build.gradle .
COPY city-service/settings.gradle .
COPY city-service/src src
COPY --from=frontend-build /app/build ./src/main/resources/static
RUN ./gradlew build

# Stage 3: Create final image
FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
COPY --from=backend-build /app/build/libs/city-0.0.1.jar ./city-0.0.1.jar
EXPOSE 8080
CMD ["java", "-jar", "city-0.0.1.jar"]
