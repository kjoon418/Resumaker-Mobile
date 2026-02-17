pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Resumaker"

// Core modules
include(":core:common")
include(":core:navigation")
include(":core:designsystem")
include(":core:ui")
include(":core:network")
include(":core:datastore")

// Domain modules (순수 Kotlin 비즈니스 로직)
include(":domain:common")
include(":domain:auth")
include(":domain:resume")
include(":domain:persona")
include(":domain:mypage")

// Data modules
include(":data:auth")
include(":data:resume")
include(":data:persona")
include(":data:mypage")

// Feature modules
include(":feature:login")
include(":feature:signup")
include(":feature:interview")
include(":feature:resume-builder")
include(":feature:career-manager")
include(":feature:mypage")

// App (Main Entry)
include(":app")
