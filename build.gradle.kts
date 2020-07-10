plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "com.github.patrick-mc"
version = "0.1-SNAPSHOT"

repositories {
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot-api:1.8-R0.1-SNAPSHOT")
    implementation("com.neovisionaries:nv-websocket-client:2.9")
}

tasks {
    shadowJar {
        archiveClassifier.set("dist")
    }

    create<Copy>("distJar") {
        from(shadowJar)
        into("W:\\Servers\\1.15.2\\plugins")
    }
}