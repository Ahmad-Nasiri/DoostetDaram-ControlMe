plugins {
    id("com.android.application")
    kotlin("android")
}
android {
    namespace = "com.example.doostetdaram"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.doostetdaram"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-ktx:1.10.1")
}
