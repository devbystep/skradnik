This project is similar to:

* front-end client for android [google play](https://play.google.com/store/apps/details?id=by.skarnik.smolik)
* front-end web [skarnik.by](http://www.skarnik.by)

~~My old implementation of server+web client currently is available on [github](https://github.com/minsler/skarnik.by)~~ in the 'wwww-server-client' directory.

##Start server(embedded Jetty) from root directory##

    ./gradlew :www-server-client:jettyRun

or

    ./gradlew :www-server-client:jettyRunWar

This command runs jetty on http://localhost:9000/

##To stop jetty please use:##

    ./gradlew :www-server-client:jettyStop


