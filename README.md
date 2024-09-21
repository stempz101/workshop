## Navigation
- [Requirement](#Requirement)
- [Tasks](#Tasks)
- [Technologies](#Technologies)
- [How to run](#How-to-run)

# Requirement
In the UI we have the form for the new user registration. Form contains 3 fields where username and email are mandatory. Email and mobile contains necessary validation.

Click on Register button calls execution plan that saves values to the database (image some User table with fields). Database listener needs to be running for successful data saving.

![image](https://github.com/user-attachments/assets/a3257945-7658-479a-a61c-cbb37e03437c)

# Tasks

## Task 1

Flowchart for feature implementation (execution plan that save data to database after click on button)

Registration Click Flowchart:

![Registration Click drawio](https://github.com/user-attachments/assets/f35e3174-4532-4f79-b171-a889b57ce43d)

Registration Process Flowchart:

![Registration Process drawio](https://github.com/user-attachments/assets/11ac9b93-a5ea-49a0-948d-a0a8ead43535)

## Task 2

Validation that should be included in the form (think about scenario from the real world)

![image](https://github.com/user-attachments/assets/d3f54f6a-5560-464a-9a94-65484e22fa50)
![image](https://github.com/user-attachments/assets/3dc5fb70-60d4-49c3-b716-7e3670a3eed2)
![image](https://github.com/user-attachments/assets/d7d31012-d5d7-48b8-b68b-d8a53d9f19b5)
![image](https://github.com/user-attachments/assets/16108022-2e02-4d80-a3cc-7ad988b6a1db)
![image](https://github.com/user-attachments/assets/b9b5a564-64a2-4490-8a1d-940eb8ab2056)
![image](https://github.com/user-attachments/assets/7eecff7c-64a3-4526-9671-7c405c279554)

## Task 3

Script for table creation (table of the table and fields are up to you), don't forget for the mapping (name of the UI fields are up to you)

Script is located in [default-tables.sql](src/main/resources/db/changelog/wshop-changelogs/default-tables.sql)

![image](https://github.com/user-attachments/assets/cfbaa237-f473-4026-9171-7fcfc4832b29)

# Technologies

- Java 17
- Spring (Boot, Web, Data, Validation, Thymeleaf)
- Liquibase
- PostgreSQL
- HTML/CSS/JavaScript
- Docker

# How to run
Using Docker, just run the next command:
   ```bash
   docker compose up -d
   ```
