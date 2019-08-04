# Solution

This is a springboot maven application. Import this as a maven project in your favourite IDE and run it.

## About the solution -
* Swagger is added to view all the endpoints
* H2 datbase is used to store data. Its a file based storage so data is persisted accross restart.
* JPA is used to communicate with DB
* Junit test cases are written using mockito
* Spring security is used to secure the endpoints
* Lombok API is used to get rid of boilerplate codes
* Sonar code scan is done for quality check

Note: Lombok API is used in this project, so you may need to add Lombok plugin to your IDE if it is giving compile time error.

## Swagger UI
URL - http://localhost:8080/swagger-ui.html <br/>
![swagger ui](https://photos.google.com/share/AF1QipMNfOlq9yL8VvL_hfMoY_WYPxpl1MBJyUM8yv6f4qxrP7B6bVOO1zztiEiEwbJ0Kw/photo/AF1QipONICGBj80PV56zv0gpg1E_WRaOOx0tWGtDh_Zl?key=S054aHFkd1RxWHBfYm5haWI2RFB6eHZIS0xxbk53)

## Authentication
Basic authentication is used in this project so use below credentials to access the endpoints <br/>
User name - user <br/>
Password - User@123  <br/>

## Endpoints -
To create a new player - POST http://localhost:8080/api/players <br/>
Request body - <br/>
```{"playerName":"Madan"}```

To get all available players - GET http://localhost:8080/api/players <br/>

To get a particular player - GET http://localhost:8080/api/players/{playerId} <br/>

To get a player balance - GET http://localhost:8080/api/player/1/balance <br/>

To get a player transactions - GET http://localhost:8080/api/player/1/transactions <br/>

To perform a transaction - POST http://localhost:8080/api/transact
Request body - <br/>
```{"playerId": 1,"transactionKey": "11",	"amount": 0.50,"transactionType": "CREDIT"}```







