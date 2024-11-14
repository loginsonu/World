plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

val serverBaseUrl:String = project.properties["serverBaseUrl"].toString()
val serverApiKey:String = project.properties["serverApiKey"].toString()

android {
    namespace = "com.example.world"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.world"
        minSdk = 24
        //noinspection OldTargetApi
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
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            buildFeatures.buildConfig = true
            buildConfigField("String", "SERVER_BASE_URL", serverBaseUrl)
            buildConfigField("String", "SERVER_API_KEY", serverApiKey)
            signingConfig = signingConfigs.getByName("debug")

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
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //for collecting state as life cycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    //for api calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.retrofit2.rxjava2.adapter)


    //for navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //for loading images
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //for http logging
    implementation(libs.logging.interceptor)


    // for unit testing
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.mockwebserver)
}