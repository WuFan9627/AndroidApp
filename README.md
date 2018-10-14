# AndroidApp
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

