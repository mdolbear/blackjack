# blackjack
This is the first project I have tried with Spring-Boot running on top of a Mongo database. Its
a blackjack game (with some simplified rules because I have never played cards in my life). The persistence store
contains two separate collections, and I am using application.properties to point to the mongo db. I didn't use
the embedded mongo because I wanted get familiar with mongo and its tools.

This game depends on Mongo, and I didn't want to use Mongo, so make sure you have installed Docker, and 
then you can do the following:

docker pull mongo
docker run -p 27017:27017 mongo
