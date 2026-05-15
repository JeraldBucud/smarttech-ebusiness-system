# COIT20259-Assignment2-Ebusiness-System
Team-based Jakarta EE 3-tier e-business application for COIT20259 Assignment 2.

## Team Setup Instructions

Each team member must clone the repository, create their own branch, and set up GlassFish and MySQL locally.

### Repository Workflow

Do **not** push directly to `main`.

Create a branch based on your assigned layer:

```cmd
git checkout -b yourname-layer-name
git push -u origin yourname-layer-name
```

Recommended branches:

```text
jerald-presentation-layer
name-business-layer
name-persistence-layer
name-security-functions
```

### Required Software

```text
Apache NetBeans
JDK 21
GlassFish 7.0.12
MySQL Server 8.0
MySQL Workbench
MySQL Connector/J
Git
smtp4dev
```

### Open Project

Open this folder in NetBeans:

```text
COIT20259-Assignment2-Ebusiness-System\EBusinessSystem
```

### MySQL Local Database

Each team member should create their own local database. Do not connect to another member's MySQL.

Run this in MySQL Workbench:

```sql
CREATE DATABASE IF NOT EXISTS ebusiness_db;

CREATE USER IF NOT EXISTS 'ebusiness_user'@'localhost' IDENTIFIED BY 'Password123';

GRANT ALL PRIVILEGES ON ebusiness_db.* TO 'ebusiness_user'@'localhost';

FLUSH PRIVILEGES;
```

Database details:

```text
Database Name: ebusiness_db
Username: ebusiness_user
Password: Password123
Host: localhost
Port: 3306
```

### GlassFish JDBC Pool

Copy MySQL Connector/J `.jar` into:

```text
D:\05_School\Java\glassfish7\glassfish\lib
```

Restart GlassFish:

```cmd
asadmin stop-domain
asadmin start-domain
```

In GlassFish Admin Console, create a JDBC Connection Pool:

```text
Pool Name: EBusinessPool
Resource Type: javax.sql.DataSource
Database Driver Vendor: MySQL
Datasource Classname: com.mysql.cj.jdbc.MysqlDataSource
```

Additional properties:

```text
password                  Password123
databaseName              ebusiness_db
serverName                localhost
user                      ebusiness_user
portNumber                3306
useSSL                    false
allowPublicKeyRetrieval   true
serverTimezone            UTC
```

Ping the pool. Expected result:

```text
Ping Succeeded
```

### GlassFish JDBC Resource

Create a JDBC Resource:

```text
JNDI Name: jdbc/ebusiness_db
Pool Name: EBusinessPool
Status: Enabled
```

The project `persistence.xml` should use:

```xml
<jta-data-source>jdbc/ebusiness_db</jta-data-source>
```

### smtp4dev

Start smtp4dev and open:

```text
http://localhost:5000
```

Use these email settings in the project:

```text
SMTP Host: localhost
SMTP Port: 25
Authentication: false
SSL/TLS: false
```

### Run Project

In NetBeans:

```text
Right-click EBusinessSystem → Run
```

Expected URL:

```text
http://localhost:8080/EBusinessSystem/
```
