plugins {
    id("newsapp.android.feature")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.9.23"
}

android {
    namespace = "com.arammeem.android.apps.newsapp.feature.sources"
}

dependencies {
    implementation(project(":core:database"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    implementation(libs.retrofit.core)

    implementation(libs.room.runtime)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)
}
