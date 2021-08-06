# WALLET MICROSERVICE

Build a simple wallet microservice running on the JVM that manages credit/debit transactions on behalf of players.

# DESCRIPTION

A monetary account holds the current balance for a player. The balance can be modified by registering transactions on the account, either debit transactions (removing funds) or credit transactions (adding funds). Create a REST API and an implementation that fulfils the requirements detailed below and honours the constraints.

# DESIRED FUNCTIONALITY

• Current balance per player
• Debit /Withdrawal per player A debit transaction will only succeed if there are sufficient funds on the account (balance - debit amount >= 0). The caller will supply a transaction id that must be unique for all transactions. If the transaction id is not unique, the operation must fail.
• Credit per player. The caller will supply a transaction id that must be unique for all transactions. If the transaction id is not unique, the operation must fail.
• Transaction history per player

# NON-FUNCTIONAL REQUIREMENTS

• The solution need not persist data across restarts but it is a bonus if it does.
• If the solution uses a database, please use h2 or some other in-memory database. Make sure that your build/run script builds, installs and configures any such database.
• Do not use 3rd party software that entails us to install software on our machines. If 3rd party software is a necessity, create a docker image with a fully prepped environment.

# What we will look at:

• Design
• Clean code
• Testability
• Software craftsmanship

# What you shall think about:

• Concurrency
• Scalability
• Atomicity
• Idempotency

# FINAL THOUGHTS
If you think some part of the exercise is unclear, don't worry. 
Decide for yourself what would be a logical thing to do, and explain in your comments why you did what you did.
It is OK to reduce the scope of the assignment where necessary, but please provide a brief description of what would need to be changed in order for your service to be production-ready.
You are free to choose frameworks and libraries for the task yourself but please provide instructions on how to run your service and how the API looks.

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
