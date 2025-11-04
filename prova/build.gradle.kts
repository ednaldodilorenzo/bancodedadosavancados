plugins {
    id("java")
}

group = "br.edu.ifpb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")
    implementation("com.h2database:h2:2.1.214")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}