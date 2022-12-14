# Exchange Rates

### Goal
Create an application to display exchange rates from [fixer.io](https://fixer.io/) API.

### Currently solution (to test application. You don't need to have an account on fixer.io)
An API the application is currently communicating with is running on a server here: [ktor-exchange-rates](https://github.com/maciejglownia/ktor-exchange-rates)
To run the application this way you need to do following steps:
1. Clone this repo or download ZIP (open in Android Studio),
2. Make sure your BASE_URL in Constants file is correct: 
- http://[BASE_URL]:8080/ 
(to obtain BASE_URL on your PC (Win 11): 
(Start -> cmd -> ipconfig -> Ipv4 ->  number)
- make sure your network is set to private (Win 11): 
(Settings -> Network & internet -> WiFi -> Manage known networks -> Network profile type -> Setup to Private network)
3. Clone or download ZIP [ktor-exchange-rates](https://github.com/maciejglownia/ktor-exchange-rates) (open in IntelliJ IDEA),
4. Run the ktor-exchange-rates server, 
5. Run the Exchange Rates application using an emulator (to run on your mobile device make sure your network is set to private),
6. You are able to get a data from your local server - enjoy!

### Architecture
![Architecture](app/src/main/res/drawable-v24/architecture_mvvm.jpg)

### Screenshots

![Main Screen](app/src/main/res/drawable/screen_main_screen.jpg)
![Details Screen](app/src/main/res/drawable/screen_details_screen.jpg)
![No internet](app/src/main/res/drawable/screen_no_internet.jpg)
![Loading](app/src/main/res/drawable/screen_loading.jpg)

### Short Overview
- Application retrieving data from API,
- Displaying a list of exchange rates for current day,
- User can scroll it down until reach end of data for current day, 
- Then a new data from previous day is displaying,
- This works like that until it reach the oldest day available in API (year: 1999), 
- User is able to see details of single item by click on it.

### What I have used here
- MVVM,
- Rest API,
- Retrofit2,
- OkHttp3,
- LiveData,
- ViewBinding,
- Navigation Component,
- RecyclerView,
- XML,
- Gson,
- Shimmer for Android.

### Thank you
- [Florian Walther](https://codinginflow.com/)
- [Philipp Lackner](https://pl-coding.com/)
- [Miro - architecture diagram](https://miro.com/)

### Dear Visitor
If you see an opportunity to improve my code do not hesitate to contact me:
maciej.k.glownia@gmail.com. 
If you want to copy it and develop with your own idea, take it and enjoy
your learning path.
