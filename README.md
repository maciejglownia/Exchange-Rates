# Exchange Rates

### Goal
Create an application to display exchange rates from [fixer.io](https://fixer.io/) API.

### Architecture
![Architecture](app/src/main/res/drawable-v24/architecture_mvvm.jpg)

### Screenshots

[//]: # (![Main Screen]&#40;app/src/main/res/drawable-v24/screen_main_screen.jpg&#41;)

[//]: # (![Single value]&#40;app/src/main/res/drawable-v24/screen_single_data.jpg&#41;)

[//]: # (![No internet]&#40;app/src/main/res/drawable-v24/screen_no_internet.jpg&#41;)

### Short Overview
- Application retrieving data from API,
- Displaying a list of exchange rates for current day,
- User can scroll it down until reach end of data for current day. 
- Then a new data from previous day is displaying. 
- This works like that until it reach the oldest day available in API (year: 1999) 
- User is able to see details of single item by click on it.

### What I have used here
- MVVM,
- Rest API
- Retrofit2,
- OkHttp3,
- LiveData,
- ViewBinding,
- Navigation Component,
- RecyclerView,
- XML,
- GSON.

### Thank you
- [Florian Walther](https://codinginflow.com/)
- [Philipp Lackner](https://pl-coding.com/)
- [Miro - architecture diagram](https://miro.com/)

### Dear Visitor
If you see an opportunity to improve my code do not hesitate to contact me:
maciej.k.glownia@gmail.com. If you want to copy it and develop with your own idea, take it and enjoy
your learning path.
