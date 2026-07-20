FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew
RUN sed -i 's/\r$//' ./gradlew # removes \r which windows somehow adds to eol

RUN --mount=type=cache,target=/home/gradle/.gradle/caches \
    ./gradlew dependencies --no-daemon

COPY ./src ./src

RUN --mount=type=cache,target=/home/gradle/.gradle/caches \
    ./gradlew clean build -x test --no-daemon

ENTRYPOINT ["java","-jar","/app/build/libs/wise-task-profile-1.0.0.jar"]
