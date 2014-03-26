##Project description##

####This project is similar to:####

* front-end client for android [google play](https://play.google.com/store/apps/details?id=by.skarnik.smolik)
* front-end web [skarnik.by](http://www.skarnik.by)

~~My old implementation of server+web client currently is available on [github](https://github.com/minsler/skarnik.by)~~ in the 'skradnik-web' directory.


###Currently project is deployed on OpenShift platform.###

[skarnik-minsler.rhcloud.com](http://skarnik-minsler.rhcloud.com/)


##To build project##
*android client is disabled for now*

Unix

     ./gradlew clean build

Windows

     gradlew.bat clean build


##Start server(embedded Jetty) from root directory##

    ./gradlew :skradnik:jettyRun

or

    ./gradlew :skradnik-web:jettyRunWar

This command runs jetty on http://localhost:9009/

##To stop jetty please use:##

    ./gradlew :skradnik-web:jettyStop

## Project structure ##

### Gradle modules ####
* *skradnik-shared* - module for common libraries for server and android parts
* *skradnik-web* - mudule for server parts
* *skradnik-droid* - module for android

To add new project please configure *settings.gradle*

### Gradle related files ###
* *build.gradle* - main build file(names of artifacts, configuration plugins)
* *gradle.properties* - properties for gradle tool(run as daemon, jvm args, parallel mode)
* *setting.gradle* - for configuration project with multi modules
* *gradle* - gradle wrapper files
* *gradlew* and *gradlew.bat* - gradle wrapper. It allow build project without installation Grade



[![Build Status](https://drone.io/bitbucket.org/devbystep/skradnik/status.png)](https://drone.io/bitbucket.org/devbystep/skradnik/latest)
