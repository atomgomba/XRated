plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("io.fabric")
    id("com.google.gms.google-services")
    id("com.github.triplet.play") version "2.7.2"
}

val versionMajor = 0
val versionMinor = 1
val versionPatch = 0

android {
    compileSdkVersion(Versions.compileSdk)

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
    }

    defaultConfig {
        applicationId = "com.ekezet.xrated"
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        versionCode = 10000 * versionMajor + 100 * versionMinor + versionPatch
        versionName = "$versionMajor.$versionMinor.$versionPatch"

        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        register("release") {
            storeFile = file(System.getenv("KEYSTORE_PATH") ?: "../.keystore/release.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEYSTORE_ALIAS")
            keyPassword = System.getenv("KEYSTORE_ALIAS_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isZipAlignEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            resValue("bool", "useFirebase", "true")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true

            resValue("bool", "useFirebase", "false")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    useBuildCache = true
}

play {
    serviceAccountCredentials = file(System.getenv("SERVICE_ACCOUNT_PATH") ?: "gcp-key.json")
}

dependencies {
    implementation(Deps.Kotlin.stdLib)
    implementation(Deps.Kotlin.coroutinesCore)
    implementation(Deps.Kotlin.coroutinesAndroid)

    implementation(Deps.Androidx.appcompat)
    implementation(Deps.Androidx.coreKtx)
    implementation(Deps.Androidx.constraintLayout)
    implementation(Deps.Androidx.Lifecycle.common)
    implementation(Deps.Androidx.Lifecycle.extensions)
    implementation(Deps.Androidx.recyclerView)
    implementation(Deps.Androidx.viewPager2)

    implementation(Deps.material)

    implementation(Deps.Dagger.dagger)
    kapt(Deps.Dagger.compiler)
    implementation(Deps.Dagger.androidSupport)
    kapt(Deps.Dagger.androidProcessor)

    implementation(Deps.OkHttp.OkHttp)
    implementation(Deps.OkHttp.loggingInterceptor)

    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.moshiConverter)

    implementation(Deps.timber)
    implementation(Deps.jodaTime)

    implementation(Deps.firebaseAnalytics)
    implementation(Deps.crashlytics)

    implementation(project(":base"))
    implementation(project(":rates"))

    testImplementation(Deps.Test.JUnit.api)
    testRuntimeOnly(Deps.Test.JUnit.engine)
    testImplementation(Deps.Test.JUnit.params)
    testImplementation(Deps.Test.mockk)
    testImplementation(Deps.Test.lifecycle)
}
