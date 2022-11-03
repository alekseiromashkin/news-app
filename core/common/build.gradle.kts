plugins {
    id("newsapp.android.library")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.7.20"
}

android {
    namespace = "com.arammeem.android.apps.newsapp.core.common"
}

dependencies {
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit4)
}