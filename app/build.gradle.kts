import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}



android {
    namespace = "com.example.retrofitdavidcarlos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.retrofitdavidcarlos"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

/*
 *  Deberías tener las claves de TWITCH_CLIENT_ID y TWITCH_CLIENT_SECRET en tu archivo  de local.properties
 *  Entonces podrás usar esas claves asignando estas variables
 *  Ejemplo:
 *  val clientId = twitchClientId
 *  val clientSecret = twitchClientSecret
 */
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
/**
 * ID del cliente registrado en locals.properties
 */
val twitchClientId: String = localProperties.getProperty("TWITCH_CLIENT_ID") ?: ""
/**
 * Secreto del cliente registrado en locals.properties
 */
val twitchClientSecret: String = localProperties.getProperty("TWITCH_CLIENT_SECRET") ?: ""

