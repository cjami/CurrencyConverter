package che.codes.currencyconverter.build

object Versions {
    val kotlin = "1.3.41"
    val gradle = "3.4.2"

    val retrofit = "2.6.0"
    val dagger = "2.23.2"
    val rxjava = "2.2.9"
    val rxandroid = "2.1.1"
    val rxkotlin = "2.4.0-beta.1"
    val picasso = "2.71828"
    val picassoTransformations = "2.2.1"
    val gson = "2.8.5"
    val room = "2.2.0-alpha01"
    val okhttp = "4.0.1"

    object Support {
        val appcompat = "1.0.2"
        val lifecycle = "2.2.0-alpha02"
        val recyclerView = "1.0.0"
        val core = "1.2.0-alpha02"
        val coreKtx = "1.2.0-alpha02"
    }

    object Test {
        val junit = "5.5.0"
        val junit4 = "4.12"
        val mockito = "1.0.6"
        val mockitoKotlin = "2.1.0"
        val coreTesting = "2.0.0-rc01"
        val hamcrest = "2.1"
    }

    object AndroidTest {
        val espresso = "3.1.1"
        val rxidler = "0.9.1"
        val runner = "1.1.1"
        val mockwebserver = "4.0.1"
        val rules = "1.1.0"
        val junit = "1.1.0"
        val junitKtx = "1.1.0"
        val fragment = "1.2.0-alpha01"
    }
}

object Android {
    val compileSdkVersion = 28
    val applicationId = "che.codes.currencyconverter"
    val minSdkVersion = 24
    val targetSdkVersion = 28
    val versionCode = 1
    val versionName = "1.0"
}

object BuildPlugins {
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object AnnotationPlugins {
    val dagger = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val room = "androidx.room:room-compiler:${Versions.room}"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val room = "androidx.room:room-runtime:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    val roomRx = "androidx.room:room-rxjava2:${Versions.room}"
    val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    val picassoTransformations = "jp.wasabeef:picasso-transformations:${Versions.picassoTransformations}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

    object Support {
        val appcompat = "androidx.appcompat:appcompat:${Versions.Support.appcompat}"
        val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.Support.lifecycle}"
        val recyclerView = "androidx.recyclerview:recyclerview:${Versions.Support.recyclerView}"
        val core = "androidx.core:core:${Versions.Support.core}"
        val coreKtx = "androidx.core:core-ktx:${Versions.Support.coreKtx}"
    }

    object Test {
        val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.Test.junit}"
        val junit4 = "junit:junit:${Versions.Test.junit4}"
        val mockito = "org.mockito:mockito-core:${Versions.Test.mockito}"
        val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.Test.mockitoKotlin}"
        val coreTesting = "androidx.arch.core:core-testing:${Versions.Test.coreTesting}"
        val hamcrest = "org.hamcrest:hamcrest:${Versions.Test.hamcrest}"
    }

    object AndroidTest {
        val espressoCore = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}"
        val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.AndroidTest.espresso}"
        val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.AndroidTest.espresso}"
        val runner = "androidx.test:runner:${Versions.AndroidTest.runner}"
        val mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.AndroidTest.mockwebserver}"
        val rxidler = "com.squareup.rx.idler:rx2-idler:${Versions.AndroidTest.rxidler}"
        val rules = "androidx.test:rules:${Versions.AndroidTest.rules}"
        val junit = "androidx.test.ext:junit:${Versions.AndroidTest.junit}"
        val junitKtx = "androidx.test.ext:junit-ktx:${Versions.AndroidTest.junitKtx}"
        val fragment = "androidx.fragment:fragment-testing:${Versions.AndroidTest.fragment}"
    }
}