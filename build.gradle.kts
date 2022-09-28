plugins {
    id("java")
    id("org.springframework.boot") version("2.7.4")
    id("io.spring.dependency-management") version ("1.0.14.RELEASE")
}

group = "com.yujiangjun"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

configurations.all{
    exclude("org.springframework.boot","spring-boot-starter-logging")
}
dependencies {

    implementation("cn.hutool:hutool-all:5.8.8")
    implementation("com.lmax:disruptor:3.4.4")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.netty:netty-all:4.1.82.Final")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}