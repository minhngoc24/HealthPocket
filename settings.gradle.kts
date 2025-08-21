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

        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://pkgs.dev.azure.com/SurfaceDuo/maven/_packaging/surface-duo/maven/v1") }
    }
}

rootProject.name = "Health Pocket"
include(":app")
