# Star War Search


## INTRODUCTION

The case study project is a simple search Android application that integrates with the open Star Wars APIs to search characters from the Star Wars universe. The application has two main areas: 

1. Character Search and;

2. Character Detail (which is later divided to sub-areas, please see below)

The Android application supports Android devices between API levels 19 and 29 (the latest API level). The support goes as low as API 19 in order to have a large Android device support coverage for about 99% of Android-OS powered devices worldwide.
The application is built to be design responsive and to scale across multiple devices with varying screen density buckets. Its design comes in handy with the master-detail UI pattern in order to maximize screen spaces for landscape device orientation and on devices with bigger screen areas.
In a bid to improve the application user experience, the application is built to hold fetched API data in a local, light-weight cache that is constantly refreshed on search term change - this means that the data that was previously fetched from the API need not be fetched again if the user demands the same data later. With this capability, the application navigation becomes smoother and faster.

## DESIGN AND USER FLOW
As stated above, the application has two main areas: character search and character detail. The character detail area is further broken down to the movies and species sub-areas.
These sub-areas are accessible from the main area (character detail).

This further breakdown was hugely influenced by the thirst for application scalability and good user experience while searching characters with the application.
__(Please see the figure below for more clarity)__

In order to explain to context, let us consider what a typical Star Wars character is and how it is modeled from the API side. Amongst other properties of a character are the films (the movie(s) the character appeared in) and the specie of the character. These properties are modeled as arrays. By programming implications, this means the properties (films and species) are growing properties whose size will change over the time (when the data is updated).
If these properties grow infinitely (e.g. say a character is famous and has appeared in over 100 movies and will yet appear in 50 movies more), an attempt to load all the details of this character on a single screen will lead to many issues. Among them are:

1. **Long data (detail) loading time** which is an equivalent of poor user experience because the user will have to wait to loop through the entire film and specie arrays (e.g. each of array size 50 - considering when the character data increases infinitely in the future) that contain URL strings to these resources on the server. This means the number of API calls to make in such a scenario is 100 (50 for each URL string item in the specie array to fetch the specie detail, 50 for each URL string item in the film array to fetch the movie detail). That’s a lot of waiting time on the user! Consider a user with a poor internet connectivity too - that may take forever!

2. **Long detail screen to scroll through**. Design research shows that users are easily pissed off with poorly arranged or disorganized data. This does not leave out long scrolling as well.

The aforementioned possible issues influenced the decision to further breakdown the character detail screen to delegate some character detail to the movie and specie screens. The importance of this is that the movie and specie screens are reusable beyond the character search feature covered by the scope of this case study. The Star Wars API has other roots apart from the “/people” path. If later the planet search feature will be included in the application, the incorporation will only entail throwing intents to these screens to display the appropriate data.
This breakdown, sort of, makes the application design ready for future integrations or feature addition - hence a scalable application (in this regard).

## ARCHITECTURAL APPROACH AND DESIGN PATTERN
This application is built following the Clean Archtecture convention. This is to ensure, among others, that the application code is:
1. independent of frameworks and tools
2. separated into logical concerns for good testability and maintainability
3. robust, flexible and scalable

In order to ensure the aforementioned, the application code is divided into three (3) layers:
1. Presentation Layer (employing the Model-View-ViewModel Architectural pattern)
2. Domain Layer
3. Data Layer (employing the Repository pattern)

### 1. Presentation Layer
This layer is responsible for the UI logic and handling of user interactions. The view (i.e. activity or fragment) notifies the viewmodel (the Android Architectural Component’s ViewModel class is used here as the MVVM’s viewmodel) of the user’s interaction, the viewmodel modifies the view’s underlying model and the viewmodel, in turn, updates the view with the appropriate state data.

To ensure that the viewmodel and the view are not tightly coupled (i.e. the viewmodel not holding reference to the view, or whatsoever), the State LiveData is exposed from the viewmodel for the view to observe for any state change. This is made possible by the Observer Pattern, i.e. when there is a state change based on user’s interaction, the viewmodel sets the value of the State LiveData with the new state data and the view logic is executed to render the new state on the UI.
Each UI state is modeled by an immutable data class, this is to cut off multiple LiveData objects representing the view state. This approach helps for view state consistency and unidirectional data flow.

Every ViewModel class in the codebase extends the BaseViewModel. The base class manages the state data manipulation when triggered by the emit function in any of the BaseViewModel subclasses. This trigger calls for a state reduction (i.e. the creation of a new state data object from the old state data object) and the updating of the state LiveData value to a new state. The BaseViewModel performs state reduction by calling the onChangeState function in its subclass. This onChangeState function is the one responsible for creating a new state data object from the old state data object.
Also, the BaseViewModel contains the Action LiveData object. This Livedata notifies the view to perform actions other than UI state update; actions like open dialog, trigger permission request, etc. The Action LiveData is triggered by the performAction function.
All the UI actions that can be performed are represented with Kotlin sealed classes.

