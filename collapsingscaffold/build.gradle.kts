import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.maven)
    id("signing")
}

android {
    namespace = "com.ny.collapsing.toolbar"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates("io.github.skdud0629", "collapsing-header-scaffold", "0.0.2")

    pom {
        name = "CollapsingHeaderScaffold"
        description = "A Jetpack Compose library for creating a collapsible header with a sticky list, similar to the classic CollapsingToolbarLayout."
        url = "https://github.com/skdud0629/collapsing-header-scaffold"
        inceptionYear = "2025"

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        developers {
            developer {
                id = "skdud0629"
                name = "skdud0629"
                url = "https://github.com/skdud0629"
            }
        }

        scm {
            connection.set("scm:git:git://github.com/skdud0629/collapsing-header-scaffold.git")
            developerConnection.set("scm:git:ssh://git@github.com/skdud0629/collapsing-header-scaffold.git")
            url.set("https://github.com/skdud0629/collapsing-header-scaffold")
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}
