Bourbon
=======

<p align="center">
    <img src="images/header.png" alt="Web Icons"/>
</p>

Bourbon is a simple Dribbble client built for Android Mobile, Wear and TV (it's also optimised for tablets). It was built as an experiment for sharing code through a common-code module when using an MVP architectural approach.

Requirements
------------

 - [Android SDK](http://developer.android.com/sdk/index.html).
 - Android [5.0 (API 21) ](http://developer.android.com/tools/revisions/platforms.html#5.0).
 - Android SDK Tools
 - Android SDK Build tools 23.0.2
 - Android Support Repository
 - Android Support libraries

Building
--------

To build, install and run a debug version, run this from the root of the project:

    ./gradlew app:assembleDebug
    
    
Testing
-------

As mentioned the the structure section above, each of the instrumentation tests are split into a seperate module. You can run the instrumentation tests for these modules by running the commands below from the root of the project:


To run instrumentation tests for mobile:

	./gradlew mobile-androidTest:connectedAndroidTest


To run instrumentation tests for wear:

	./gradlew wear-androidTest:connectedAndroidTest


To run instrumentation tests for TV:

	./gradlew tv-androidTest:connectedAndroidTest
