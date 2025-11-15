import java.io.FileInputStream
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
}

val localProperties = Properties().apply {
  val localFile = rootProject.file("local.properties")
  if(localFile.exists()){
    load(FileInputStream(localFile))
  }
}

val apiUrl = localProperties.getProperty("API_URL") ?: "https://defaulturl.com"
val socketUrl = localProperties.getProperty("SOCKET_URL") ?: "ws://defaulturl.com"


android {
  namespace = "com.tarangini.traiana"
  compileSdk = 36

  buildFeatures{
    buildConfig = true
  }

  defaultConfig {
    applicationId = "com.tarangini.traiana"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    buildConfigField("String","API_URL","\"$apiUrl\"")
    buildConfigField("String","SOCKET_URL","\"$socketUrl\"")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
  implementation(libs.material3)
  implementation(libs.androidx.foundation.layout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  //  COIL DEPENDENCIES
  implementation("io.coil-kt:coil-compose:2.6.0")
  implementation("io.coil-kt:coil-svg:2.6.0")

  //  MAPBOX
  implementation("com.mapbox.maps:android-ndk27:11.14.4")
  implementation("com.mapbox.extension:maps-compose-ndk27:11.14.4")

  // ACCOMPANIST PERMISSIONS
  implementation("com.google.accompanist:accompanist-permissions:0.37.3")
  implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

  // GOOGLE PLAY SERVICES
  implementation("com.google.android.gms:play-services-location:21.0.1")

  // OK HTTP
  implementation("com.squareup.okhttp3:okhttp:4.12.0")

  // COROUTINES
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

  // FORM BUILDER
  implementation("com.github.jkuatdsc:form-builder:1.0.3")

  // COMPOSE NAVIGATION
  implementation("androidx.navigation:navigation-compose:2.9.3")

  // RETRO FIT
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")

  // MAVERICKS
  implementation("com.airbnb.android:mavericks:3.0.10")
  implementation("com.airbnb.android:mavericks-compose:3.0.10")

  // ANDROID SECURITY
  implementation("androidx.security:security-crypto:1.1.0-alpha06")

  // NAVIGATION ANIMATION
  implementation("androidx.navigation:navigation-compose:2.7.0")
}