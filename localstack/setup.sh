#!/bin/sh
echo "Criar queue localstack"

awslocal sqs create-queue --queue-name queue-hot
awslocal sqs create-queue --queue-name queue-throttling
