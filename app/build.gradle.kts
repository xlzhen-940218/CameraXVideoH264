plugins {
    id("com.android.application")
}

android {
    namespace = "androidx.camera.video"
    compileSdk = 34

    defaultConfig {
        applicationId = "androidx.camera.video"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(project(mapOf("path" to ":mvvm")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    compileOnly("com.google.auto.value:auto-value-annotations:v1.10.2")
    implementation("androidx.concurrent:concurrent-futures:1.1.0")
    implementation("androidx.camera:camera-core:1.3.0-beta02")
    implementation("androidx.camera:camera-camera2:1.3.0-beta02")
    implementation("androidx.camera:camera-lifecycle:1.3.0-beta02")
    implementation("androidx.camera:camera-video:1.3.0-beta02")
    implementation("androidx.camera:camera-view:1.3.0-beta02")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

}