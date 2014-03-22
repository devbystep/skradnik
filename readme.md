This project is similar to:

* front-end client for android [google play](https://play.google.com/store/apps/details?id=by.skarnik.smolik)
* front-end web [skarnik.by](http://www.skarnik.by)

~~My old implementation of server+web client currently is available on [github](https://github.com/minsler/skarnik.by)~~ in the 'wwww-server-client' directory.

#To deploy server parts#

##Restore postgresql database ##

###Create dababase if not exist###

	createdb -h localhost skradnik

###Restore dump to created database###

	psql -h localhost skradnik -f database/skradnik.sql

###Configure connection to postgresql databaase###

* Find www-server-client/src/main/webapp/WEB-INF/web.xml file

* Edit section related with database connection

##Start server(embedded Jetty) from root directory##

    ./gradlew :www-server-client:jettyRun

This command runs jetty on http://localhost:9000/

##To stop jetty please use:##

    ./gradlew :www-server-client:jettyStop


