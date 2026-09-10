plugins {
    java
    application
}

group = "com.example.weather"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    compileOnly("org.projectlombok:lombok:1.18.40")
    annotationProcessor("org.projectlombok:lombok:1.18.40")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    mainClass.set("com.example.weather.Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
