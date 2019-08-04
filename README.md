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

![Swagger UI](https://lh3.googleusercontent.com/6V7ME8FYMRGwCAWjg9ieeCprPmSspnL28Bkw6sapAEonUiKXmaxzqMoA02sWHEtRXyoLBv5zzH75XOkdP1gsjIBSaZkMe33HoV8dzt5Oz1EfDF0DK3qkhUIRIoNP8uoaBSpluUzqOuOeJT11Xt1HY6bkp9_5CXVLgDNDQhI6xa60eUg9YRi8-1ey15feXYf4_3kgcpAf_OtpuZIAqIWRa4d1VYaO8HQ0vsXjTaGqXEc4bgSnBqID6cQxbnCi_Z7nBx1dcSA90pGYbhJfPeX2wysPDY0ffZWa_W5gOAWzErO96OOW_gyaY9Te74L4lXvxqMCtK8oApLGSCtCl92YPmj4nwQDPmTnMhCY_EfE2xj9kX54lKNq-AixqKU6mJKxH2HqjulMkDmpAny3Uv5oEdxr5URG3TjNxc-48XhF2DcXnMzFuNh2AI88zVd044njoGZWXNsGgHQPWyISq1jgj9YtYmzyduNLlzWVv33Q-n8gwQI4GFfMerjhmEr3AVFLXmBh0RinGKqCuPjmN-r3xNqULZMyN7_pZjkPrCcanW-d3yqu5HBpio8hZ4iQ1C8Fb0W8ApztI7-76YcGE7rJKeQ8kXFnGKZhkX2-X6W6s5VsvPV_sIyw6h2wxfCpv5WpdZXGB456F9vi64MG3tKvBQWDldZx-fcc=w1167-h657-no)

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

To perform a transaction - POST http://localhost:8080/api/transact <br/>
Request body - <br/>
```{"playerId": 1,"transactionKey": "11",	"amount": 0.50,"transactionType": "CREDIT"}```







