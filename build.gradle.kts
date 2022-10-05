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

    implementation("org.apache.hbase:hbase-client:1.3.6"){
        exclude("org.slf4j","slf4j-api")
        exclude("org.slf4j","slf4j-log4j12")
    }
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("cn.hutool:hutool-all:5.8.8")
    implementation("com.auth0:java-jwt:3.8.3")
    implementation("cn.hutool:hutool-all:5.8.8")
    implementation("com.lmax:disruptor:3.4.4")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter"){
        exclude("org.springframework.boot","spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly ("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.netty:netty-all:4.1.82.Final")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType(JavaCompile::class.java) {
    options.encoding = "utf-8"
}

tasks.withType(Javadoc::class.java) {
    options.encoding = "utf-8"
}