The presentation layer communicates with the domain layer using the Interactors (represented in the codebase as Usecases) - i.e. data transfer operations are initiated by the viewmodel using the usecases. The viewmodel in this codebase leverages the Android ViewModel class’ Coroutine ViewModelScope to manage data transfer operations that are lifecycle bounded. This helps for a better management of system resources (memory, processor, battery life, etc.)
For instance, a certain screen A needs a list of character objects and the viewmodel signals to the domain layer in the form of a data request. Accidentally or willingly, the user navigates from screen A while the data is loading. Note that the viewmodel already initiated a data request. By the time the data request is successful, the data is already stale because it is no longer needed by the user - this is a loss of resources and processing time.
How do we cut down on this resource loss?
In this application, once the user exits a particular screen, the Android system calls the onCleared function on the ViewModel associated with the screen. This in turn terminates all the children coroutine operations running within the ViewModelScope (e.g. cancels the initiated data request) - this saves system resources alot. This cancellation attribute of Kotlin Coroutines is known as Structured Concurrency and this application leverages this attribute so as to be system resource prudent and conservative. 

### 2. Domain Layer
This layer houses the business logic for the application. It is responsible for the data inflow and outflow into and out of the presentation layer. This application models operations in units as Usecases. E.g. fetch character list usecase, fetch planet detail usecase, etc.
The domain layer is connected to other layers via interfaces and data exchange between them is standardized to use a return type of Either<Failure, T> where T is the data type.
This return type is adopted from Functional Programming where either a data object or an instance of the Failure class is returned for any given operation.
The domain layer communicates with the data layer via the repository interface. 

### 3. Data Layer
This layer houses the application data and exchange protocols. It consists the repository, local data source and remote data source.
The repository has a dependency on the local and remote data sources. These sources are the data entry and exit from the application and they are managed by the repository. 
The local data source in this application is responsible for the light caching of the character, specie and planet data, so as to avoid fetching the same data from the API again and again. This makes the application to be faster. The light caching is achieved using the Dagger Scoping (this will be discussed in the third-party library section).
(Please note that data caching is only valid until the application process death. Local database persistence is not used in this application, so data only persists as long as the application process is running)
The remote data source is delegated to interface with the network service (API). it uses the Retrofit library to exchange data with the Star Wars API.

Same as the domain layer, the data layer transmits data using the Either<Failure, T> return type as well.

Other design patterns used in this application that are yet to be mentioned are:

1. **Dependency Injection** (a Creational Pattern)

As an application scales, manually providing dependencies for classes becomes more difficult and strenuous - especially when the same type of objects are recreated over and over at different instances. In this application, depedency construction and injection are delegated to the Dagger library.

2. **Facade** (a Structural Pattern)

This pattern, for instance, is used when the remote data source class calls the Retrofit client to request a data transfer with the API. The remote data source class is not aware of what goes on under the hood, e.g. how the retrofit instance is built, it merely waited for an API call response.

The entire application architecture relies heavily on Kotlin Coroutines. This is because Coroutines are light-weight threads to perform asynchronous operations in a synchronous flow/manner and they are cheaper and faster to construct and destroy when compared with the native OS threads. They help eliminate the verbosity of (nested) callbacks during asynchronous operations.
One of the reasons why Coroutines is preferred over RxJava in this project for asynchronous operations is because it has APIs native to the language of development (Kotlin).
Personally, in development, I prefer platform APIs compared to an alternative Third party library. This makes me worry less about the library maintainability (among other things) - whether it will still be maintained in the next couple of years or it will reach the end of life. For instance, according to some sources, RxJava is currently facing maintainability issues. For projects that are heavily dependent on RxJava, if not refactored to use another library to manage reactivity, they might reach their end of life.
However, there are instances where I prefer third-party libraries to the platform libraries. A typical example is the Bottom Navigation view.

## THIRD-PARTY LIBRARIES
Asides other platform libraries used in this application, a couple of other third-party libraries are used and they are as follows:

1. **Multidex**

This is a library that provides the support for an Android application to exceed the 64K methods limit per Android app file (APK). This is necessary for an application that will scale and have more functions added to it in the future.

2. **Dagger**

Dagger is (as explained above) a compile-time dependency management framework. This application, with the help of the Dagger library, uses dependency injection to provide class dependecies across the codebase. Dagger helps generate an equivalent of manual object creation code at compile time and make these dependencies available for use by annotating the appropriate class with the @Inject annotation. This helps eradicate manual dependency injection and lots of lines of code for dependency objects creation.
Using Dagger scoping, the repositories in this application are scoped to the application lifetime. This means a single instance of the repository classes are injected by Dagger every time, so far the app is alive. Hence, data can be persisted in the repository classes’ fields for usage throughout the application lifetime.

3. **Retrofit**

Retrofit is a type-safe REST client for Android - for authenticating and interacting with web service APIs. Retrofit is chosen for this project because of its handy and out-of-the-box support for Kotlin Coroutines. This makes network calls to be executed without callbacks.

4. **Gson**

Gson is a library for serializing and deserializing objects to and from JSON string. It is used in this project while exchanging data with the Star Wars API.

5. **OkHttp Interceptor**

The logging interceptor library is used in this application to log the data being exchanged between the application (client) and the server on the logcat. This is only enabled for the app in DEBUG mode.

6. **Calligraphy**

Calligraphy is a font styling library for applying custom font in Android applications. It is used in the application to set a custom font for texts.
