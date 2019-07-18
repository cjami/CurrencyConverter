# CurrencyConverter
 
| Screenshot 1 | Screenshot 2 |
| --- | --- |
|![Screenshot1](/screenshots/screen_shot1.png)|![Screenshot2](/screenshots/screen_shot2.png)

CurrencyConverter is a simple multi-module app that handles live currency conversions.

Modules are set up with the following dependency structure:


**App -> Core, Features**

**Features -> Core**

**Core -> Data, DataLocal, DataRemote**


The app polls for currency updates every second and updates the currency list accordingly.

The app features offline functionality.

The app is unit tested and has a simple integration/instrumented test that demonstrates the use of a MockWebServer and appropriate idling of RxJava calls.
