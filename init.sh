#!/bin/sh
export ECS_INSTANCE_IP_ADDRESS=$(curl --retry 5 --connect-timeout 3 -s 169.254.169.254/latest/meta-data/local-ipv4)
export ECS_TASK_NON_SECURE_INSTANCE_PORT=$(cat ${ECS_CONTAINER_METADATA_FILE} | grep HostPort | awk -F ": " '{print $2}' | sed 's/,$//g')
echo 'ECS_INSTANCE_IP_ADDRESS'
echo $ECS_INSTANCE_IP_ADDRESS
echo 'ECS_TASK_NON_SECURE_INSTANCE_PORT'
echo $ECS_TASK_NON_SECURE_INSTANCE_PORT
ip -a addr
export HOST_MACHINE_IP=$(ip -a addr | awk '{match($0,/172+\.[0-9]+\.[0-9]+\.[0-9]+/); ip = substr($0,RSTART,RLENGTH); print ip}' | sed '/^[[:space:]]*$/d')
echo 'HOST_MACHINE_IP'
echo $HOST_MACHINE_IP
java -jar product-management-0.0.1-SNAPSHOT.jar -DHOST_MACHINE_IP=$HOST_MACHINE_IP
