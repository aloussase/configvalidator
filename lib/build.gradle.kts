plugins {
    `java-library`
    id("com.vanniktech.maven.publish") version "0.35.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.yaml:snakeyaml:2.2")
    implementation("com.squareup.okhttp3:okhttp:5.3.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0-rc1")
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val version = "0.2.0"

mavenPublishing {
    coordinates("io.github.aloussase", "configvalidator", version)
    pom {
        name.set("Config Validator")
        description.set("Rule engine to validate configuration coming from different sources.")
        inceptionYear.set("2025")
        url.set("https://github.com/aloussase/configvalidator/")
        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/license/mit")
                distribution.set("https://opensource.org/license/mit")
            }
        }
        developers {
            developer {
                id.set("aloussase")
                name.set("Alexander Goussas")
                url.set("https://github.com/aloussase/")
            }
        }
        scm {
            url.set("https://github.com/aloussase/configvalidator/")
            connection.set("scm:git:git://github.com/aloussase/configvalidator.git")
            developerConnection.set("scm:git:ssh://git@github.com/aloussase/configvalidator.git")
        }
    }
}