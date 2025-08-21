pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 🔑 Đúng cú pháp Kotlin DSL
        maven("https://pkgs.dev.azure.com/microsoft/DisplayMask/_packaging/display-mask/maven/v1")
    }
}

rootProject.name = "Health Pocket"
include(":app")
