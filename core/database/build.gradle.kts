@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("newsapp.android.library")
    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    defaultConfig {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    namespace = "com.arammeem.android.apps.newsapp.core.database"
}

dependencies {
    implementation(libs.dagger)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    kapt(libs.dagger.compiler)
}