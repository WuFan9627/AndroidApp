
use Starbucks;


create type StarbucksType as {
    id: string,
    location: point
};

create type FollowersType as {
    user_id: string,
    twitter_id: string,
    followers: [string]
};

create dataset Starbucks(StarbucksType)
    primary key id;

create dataset Followers(FollowersType)
    primary key user_id;


create type TwitterUser as closed {
    id: int64,
    id_str: string,
    screen_name: string
};

create type Tweet as{
    id:int64,
    user: TwitterUser
};

create dataset Tweets (Tweet)
primary key id;


create feed FollowersFeed with{
    "adapter-name": "socket_adapter",
      "sockets": "127.0.0.1:10008",
      "address-type": "IP",
      "type-name": "FollowersType",
      "format": "adm"

};
connect feed FollowersFeed to dataset Followers;
start feed FollowersFeed;




   upsert into Starbucks
   ([
   {"id":"001", "location":point("32.67918392,-117.5")},
   {"id":"002", "location":point("32.8732152,-117.2192960")},
   {"id":"003", "location":point("32.40427696,-117.00795969")},
   {"id":"004", "location":point("32.8732153,-117.2192961")}
   ]);


   create function StarbucksFriends(user_id)
   {
   let p = (select value t.place.bounding_box.coordinates[0][0]from Tweets t),
   p2 = (select value f.place.bounding_box.coordinates[0][0]from Tweets f),
   sb = (select value s.location from Starbucks s where spatial_intersect(create_circle(create_point(p[0][0],p[0][1]),100.2),s.location)limit 1),
   followers = (select * from Tweets f where f.user.id_str in
        (select value fw.followers from Followers fw where fw.twitter_id=user_id  )[0] and spatial_intersect(create_circle(create_point(p2[0][0],p2[0][1]),5.0),create_circle(create_point(p[0][0],p[0][1]),50.0)))
   	select t, followers, sb from Tweets t
   	where (datetime_from_unix_time_in_ms(bigint(t.timestamp_ms)) + day_time_duration("PT3S") < current_datetime())and t.user.id_str = user_id
   };

   create repetitive channel nearbyTweetChannel using StarbucksFriends@1 period duration ("PT30S");
   create broker brokerA at "http://www.notifyA.com";
   subscribe to nearbyTweetChannel("990302026805342209") on brokerA;



      create feed TwitterFeed with {
            "adapter-name": "socket_adapter",
            "sockets": "127.0.0.1:10001",
            "address-type": "IP",
            "type-name": "Tweet",
            "format": "json"
          };

          connect feed TwitterFeed to dataset Tweets;
          start feed TwitterFeed;
