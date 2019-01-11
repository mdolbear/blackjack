#Mongo
kubectl create -f mongo-persistent-volume.yml
kubectl create -f mongo-pv-claim.yml
kubectl create -f mongo-deployment.yml
kubectl create -f mongo-service.yml

#Blackjack service
kubectl create -f blackjack-deployment.yml
kubectl create -f blackjack-service.yml

#Ingress
kubectl create -f igctl-default-backend-svc.yml
kubectl create -f ingress-controller.yml
kubectl create -f ingress.yml

#Prometheus
kubectl create namespace monitoring
kubectl create -f prometheus/prom-config-map.yaml
kubectl create -f prometheus/prom-deployment.yaml

