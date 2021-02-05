# Food Deli REST API

![Java CI with Maven](https://github.com/adjurk/MPR-20498/workflows/Java%20CI%20with%20Maven/badge.svg)

Simple Java REST API for MPR classes @ PJAIT 2021.

## Running

### With Maven

Prerequisites:

- JDK 1.8
- Maven

```bash
mvn package
java -jar food-deli-0.0.1-SNAPSHOT.jar
```

### With Docker

Prerequisites:

- Docker

```bash
docker image build -t food-deli:0.0.1 .
docker run -p 8080:8080 food-deli:0.0.1
```

## Example endpoints

Run the app first and use Postman to reach these endpoints. You can also import Postman collection file `food-deli.postman_collection.json` at repo root.

### `POST /customer`

```json
{
    "firstName": "Adam",
    "lastName": "Jurkiewicz",
    "address": "Subisława, Gdańsk"
}
```

### `POST /restaurant`

```json
{
    "name": "First Restaurant",
    "address": "Podwale Staromiejskie 51 Gdańsk",
    "open": true,
    "foods": [
        {
            "name": "French Fries",
            "cost": 12
        },
        {
            "name": "Vegan Bowl",
            "cost": 20
        }
    ],
    "maxDistance": 12,
    "deliveryCost": 6.0
}
```

### `POST /food-order`

```json
{
  "orderItems": [
    {
      "id": 1,
      "name": "French Fries",
      "cost": 12.0
    }
  ],
  "restaurant": {
    "id": 1,
    "name": "First Restaurant",
    "address": "Podwale Staromiejskie 51 Gdańsk",
    "open": true,
    "maxDistance": 12.0,
    "deliveryCost": 6.0
  },
  "customer": {
    "id": 1,
    "firstName": "Adam",
    "lastName": "Jurkiewicz",
    "address": "Subisława, Gdańsk"
  }
}
```
