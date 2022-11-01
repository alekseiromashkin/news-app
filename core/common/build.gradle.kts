plugins {
    id("newsapp.android.library")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.7.20"
}

android {
    namespace = "com.arammeem.android.apps.newsapp.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.dagger)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.kotlin.reflect)

    implementation(libs.kotlinx.serialization.json)

    kapt(libs.dagger.compiler)

    testImplementation(libs.junit4)
}