# AndroidApp
## Introduction
* [click to watch demo](https://www.youtube.com/watch?v=jEFvWzdaVEM&t=2s)
* This APP demonstrates Big Active Data (BAD) concept by utilizing the continuous query and data ingestion capabilities of Asterix DB.
* It offers a notification service to users when their Twitter friends are nearby so they can meet at a nearby coffee shop.Users can subscribe to a channel created by Broker (i.e. Followers/Followees). When followers or followees are approaching a user’s location, Broker sends a notification to users and displays them on the mobile map. The application also marks the coffee shops (Starbucks) nearby. Thus, users can send a message through the application and invite friends to meet at a coffee shop.
* 
![Architecture](https://github.com/WuFan9627/AndroidApp/blob/master/Starbucks%20Meeting%20App.pdf)

## Environment Setup
#### AsterixDB
* [Click to Download](https://cwiki.apache.org/confluence/display/ASTERIXDB/Creating+a+BAD+Cluster+of+AsterixDB)

#### Broker
* [Click to Download](https://bitbucket.org/yusufsarwar/badbroker/overview)
* Configuration:
1. BADBroker.py: If broker is deployed in local computer, make sure that the brokerIPAddr and brokerPort is "localhost" and "8989"
2. BADWebServer.py: The application should listen to the same port that set in BADBroker.py
3. notifier/android.py: Get gcmRegistrationToken[Tutorial](https://firebase.google.com/docs/cloud-messaging/android/client) and gcmAuthorizationKey from firebase console [Tutorial](https://developer.clevertap.com/docs/find-your-fcm-sender-id-fcm-server-api-key).

#### Twitter
* Create a developer account in twitter developer console
* Save the timeline tweets in local file using following commend: [Timeline API Tutorial](https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-user_timeline.html)
```
twurl /1.1/statuses/home_timeline.json?count=1 > tweets.json
```
* Make sure that the file name is the same with the one in socket adapter(ddl).
* Running ddl in AsterixDB console to get tweets automatically.

## Main function in Android
* ChannelsActivity.java & ChannelsAdapter: subscribe & unsubscribe
* FollowersActivity.java & FollowersAdapter: show all the followers
* LoginActivity.java: register and login 
* MapsActivity.java: show current location
* MyFirebaseMessagingService: get message from server using firebase service
* OKHttpUtil.java: containing broker functions
* ShowInfo.java: show friends'location on map

## Future improvement
Currently the location of friends' is showed after user clicks on the notification. An improvement is to show the real-time location of friends on the homepage map.


