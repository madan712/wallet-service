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

![Swagger UI](https://lh3.googleusercontent.com/EPgkSr9oG5dEGnlpRjgQ-jp_rFFUeveI8gYmGunhpms67Kk1EdTXTooIJ8GxQAI7aFSO8Qn48ef8dsd_BaCHBXCGzC3_NjeRR4z-ZHa-uPQgB49ab3PBPLM5mc_EDXBRzEYv0veInlKn0_lbrevJPGKupMStSPA-4B-ky4Cp3qawWueaA3Sn2JDn4yXTTjB9MrrvNkOJUKDCKBctaa9cHg3dbURZUHTyXRRmG6r27j2KfqC_HXblBxEyo0S4lFPwSYXNP7mj9ibZDMyTOHcKQx14lYZ0CgZBjFdJ98tq52I8Bgfn0squHYIsY0vZtfE2wK2F3HJE7hMXk3qXsjJIhKh_1TwFI1LLlbkMjKcURuxVV3twCfMP3JVfaO4jq_oq_NoFbvmCrd7Oygl5Ks60SwBwWyu0narKsptiO0nPNAmft9xtiBSjIeVrfyNxD_SkTkBYdAwdt62ayZjzGr9Pd0trmf7FGwfaoLHWd_PYZihdfDKhonA8fnRKKRWJ8UXnm4qNfCjIb-agqsP1CYceyhn7nKds2bw-f642Rb_6lJcDJ3kIu-aGrNSSAmDVzDfjG8SgGVd8YCjVAYGIvrXFTqStYu9PYchCkvqjcScw-PL76nEhLFp4N4XzEejsd3L-ASNspwz4ZRkXjDHjK58bWuOtQrMC5KVE1az3w32nISX1jXNo80LGbVo0kv1voSarMudzDQ-N7fI10sSyUjspg_Pv=w1169-h657-no)

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

To get a player balance - GET http://localhost:8080/api/players/{playerId}/balance <br/>

To get a player transactions - GET http://localhost:8080/api/players/{playerId}/transactions <br/>

To perform a transaction - POST http://localhost:8080/api/transact <br/>
Request body - <br/>
```{"playerId": 1,"transactionKey": "11",	"amount": 0.50,"transactionType": "CREDIT"}```

transactionType options - CREDIT, DEBIT