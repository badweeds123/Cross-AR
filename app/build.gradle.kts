plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.github.badweeds123.crossar"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    // ARCore
    implementation("com.google.ar:core:1.41.0")

    // Sceneform (Thomas Gorisse fork)
    implementation("com.gorisse.thomas.sceneform:sceneform:1.22.0")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.badweeds123"
            artifactId = "Cross-AR"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
