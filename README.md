# FolderStructure

##  Requirements
1.  JDK14

##  Set up project
0.  Set up a SQL Server locally: create an empty schema called "file_structure".
1.  Set up environment variable in Intellij Idea:


    DATABASE_URL = `jdbc:mysql://localhost:3306/file_structure?user=<user>&password=<password>&serverTimezone=UTC`
2.  Launch application via Intellij Idea. schema.sql and data.sql will be run automatically.
3.  Test the project with Postman collection (https://www.getpostman.com/collections/888e462ce6a6d9e13434)
