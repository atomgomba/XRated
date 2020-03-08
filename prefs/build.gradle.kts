plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Versions.compileSdk)

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
            res.setSrcDirs(mutableListOf(
                "src/main/res",
                "src/main/res-transferwise"
            ))
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
    }

    flavorDimensions("default")

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(Deps.Kotlin.stdLib)
    implementation(Deps.Kotlin.coroutinesCore)
    implementation(Deps.Kotlin.coroutinesAndroid)

    implementation(Deps.Androidx.coreKtx)
    implementation(Deps.Androidx.appcompat)
    api(Deps.Androidx.preference)
    implementation(Deps.Androidx.constraintLayout)
    implementation(Deps.Androidx.recyclerView)
    implementation(Deps.Androidx.Lifecycle.common)
    implementation(Deps.Androidx.Lifecycle.extensions)

    implementation(Deps.material)

    implementation(Deps.Dagger.dagger)
    implementation("androidx.preference:preference:1.1.0")
    kapt(Deps.Dagger.compiler)
    implementation(Deps.Dagger.androidSupport)
    kapt(Deps.Dagger.androidProcessor)

    implementation(Deps.timber)

    api(project(":base"))
    api(project(":testing"))

    testImplementation(Deps.Test.JUnit.api)
    testRuntimeOnly(Deps.Test.JUnit.engine)
    testImplementation(Deps.Test.JUnit.params)
    testImplementation(Deps.Test.mockk)
    testImplementation(Deps.Test.lifecycle)
}
