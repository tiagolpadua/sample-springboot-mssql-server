# Cheat Sheet

## SQL Server

```bash
$ docker-compose up db
$ sqlcmd -S localhost -U SA -P 'Admin123!'
$ sqlcmd -S 127.0.0.1 -U SA -P 'Admin123!'
```
Sempre terminar os comandos com ```GO```;


```sql
CREATE DATABASE TestDB
SELECT Name from sys.Databases
USE TestDB
CREATE TABLE Inventory (id INT, name NVARCHAR(50), quantity INT)
INSERT INTO Inventory VALUES (1, 'banana', 150); INSERT INTO Inventory VALUES (2, 'orange', 154);
SELECT * FROM Inventory WHERE quantity > 152;
QUIT

CREATE DATABASE springbootdb
GO
USE springbootdb
GO
SELECT TABLE_NAME FROM springbootdb.INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'
GO

```

## Swagger
JSON: http://localhost:8080/api/v2/api-docs
WEB-UI: http://localhost:8080/api/swagger-ui.html
