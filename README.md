# ING Tech project - User Authentication

## Overview

This repository contains a Java application for user authentication. 

This application was developed as part of the ING Tech recruiting process.

## Instalation

To download and install this application, follow these steps :

1. Clone this repository on your local machine.

2. Open the project folder in the preferred IDE. (I have used IntelliJ IDEA).

3. Import the database (Run the **database-init.sql** file located in [ingtechproject\user-authentication\src\main\resource](ingtechproject\user-authentication\src\main\resource)).

3. Run the application (The application will start on port 8080).

## Application functionalities

This application has 4 functionalities :

1. User registration (/signup)

2. User login (/login)

3. See user details (/user-details)

4. User logout (/log-out)

## How to use the application

1. Import the Postman collection with requests from **INGTech.postman_collection.json** located in [ingtechproject\user-authentication\src\main\resource](ingtechproject\user-authentication\src\main\resource) in Postman.

2. Send the login request (POST http://localhost:8080/login).

3. Copy the generated JWT.

4. Add the JWT in Authorization header (Type : "Bearer Token") in the user details request (GET http://localhost:8080/user-details) and send the request.

5. Send the logout request (POST http://localhost:8080/log-out) with the JWT in Authorization header (Type : "Bearer Token").

6. Try to send a user details request again with the last token. Now the request won't work.

7. If you want to create a new user, send a signup request (POST http://localhost:8080/signup) and add the user details in the body of the request.

## Tests available

getUsersTest() 

getUserByUsernameTest()

saveUserTest()




