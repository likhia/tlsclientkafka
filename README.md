###  How to run the sample 
 
* Step 1:  Edit the environment variables in 1.generateCert.sh.
  * PROJECT - Name of the Openshift Project with the Kafka Cluster
  * KAFKA_CLUSTER - Name of the Kafka cluster
  * TLS_LISTENER - Name of the TLS Listener
  * USER - Name of the KafkaUser 

* Step 2: ./1.generateCert.sh

* Step 3: ./2.compile.sh 

* Step 4: ./3.runConsumer.sh

* Step 5: Use another terminal to run 4.runProducer.sh
