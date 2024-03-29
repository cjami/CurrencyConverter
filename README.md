# CurrencyConverter
 
| Screenshot 1 | Screenshot 2 |
| --- | --- |
|![Screenshot1](/screenshots/screen_shot1.png)|![Screenshot2](/screenshots/screen_shot2.png)

CurrencyConverter is a simple multi-module app that handles live currency conversions.

### Structure:

Modules are set up with the following dependency structure:

**App -> Core, Features**

**Features -> Core**

**Core -> Data, DataLocal, DataRemote**

**DataLocal, DataRemote -> Data**

Modules can depend on utility testing modules as needed.

A flat hierarchy is used (with appropriate naming) to allow the modules to be clearly distinguishable in the 'Android' perspective in Android Studio.

### Notes:

* The app polls for currency updates every second and updates the currency list accordingly.

* The app features offline functionality via **Room**.

* If possible, the app selects an initial currency based on the user's JVM locale.

* Currencies are formatted according to the user's JVM locale.

* Flags are loaded online from https://www.countryflags.io. **Picasso** disk caches these by default so they can be used when the app is offline (although not if the first run is offline).

* The app is unit tested and has a simple integration/instrumented test that demonstrates the use of **MockWebServer** and appropriate idling of **RxJava** calls.

* The app follows an **MVVM** pattern with the use of **ViewModel + LiveData** components but does not use data binding.

* The project takes advantage of the `buildSrc` folder to hook in dependency properties and versioning (mainly library dependencies) to be used in all modules. This allows gradle build files to look more expressive with statements such as: `implementation Libraries.Support.appcompat`

* Modules can also inherit configurations from `base-android-library.gradle` and `base-kotlin-library.gradle` to avoid lots of messy gradle files.

### Libraries:
* RxJava
* Dagger
* Retrofit
* Room
* Picasso
* Mockito
* Espresso
* MockWebServer
* ViewModel + LiveData
