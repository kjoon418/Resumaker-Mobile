plugins {
    `kotlin-dsl`
}

group = "com.resumaker.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("com.android.tools.build:gradle:8.13.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.10")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.3.10")
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "resumaker.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "resumaker.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "resumaker.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}
