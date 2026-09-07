import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinSerialization)
    id("com.google.gms.google-services")
}

android {
    namespace = "fureverlove.ucb"
    compileSdk = 35

    defaultConfig {
        applicationId = "fureverlove.ucb"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Defined only in the ignored local.properties file.
        // Android will embed this value in the APK, so the key must additionally
        // be restricted by Android application ID and signing-certificate SHA-1.
        val localProperties = Properties().apply {
            val localPropertiesFile = rootProject.file("local.properties")
            if (localPropertiesFile.exists()) {
                localPropertiesFile.inputStream().use(::load)
            }
        }
        val mapsApiKey = localProperties.getProperty("MAPS_API_KEY", "")
        resValue("string", "google_maps_api_key", mapsApiKey)
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
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.play.services.location)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.runtime.livedata)
    debugImplementation(libs.leakcanary.android)
    implementation(libs.navigation)
    implementation(libs.hilt.navigation)
    implementation(libs.kotlinx.coroutines.core) // Para Flow
    implementation(libs.coil)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.test)
    kaptAndroidTest(libs.hilt.compiler)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    implementation (libs.androidx.navigation.compose.v272)

    // Import the Firebase BoM
    implementation(libs.firebase.bom)
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.firebase.analytics)
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
    implementation (libs.firebase.auth)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx.v262)

    implementation(libs.firebase.analytics.ktx)

    implementation (libs.google.firebase.firestore.ktx)

    implementation("com.google.maps.android:maps-compose:4.3.2")
    implementation("com.google.android.gms:play-services-maps:19.2.0")

    implementation ("androidx.core:core-ktx:1.16.0")

    implementation ("androidx.core:core-ktx:1.12.0")

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)

    implementation(project(":usecases"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":framework"))
}

kapt {
    correctErrorTypes = true
}
