# Movies

This is an Android Application written in Kotlin to display a collection of popular movies loaded from Movie db (https://www.themoviedb.org/). 
Using the popular movie db API, you have to create an app which has 2 screens (Popular movies screen, Movie detail screen)
Popular movies screen. Using the get popular movies API, we want you to display the list of movies in a list or grid.
When clicking on a movie item, we should show the movie detail screen using the get movie details API


# Installation
Clone the repo and install the dependencies.

      https://github.com/maquadir/Movies/tree/master

# Architecture and Design
The application follows an MVVM architecture as given below

<img width="449" alt="Screen Shot 2019-12-25 at 8 05 55 AM" src="https://user-images.githubusercontent.com/19331629/71425127-6ca3cc00-26ed-11ea-98b5-a344b54b7050.png">

# Setup
### Manifest File
- Since the app is going to fetch from json url .We have to add the following internet permissions to the manifest file.
    
        <uses-permission android:name="android.permission.INTERNET" />

### Material Styling
- A progress bar is displayed during the async JSON read operation.

### Invoke JSON Url using Retrofit.Builder()
We have declared a Properties API interface to invoke the JSON url using Retrofit.Builder()

         return Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(PropertiesApi::class.java)

### Model
A Model contains all the data classes.
PopularMovies and MovieDetails data classes are created using "JSON to Kotlin class" plugin to map the JSON data to Kotlin. A ApiService class to handle api requests and a repository takes care of how data will be fetched from the api.
                  
              val apiService = ApiService()
              val repository = MoviesRepository(apiService)

### View Model
We set up a view model factory which is responsible for creating view models.It contains the data required in the View and translates the data which is stored in Model which then can be present inside a View. ViewModel and View are connected through Databinding and the observable Livedata.
We are using a shared Viewmodel for the 2 fragment screens.

### Coroutines
Coroutines are a great way to write asynchronous code that is perfectly readable and maintainable. We use it to perform a job of reading data from the JSON url.

### View
It is the UI part that represents the current state of information that is visible to the user.A Recycler View displays the data read from the JSON. We setup a recycler view adapter to take care of displaying the data on the view.
We use Glide to display profile image using view binding. 

### Nav Graph
We are using a navigation graph to navigate between the 2 fragments representing popular movies screen and movie details screen.

### Dependency Injection
Constructor dependency injection has been used at multiple instances.It allows for less code overall when trying to get reference to services you share across classes, and decouples components nicely in general

### View Binding
The View Binding Library is an Android Jetpack library that allows you to create class files for the XML layouts.All the UIView elements in the layout are binded to the class program through view binding.

# Testing
Unit tests for the ApiService, Repository, Controllers & VM.

# Screenshot

![image](https://user-images.githubusercontent.com/19331629/219947035-1862fa9a-3482-4352-8aab-93d46fbeda9a.png)

<img width="321" alt="image" src="https://user-images.githubusercontent.com/19331629/165748603-fcb63536-fa9b-4c40-a077-4c68ac14ceec.png">

![image](https://user-images.githubusercontent.com/19331629/219947053-843c5011-d3bb-4f6e-bf4f-51b31b6a4feb.png)



