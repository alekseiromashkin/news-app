# News App for Aram Meem LLC

Here is my news application, it was quite interesting to run it.<br />
Modularization for such a small application looks like a bit of overengineering for our test task but  if it could be a real project the growth of the team and application functionality will go much easier.<br />
I used the CLEAN architecture but did not do the mapping in the UI model I just used entities from the model layer as I decided that so far it's ok.<br />
As the architecture of the presentation layer I chose MVVM with view state and event messages for the view model.<br />
I also decided to cache data in the database using Room it seemed to me that it is very suitable here.<br />
The caching strategy has been simplified it will take more time to think through a more thoughtful approach.<br />
Also, without caching, error forwarding in the UI works but with caching more time is needed to implement error forwarding along with the cached data.<br />
I used the pull to refresh library so you can use it to restart the request if something goes wrong.<br />
I also wrote several unit tests, made adaptation for different screen orientations, implemented pagination, tested the performance of debug and release builds on android versions 5.1, 7.0, 9.0 , 11.0.<br />
If you have any questions about the implementation please feel free to write me and I'll be happy to answer them.<br />