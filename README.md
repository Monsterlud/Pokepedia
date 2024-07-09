# Pokepedia (Douglas Monsalud)

**Pokepedia** is a simple app that downloads a list of Pokemon from the official PokeApi. When you 
launch the app the first 10 Pokemon are loaded from the API and displayed in a Jetpack Compose 
LazyColumn. Each Pokemon is displayed in a Card with the ID, NAME, and an image of the Pokemon.

Clicking the card takes you to the detail screen, which contains additional information (e.g. 
weight, height, etc). 

## Discussion of Architecture, Frameworks, and Libraries
For this app I chose to use some more modern Android Frameworks and Libraries. For the UI I am
using Jetpack Compose and Material 3 components. I also imported fonts using Google Fonts and am 
using Coil to load the images using AsyncImage. I am using Compose Navigation for navigation purposes. 

For interacting with the API I chose to use Ktor as an HTTP Client and for serialization. I am also
using Google Gson for this application.

For local storage I implemented a simple Room Database and DAO. The repository gets the API 
download of Pokemon and immediately saves it to Room. Then, the UI grabs that list, utilizing a
unidirectional data flow using Kotlin Flows.

For dependency injection I used Koin, which has a module defined and is started in the Application
class. 

For testing I added a few libraries such as MockK, Espresso, and KotlinX Coroutines Test.

![pokepedia_main_screen](images/pokepedia_main_screen.png)
![pokepedia_details](images/pokepedia_details.png)

For architecture I tried to keep a separation of concerns and a clean delineation between layers.
In this app I have Presentation (Composables, ViewModel), Domain(Model, Repository Interface), and 
Data (RepositoryImpl, LocalDataSource and RemoteDataSource Interfaces). One layer down from there
I have DataSource, which contains the LocalDataSourceImpl, RemoteDataSourceImpl, and Room 
Implementation.

## Instructions for running Pokepedia
After opening the app with an IDE (Android Studio or IntelliJ) make sure these settings are used:
Java 17, Gradle Plugin Version 8.4.2, and Gradle Version 8.6.