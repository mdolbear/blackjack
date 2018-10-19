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

#Display k8s dashboard. 

This is an alternative to using kubectl: Instructions here: https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/

kubectl create -f https://raw.githubusercontent.com/kubernetes/dashboard/master/src/deploy/recommended/kubernetes-dashboard.yaml

kubectl proxy --port=8090

Now bring up a browser and go to http://<master-ip>:<apiserver-port>/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/

Example: http://localhost:8090/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/

#Remote debug a container in k8s cluster:

These changes are required in Dockerfile:

EXPOSE 4000
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,address=4000,suspend=n"


These changes are required in my blackjack-service.yml:

  - name: debug
    port: 4000
    protocol: TCP
    targetPort: 4000
    
Now run

kubectl describe services blackjack

Under the response, pull out

NodePort:                 debug  31539/TCP
LoadBalancer Ingress:     localhost

and set up debugger url to be 

-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=31539

in the Intellij or Eclipse debugger.


#To use Telepresence debugging with K8s: https://www.telepresence.io/

telepresence --swap-deployment blackjack --expose 8080 \
--run java -agentlib:jdwp=transport=dt_socket,server=y,address=4000,suspend=n -Dspring.profiles.active=prod -jar blackjack-1.0-SNAPSHOT.jar

Then you can set local breakpoints on localhost:4000
