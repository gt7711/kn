# City App

The City App is a web application that allows users to view and update city information.

## Prerequisites

Before running the City App, please make sure you have the following installed on your system:

- Docker: [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: [Install Docker Compose](https://docs.docker.com/compose/install/)

## Running the City App

To run the City App using Docker Compose, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/gt7711/kn.git

2. cd kn
3. docker-compose up
4. http://localhost:8080

## Additional Notes

The basic spring security has been added. The browser will prompt for login

1. Two Users have been created in memory with following credentials
  
      username: user
  
      password: password
  
      role: USER

      **************
      
      username: admin
  
      password: password
  
      role: ADMIN
  
2. The city update access is given only for the admin user
3. Since logout and session management are not added for now, please open the application in private(or incognito) mode. If you want to login with another user, please close the browser and re-open the private(or incognito) window again



