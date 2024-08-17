import java.util.Properties

plugins {
    // Android Application
    alias(libs.plugins.android.application)
    // Android Kotlin
    alias(libs.plugins.jetbrains.kotlin.android)
    // Hilt Dagger
    alias(libs.plugins.hilt.android)
    // KSP
    alias(libs.plugins.devtools.ksp)
    // Android OSS
    id("com.google.android.gms.oss-licenses-plugin")
    // Android Kotlin Kapt
    // id("kotlin-kapt")
    // Kotlin X Serialization
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "com.thomas200593.mini_retail_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.thomas200593.mini_retail_app"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        resourceConfigurations += listOf("en", "in")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables{
            useSupportLibrary = true
        }
    }

    buildTypes {
        val apiKeyFile = project.rootProject.file("apikey.properties")
        val properties = Properties()
        properties.load(apiKeyFile.inputStream())

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            /**
             * BuildConfig Variables (Debug)
             */
            buildConfigField(
                type = "String",
                name = "APP_LOCAL_DATASTORE_FILENAME",
                value = "\"app_local_datastore\""
            )
            buildConfigField(
                type = "String",
                name = "APP_LOCAL_DATABASE_FILENAME",
                value = "\"app_local_database.db\""
            )
            buildConfigField(
                type = "String",
                name = "GOOGLE_AUTH_WEB_ID",
                value = properties.getProperty("GOOGLE_OAUTH2_WEB_CLIENT_ID_DEBUG").orEmpty()
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            /**
             * BuildConfig Variables (Release)
             */
            buildConfigField(
                type = "String",
                name = "APP_LOCAL_DATASTORE_FILENAME",
                value = "\"app_local_datastore\""
            )
            buildConfigField(
                type = "String",
                name = "APP_LOCAL_DATABASE_FILENAME",
                value = "\"app_local_database.db\""
            )
            buildConfigField(
                type = "String",
                name = "GOOGLE_AUTH_WEB_ID",
                value = properties.getProperty("GOOGLE_OAUTH2_WEB_CLIENT_ID_RELEASE").orEmpty()
            )
        }
    }
    testOptions.unitTests{
        isIncludeAndroidResources = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        compose = true
        buildConfig = true
        shaders = false
    }
    composeOptions{
        kotlinCompilerExtensionVersion="1.5.3"
    }
    /*hilt{
        enableAggregatingTask = true
    }*/
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.android.material)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size.classes)
    implementation(libs.androidx.tools.core)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.coil.compose)
    implementation(libs.googleid)
    implementation(libs.hilt.android)
    implementation(libs.joda.money)
    implementation(libs.jwtdecode)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.okhttp)
    implementation(libs.okhttp.urlconnection)
    implementation(libs.play.services.oss.licenses)
    implementation(libs.timber)
    implementation(libs.tracing.trace)
    implementation(libs.ulid.kotlin)

    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testRuntimeOnly(libs.junit.jupiter.engine)

    annotationProcessor(libs.androidx.room.compiler)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.ui.tooling)

    ksp(libs.androidx.hilt.compiler)
    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.android.compiler)
}