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

Modules can depend on utility testing modules as needed.

### Notes:

The app polls for currency updates every second and updates the currency list accordingly.

The app features offline functionality via **Room**.

The app is unit tested and has a simple integration/instrumented test that demonstrates the use of **MockWebServer** and appropriate idling of **RxJava** calls.

The app follows an MVVM pattern with the use of ViewModel + LiveData components but does not use data binding.

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
