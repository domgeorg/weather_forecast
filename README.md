# Weather Forecast Android App

### This repo shows a simple Model-View ViewModel Architecture with Use Cases to write decoupled, testalble and maintainable code.
## Don't forget to see the *unit test* package [HERE](https://github.com/domgeorg/weather_forecast/tree/master/app/src/test/java/gr/georgiopoulos/weather_forecast)

![Alt text](https://github.com/domgeorg/weather_forecast/blob/master/images/architecture.png "Architecture")

A View (Activity and Fragment) knows about the view-model but the view-model has no idea about the view. The view-model use independent use case components to collect all the necessary data.

### Example
![Alt text](https://github.com/domgeorg/weather_forecast/blob/master/images/specific_architecture.png)

# App walkthrough

### HomeActivity
![Alt text](https://github.com/domgeorg/weather_forecast/blob/master/images/HomeActivity%20screen.png)

### Add a city to database
![Alt text](https://github.com/domgeorg/weather_forecast/blob/master/images/add_city.png)

### Use [Places](https://developers.google.com/maps/documentation/places/web-service/overview) predictions for autocompletion
![Alt text](https://github.com/domgeorg/weather_forecast/blob/master/images/places_predictions.png)

### When the user taps on the cloud he/she can see the hourly weather forecast for the next 5 days using the [Local Weather API](https://www.worldweatheronline.com/developer/api/docs/local-city-town-weather-api.aspx)
![Alt text](https://github.com/domgeorg/weather_forecast/blob/master/images/weather_forecast.png)

# Dependencies

Library|Use
-------|----
androidx.lifecycle|Lifecycle-aware components like ViewModel, LiveData etc
com.afollestad.material-dialogs|fluid, and extensible dialogs API for Kotlin & Android
retrofit|basic type-safe HTTP client for Android
gson|basic serialization/deserialization library to convert Java Objects into JSON and back
androidx.room|basic persistence library that provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite
picasso|basic image downloading and caching library for Android
places|provides programmatic access to Google's database of local place and business information, as well as the device's current place
mockito|basic mocking framework for unit tests
 
