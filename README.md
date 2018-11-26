# AndroidApp
## Introduction
* [click to watch demo](https://www.youtube.com/watch?v=jEFvWzdaVEM&t=2s)
* This APP demonstrates Big Active Data (BAD) concept by utilizing the continuous query and data ingestion capabilities of Asterix DB.
* It offers a notification service to users when their Twitter friends are nearby so they can meet at a nearby coffee shop.Users can subscribe to a channel created by Broker (i.e. Followers/Followees). When followers or followees are approaching a user’s location, Broker sends a notification to users and displays them on the mobile map. The application also marks the coffee shops (Starbucks) nearby. Thus, users can send a message through the application and invite friends to meet at a coffee shop.
* Architecture
![Screenshot](https://github.com/WuFan9627/AndroidApp/Starbucks Meeting App.pdf)

## Environment Setup
#### AsterixDB
* [Click to Download](https://cwiki.apache.org/confluence/display/ASTERIXDB/Creating+a+BAD+Cluster+of+AsterixDB)
* Configuration:

#### Broker
* [Click to Download](https://bitbucket.org/yusufsarwar/badbroker/overview)
* Configuration:
1. BADBroker.py: If you are deploying in your local computer, make sure that the brokerIPAddr and brokerPort is "localhost" and "8989"
2. BADWebServer.py: Make sure the application is listening to the same port that set in BADBroker.py
3. notifier/android.py: get gcmRegistrationToken from android code and get gcmAuthorizationKey from firebase console
#### Twitter
* Create a developer account in twitter developer console
* Save the timeline tweets in local file:
```
twurl /1.1/statuses/home_timeline.json?count=1 > tweets.json
```
* Make sure that the file name is the same with the one in socket adapter.
* Using script in ddl directory to get tweets automatically.

## Android related

## Future improvement

