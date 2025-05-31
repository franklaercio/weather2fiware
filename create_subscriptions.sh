#!/bin/sh

# Função para criar subscription
create_subscription() {
  SERVICE=$1
  SERVICE_PATH="/$1"

  echo "Creating subscription for service: $SERVICE"

  curl -X POST http://orion:1026/v2/subscriptions \
    -H "Content-Type: application/json" \
    -H "Fiware-Service: $SERVICE" \
    -H "Fiware-ServicePath: $SERVICE_PATH" \
    -d '{
      "description": "Notify QuantumLeap of '"$SERVICE"' data",
      "subject": {
        "entities": [
          {
            "idPattern": ".*",
            "type": "'"$SERVICE"'"
          }
        ]
      },
      "notification": {
        "http": {
          "url": "http://quantumleap:8668/v2/notify"
        },
        "attrs": ["value", "timestamp", "city", "createdAt", "updatedAt"],
        "metadata": ["createdAt", "updatedAt"]
      },
      "throttling": 1
    }'

  echo "\n"
}

# Lista de serviços
for SERVICE in precipitation temperature relativeHumidity rain
do
  create_subscription $SERVICE
done
