# TwitterCodeChallenge
Weather app for Twitter. 

# Tweeteer weather

## What is Included ?
1. Demo Video
- Shows functionality of opening TWeather application and loading screen
- Display the following current conditions:
    ○ Temperature in Celsius and Fahrenheit
    ○ Wind speed.
    ○ A cloud icon if the cloudiness percentage is greater than 50%.
- Button provided to fetch 5 days weather and display its calculated standard deviation 

2. Project code ZIP folder with README included in it.

## How to load project in Android studio
- Step 1 : Download the TwitterCodeChallenge2.zip folder
- Step 2 : Extract Zip folder
- Step 3 : Open Android Studio
- Step 4 : select option “Open an Existing Project”
- Step 5: Select TwitterCodeChallenge2 > build.gradle file
This may take couple of minutes to load and index the project

## How to run the application on Device/Simulator
Prerequisite :
Project is loaded in Android studio
Debug android device is connected to your computer or Simulator is running
- Step 1 : In top right corner of Android studio click on “Run app”

## How to run APK without Android studio setup
Prerequisite : You need to have ADB installed on your computer
- Step 1 : download APK on your computer and navigate to the folder in your terminal
- Step 2 : run the following command
```bash
adb install -r -f build/outputs/apk/debug/app-debug.apk
```

## Points to Highlight
1. Project completed in both Kotlin & Java uses Jetpack libraries
2. Using MVVM architecture
3. Handling configuration changes using ViewModel lifecycle
4. Used DataBinding and ViewBinding
5. Used RxAndroid and Retrofit2 to make API calls
6. Used LiveData to automatically refresh the UX when data gets modified
7. Good code review practice with inline comments
8. Added Logs to follow the flow from log perspective

## What is next ?
1. Better UX – Design improvements
2. Application can be designed with great UX
3. Internationalization to support multiple language
4. Error handling for better user experience (wifi not connected and handle other scenarios)
3. Provide user user preferences for °C/°F
4. Other features can be added – 5 days weather trend etc
5. Add multiple logs throughout so it can become production ready
6. Unit tests, Instrumentation tests, Automation tests.