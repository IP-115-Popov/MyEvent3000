import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}
room {
    schemaDirectory("$projectDir/schemas")
}
android {
    namespace = "com.eltex.lab14"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.eltex.lab14"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        val secretProps = rootDir.resolve("secrets.properties")
            .bufferedReader()
            .use {
                Properties().apply {
                    load(it)
                }
            }
        buildConfigField("String", "API_KEY", secretProps.getProperty("API_KEY"))
        //TODO REGISTRATION
        buildConfigField("String", "AUTHORIZATION", secretProps.getProperty("AUTHORIZATION"))
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets")
            }
        }
    }
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.shimmer)
    implementation(libs.retrofit)
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation(libs.arrow.core)
    ksp(libs.androidx.room.compiler)
    implementation(libs.bumptech.glide)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.logging.interceptor)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.test)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.androidx.constraintlayout)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
}