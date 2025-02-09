import java.util.Properties

//Get the api key from the local.properties file
 //It is not added to Git as it is specified in the gitignore
 val properties = Properties()
val propertiesFile = File("local.properties")
properties.load(propertiesFile.inputStream())
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.9.22"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.solt.deliveryweatherinsighttest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.solt.deliveryweatherinsighttest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //Add a build config field for the api key
        buildConfigField("String","api_key", properties["api_key"].toString())
        //Add a build config field for the map api_key
        buildConfigField("String","map_api_key", properties["mapApiKey"].toString())
        //Add a build config field for the geoApify api_key
        buildConfigField("String","geoApifyKey", properties["geoapify_Api_Key"].toString())

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
    kapt{
        correctErrorTypes = false
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Retrofit Dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    //Room Dependencies
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-paging:$room_version")


    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    //Map Libre
    implementation("org.maplibre.gl:android-sdk:11.8.0")
    //Google Location Services
    implementation("com.google.android.gms:play-services-location:21.3.0")

    val nav_version = "2.8.6"
    //  Jetpack Navigation Component
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    //Marker View  for Map Libre
    implementation("org.maplibre.gl:android-plugin-markerview-v9:3.0.2")
    //Paging support
    val paging_version = "3.3.5"
    implementation("androidx.paging:paging-runtime:$paging_version")

    //Glide for loading images
    implementation ("com.github.bumptech.glide:glide:4.16.0")



}