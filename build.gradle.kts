/*
 * Copyright (C) 2020 PatrickKR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Contact me on <mailpatrickkr@gmail.com>
 */

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import groovy.lang.MissingPropertyException
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.jetbrains.dokka") version "1.4.20"
}

group = "com.github.patrick-mc"
version = "1.1.0"

repositories {
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://jcenter.bintray.com/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot-api:1.8-R0.1-SNAPSHOT")
    implementation("com.neovisionaries:nv-websocket-client:2.10")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<DokkaTask> {
        dokkaSourceSets {
            named("main") {
                displayName.set("WebSocket Client API")
                sourceLink {
                    localDirectory.set(file("src/main/kotlin"))
                    remoteUrl.set(uri("https://github.com/patrick-mc/${rootProject.name}/tree/master/src/main/kotlin").toURL())
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }

    withType<ShadowJar> {
        archiveClassifier.set("")
        exclude("**/*.html")
    }

    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("dokkaJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")

        from("$buildDir/dokka/html/") {
            include("**")
        }

        from("$rootDir/src/main/resources/") {
            include("**/*.html")
        }
    }

    if (System.getProperty("os.name").startsWith("Windows")) {
        create<Copy>("distJar") {
            from(shadowJar)

            val fileName = "${project.name.split("-").joinToString("") { it.capitalize() }}.jar"

            rename {
                fileName
            }

            val pluginsDir = "W:\\Servers\\1.16.4\\plugins"
            val updateDir = "$pluginsDir\\update"

            if (file("$pluginsDir\\$fileName").exists()) {
                into(updateDir)
            } else {
                into(pluginsDir)
            }
        }
    }
}

try {
    publishing {
        publications {
            create<MavenPublication>("webSocketClientAPI") {
                artifact(tasks["sourcesJar"])
                artifact(tasks["dokkaJar"])
                artifact(tasks["shadowJar"])

                repositories {
                    mavenLocal()

                    maven {
                        name = "central"

                        credentials {
                            username = project.property("centralUsername").toString()
                            password = project.property("centralPassword").toString()
                        }

                        url = if (version.toString().endsWith("SNAPSHOT")) {
                            uri("https://oss.sonatype.org/content/repositories/snapshots/")
                        } else {
                            uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                        }
                    }
                }

                pom {
                    name.set(rootProject.name)
                    description.set("A Simple WebSocket API for Bukkit")
                    url.set("https://github.com/patrick-mc/${rootProject.name}")

                    licenses {
                        license {
                            name.set("GNU General Public License v2.0")
                            url.set("https://opensource.org/licenses/gpl-2.0.php")
                        }
                    }

                    developers {
                        developer {
                            id.set("patrick-mc")
                            name.set("PatrickKR")
                            email.set("mailpatrickkr@gmail.com")
                            url.set("https://github.com/patrick-mc")
                            roles.addAll("developer")
                            timezone.set("Asia/Seoul")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/patrick-mc/${rootProject.name}.git")
                        developerConnection.set("scm:git:ssh://github.com:patrick-mc/${rootProject.name}.git")
                        url.set("https://github.com/patrick-mc/${rootProject.name}")
                    }
                }
            }
        }
    }

    signing {
        isRequired = true
        sign(tasks["sourcesJar"], tasks["dokkaJar"], tasks["shadowJar"])
        sign(publishing.publications["webSocketClientAPI"])
    }
} catch (ignored: MissingPropertyException) {}