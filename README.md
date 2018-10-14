# blackjack
This is the first project I have tried with Spring-Boot running on top of a Mongo database. Its
a blackjack game (with some simplified rules because I have never played cards in my life). The persistence store
contains two separate collections, and I am using application.properties to point to the mongo db. I didn't use
the embedded mongo because I wanted get familiar with mongo and its tools.

To build:

mvn clean install -Pbuild-docker-image


To pull docker down to your local Docker:

```docker pull mongo```
```docker run -p 27017:27017 mongo```


#This configuration supports a kubernetes cluster. Do the following to load the kubernetes cluster:

cd ~

Create k8s artifacts for mongo:

```kubectl create -f code/blackjack/src/main/resources/k8s/mongo-persistent-volume.yml```

```kubectl create -f code/blackjack/src/main/resources/k8s/mongo-pv-claim.yml```

```kubectl create -f code/blackjack/src/main/resources/k8s/mongo-deployment.yml```

```kubectl create -f code/blackjack/src/main/resources/k8s/mongo-service.yml```



After running the above, the url for mongo will be mongodb://mongo:27017/db

Now the k8s for the blackjack service:

```kubectl create -f code/blackjack/src/main/resources/k8s/blackjack-deployment.yml```

```kubectl create -f code/blackjack/src/main/resources/k8s/blackjack-service.yml```


Now the application will be available at http://LoadBalanceIngress:nodePort

Find LoadBalanceIngress:nodePort from the following commands

#Example:

```kubectl get services```

NAME         TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE

blackjack    NodePort    10.109.213.48   <none>        8080:31266/TCP   11m

kubernetes   ClusterIP   10.96.0.1       <none>        443/TCP          15m

mongo        ClusterIP   10.108.68.55    <none>        27017/TCP        12m


```kubectl describe services blackjack```

Name:                     blackjack
Namespace:                default
Labels:                   app=blackjack
                          tier=backend
Annotations:              <none>
Selector:                 app=blackjack,tier=backend
Type:                     NodePort
IP:                       10.109.213.48
LoadBalancer Ingress:     localhost
Port:                     <unset>  8080/TCP
TargetPort:               8080/TCP
NodePort:                 <unset>  31266/TCP
Endpoints:                10.1.0.144:8080
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>



#Now play the game:

Play a game of black jack

Create card deck:
```curl -X POST http://localhost:31266/blackjack/createCardDeck```
cardDeckId=5bc271f860d9ea00090b9ed1


Start a new game:
```curl -d "numberOfPlayers=2" http://localhost:31266/blackjack/startgame```

gameId=5bc2721a60d9ea00090b9ed4

Get game state

```curl http://localhost:31266/blackjack/gameState?gameId=5bc2721a60d9ea00090b9ed4```

Play a hand:

```curl -d "cardDeckId=5bc271f860d9ea00090b9ed1&gameId=5bc2721a60d9ea00090b9ed4" -X PUT http://localhost:31266/blackjack/playHand```

Keep playing hands until the game terminates.

