#!/bin/bash

## Please remember to use oc login to your Openshift Cluster BEFORE running this script. 

## Name of the Openshift Project with the Kafka Cluster
export PROJECT=kafka

## Name of the Kafka cluster
export KAFKA_CLUSTER=sample

## Name of the TLS Listener
export TLS_LISTENER=tls

## Name of the KafkaUser
export USER=my-user


rm external.lb.url
rm ca.crt
rm user.p12
rm user.password



oc project $PROJECT

## Get URL of external load balancer
oc get service $KAFKA_CLUSTER-kafka-$TLS_LISTENER-bootstrap -o=jsonpath='{.status.loadBalancer.ingress[0].hostname}{"\n"}'  > external.lb.url

## Get the current certificate for the cluster CA
oc get secret $KAFKA_CLUSTER-cluster-ca-cert -o jsonpath='{.data.ca\.crt}' | base64 -d > ca.crt

## Get the PKCS #12 archive file for storing certificates and keys
oc get secret $USER -o jsonpath='{.data.user\.p12}' | base64 -d > user.p12

##Get the Password for protecting the PKCS #12 archive fil
oc get secret $USER -o jsonpath='{.data.user\.password}' | base64 -d > user.password

keytool -keystore truststore.p12 -storepass welcome1 -noprompt -alias ca -import -file ca.crt -storetype PKCS12;

keytool -importkeystore -destkeystore keystore.p12 -srckeystore user.p12 -srcstorepass $(cat user.password) -srcstoretype PKCS12 -deststoretype PKCS12 -destkeypass welcome1 -deststorepass welcome1;




