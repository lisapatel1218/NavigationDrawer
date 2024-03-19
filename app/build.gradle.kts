plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.navigationdrawer"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.navigationdrawer"
        minSdk = 24
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
    buildFeatures{
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.gitee.baijuncheng-open-source:recycleview:1.0.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.firebase:firebase-messaging:23.4.0")
    kapt("androidx.room:room-compiler:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.airbnb.android:lottie:3.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
    // crop image
    implementation ("com.soundcloud.android:android-crop:1.0.1@aar")

  // banner ads
    implementation ("com.google.android.gms:play-services-ads:22.6.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    kapt ("com.github.bumptech.glide:compiler:4.12.0")
    //swipe delete update
    implementation ("it.xabaras.android:recyclerview-swipedecorator:1.4")
    //pull to refersh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
   implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.android.gms:play-services-auth-api-phone:18.0.2")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    //facebook
//    implementation("com.facebook.android:facebook-android-sdk:12.0.0")
//currency converter
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //facebook
    implementation ("com.facebook.android:facebook-android-sdk:13.0.0")



////    //firebase crashlytics
//    implementation ("com.google.firebase:firebase-analytics-ktx:21.5.0")
//    implementation ("com.google.firebase:firebase-crashlytics-ktx:18.6.1")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // For Gson converter (you can choose other converters based on your requirement)



}