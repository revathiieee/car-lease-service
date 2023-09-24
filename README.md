# car-lease-service
This project deals about car lease management system to manage car, customer and lease service

### Assignment Description

Implement a REST service which receives the car lease contract information for the customer

1. The service should be able to receive the following information:
    * Customer information
    * Car information
    * Lease contract information
2. Calculated Lease rate should be returned to the customer

### Prerequisites & Dependencies
* Java 17
* Maven 3.8.2
* Docker 24.0.6 (Only need when you build docker image)

* Spring Boot 3.0.10
* Spring Cloud 2022.0.4

### Overview

![Car lease Service](leasecar.png)

### How to run the application

#### 1. Run the application using maven
```shell    
    mvn clean install
    mvn spring-boot:run
```
#### 2. Build docker images
```shell
        mvn spring-boot:build-image
```
### Service Ports
| Service Name | Port |
| --- | --- |
| Customer Service | 8091 |
| Car Service | 8092 |
| Lease Service | 8093 |
| Eureka Server | 8761 |
| Gateway Service | 8060 | 



### Authentication Endpoints
| Service Name | URL |
| --- | --- |
|Lease Service | http://localhost:8093/authenticate |

### Authentication Credentials
| Username | Password |
| --- | --- |
| admin@lease.com | adminpwd |
| broker@lease.com | brokerpwd |

### JWT Token

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2NhcmxlYXNlLmNvbSIsInN1YiI6ImJyb2tlckBsZWFzZS5jb20iLCJleHAiOjE2OTU1OTEwNTV9.vc2NTtaAdFHk7iiqXzLKlLXEaRqu8IRbV7JaspYlGbA"
}
```

### Swagger UI
| Service Name | URL |
| --- | --- |
| Customer Service | http://<customer-service>:8091/swagger-ui/index.html |
| Car Service | http://<car-service>:8092/swagger-ui/index.html |
| Lease Service | http://<lease-service>:8093/swagger-ui.html |

### Gateway Service
| Service Name | URL |
| --- | --- |
| Gateway Service - Customer | http://localhost:8060/customers/
| Gateway Service - Car | http://localhost:8060/cars/leaserate
| Gateway Service - Lease - Broker | http://localhost:8060/lease

### Customer Service
| Method | URL | Description |
| --- | --- | --- |
| GET | http://localhost:8091/customers/ | Get all customers |
| GET | http://localhost:8091/customers/{id} | Get customer by id |
| POST | http://localhost:8091/customers/ | Create customer |
| PUT | http://localhost:8091/customers/{id} | Update customer |
| DELETE | http://localhost:8091/customers/{id} | Delete customer |

### Car Service
| Method | URL                                  | Description            |
| --- |--------------------------------------|------------------------|
| GET | http://localhost:8092/cars/          | Get all cars           |
| GET | http://localhost:8092/cars/{id}      | Get car by id          |
| POST | http://localhost:8092/cars/          | Create car             |
| PUT | http://localhost:8092/cars/{id}      | Update car             |
| DELETE | http://localhost:8092/cars/{id}      | Delete car             |
| POST | http://localhost:8092/cars/leaserate | Lease rate calculation |

### Lease Service
| Method | URL                                 | Description               |
|--------|-------------------------------------|---------------------------|
| POST   | http://localhost:8093/authenticate/ | Get JWT Token             |
| POST   | http://localhost:8093/lease/        | Get Car Lease Information |

#### Improvements and yet to do
* Add swagger documentation for all services
* Add docker compose file to run all services
* Add Integration test cases. Note: I have added integration test for customer service.

### Happy Coding !!!