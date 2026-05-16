# COIT20259 Assignment 2 Team Setup Instructions

This guide explains how each team member should set up the project locally so they can work on their assigned layer.

---

## 1. Project Repository

Repository name:

```text
COIT20259-Assignment2-Ebusiness-System
```

Each team member must work on their own branch. Do **not** push directly to `main`.

Recommended branch names:

```text
jerald-presentation-layer
name-business-layer
name-persistence-layer
name-security-functions
```

Recommended layer allocation:

```text
Jerald: Presentation Layer / JSF / Jakarta Faces
Member 2: Business Layer / EJB Services
Member 3: Persistence Layer / JPA Entities and MySQL
Security Functions: Shared task
```

---

## 2. Clone the Repository

Open CMD or PowerShell in the folder where you want to save the project.

Example:

```cmd
cd /d D:\05_School\COIT20259
```

Clone the repository:

```cmd
git clone https://github.com/YOUR-GITHUB-USERNAME/COIT20259-Assignment2-Ebusiness-System.git
```

Go inside the repository:

```cmd
cd COIT20259-Assignment2-Ebusiness-System
```

Create your own branch:

```cmd
git checkout -b yourname-layer-name
```

Push your branch:

```cmd
git push -u origin yourname-layer-name
```

---

## 3. Required Software

Each team member should install:

```text
Apache NetBeans
JDK 21
GlassFish 7.0.12
MySQL Server 8.0
MySQL Workbench
MySQL Connector/J
Git
FakeSMTP
=======


Recommended setup:

```text
NetBeans Project: EBusinessSystem
Application Server: GlassFish 7.0.12
Database: MySQL
Fake Email Tool: FakeSMTP
=======


---

## 4. Open the Project in NetBeans

Open NetBeans.

Go to:

```text
File → Open Project
```

Open this folder:

```text
COIT20259-Assignment2-Ebusiness-System\EBusinessSystem
```

---

## 5. Add GlassFish to NetBeans

In NetBeans:

```text
Window → Services → Servers → Add Server
```

Choose:

```text
GlassFish Server
```

Use your GlassFish installation folder.

Example:

```text
D:\05_School\Java\glassfish7
```

Use:

```text
Domain: domain1
Admin Port: 4848
HTTP Port: 8080
```

Start GlassFish and check these links:

```text
http://localhost:4848
http://localhost:8080
```

---

## 6. MySQL Database Setup

Each team member should create their **own local MySQL database**.

Do **not** connect to Jerald's MySQL. The project uses `localhost`, so each person's GlassFish will connect to their own MySQL database.

Open MySQL Workbench and run:

```sql
CREATE DATABASE IF NOT EXISTS ebusiness_db;
```

Then create the project user:

```sql
CREATE USER IF NOT EXISTS 'ebusiness_user'@'localhost' IDENTIFIED BY 'Password123';

GRANT ALL PRIVILEGES ON ebusiness_db.* TO 'ebusiness_user'@'localhost';

FLUSH PRIVILEGES;
```

Check the user:

```sql
SELECT user, host FROM mysql.user WHERE user = 'ebusiness_user';
```

Expected result:

```text
ebusiness_user | localhost
```

Database details:

```text
Database Name: ebusiness_db
Username: ebusiness_user
Password: Password123
Host: localhost
Port: 3306
```

---

## 7. Add MySQL Connector/J to GlassFish

Download or locate the MySQL Connector/J `.jar` file.

Example filenames:

```text
mysql-connector-j-8.x.x.jar
mysql-connector-j-9.x.x.jar
```

Copy the `.jar` file into:

```text
D:\05_School\Java\glassfish7\glassfish\lib
```

Restart GlassFish:

```cmd
asadmin stop-domain
asadmin start-domain
```

---

## 8. Create JDBC Connection Pool in GlassFish

Open GlassFish Admin Console:

```text
http://localhost:4848
```

Go to:

```text
Resources → JDBC → JDBC Connection Pools → New
```

Use:

```text
Pool Name: EBusinessPool
Resource Type: javax.sql.DataSource
Database Driver Vendor: MySQL
```

Click **Next**.

Set the datasource classname:

```text
com.mysql.cj.jdbc.MysqlDataSource
```

In **Additional Properties**, add or update:

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

Click **Finish**.

Then open:

```text
Resources → JDBC → JDBC Connection Pools → EBusinessPool
```

Click:

```text
Ping
```

Expected result:

```text
Ping Succeeded
```

---

## 9. Create JDBC Resource

In GlassFish Admin Console, go to:

```text
Resources → JDBC → JDBC Resources → New
```

Use:

```text
JNDI Name: jdbc/ebusiness_db
Pool Name: EBusinessPool
Status: Enabled
```

Click **OK** or **Save**.

The project `persistence.xml` should use:

```xml
<jta-data-source>jdbc/ebusiness_db</jta-data-source>
```

This allows JPA to connect to the GlassFish JDBC resource.

---

## 10. FakeSMTP Setup

FakeSMTP is used as the fake SMTP email server for registration verification and account recovery emails. The assignment demonstration uses FakeSMTP/CentreMail so the verification and recovery codes can be viewed locally instead of sending real emails through Gmail or another commercial email server.

### 10.1 Clone FakeSMTP from GitHub

Open PowerShell and go to the folder where you want to save FakeSMTP.

Example:

```powershell
cd "D:\05_School\Java\fakestmp"
```

Clone the FakeSMTP repository:

```powershell
git clone https://github.com/Nilhcem/FakeSMTP.git
```

Go inside the FakeSMTP folder:

```powershell
cd FakeSMTP
```

### 10.2 Build FakeSMTP with Maven

This project needs Apache Maven. If `mvn` is not available in PowerShell, use the full Maven path.

Example Maven path:

```text
D:\05_School\Java\apache-maven-3.9.15
```

Check Maven:

```powershell
D:\05_School\Java\apache-maven-3.9.15\bin\mvn.cmd -version
```

### 10.3 Fix Java 6 Compiler Setting for JDK 21

FakeSMTP is an older project and may use Java 6 compiler settings. When building with JDK 21, this can cause this error:

```text
Source option 6 is no longer supported. Use 8 or later.
Target option 6 is no longer supported. Use 8 or later.
```

To fix it, open the FakeSMTP `pom.xml` file:

```powershell
notepad pom.xml
```

Find the Maven compiler settings and change Java 6 to Java 8.

Change this:

```xml
<source>1.6</source>
<target>1.6</target>
```

To this:

```xml
<source>1.8</source>
<target>1.8</target>
```

If the file uses `6` instead of `1.6`, change it to `8`:

```xml
<source>8</source>
<target>8</target>
```

Save the `pom.xml` file.

### 10.4 Package FakeSMTP

From inside the FakeSMTP folder, run:

```powershell
D:\05_School\Java\apache-maven-3.9.15\bin\mvn.cmd clean package -DskipTests
```

Expected result:

```text
BUILD SUCCESS
```

The generated JAR should be created in the `target` folder.

Check the target folder:

```powershell
dir target
```

Example generated file:

```text
fakeSMTP-2.1-SNAPSHOT.jar
```

### 10.5 Run FakeSMTP on Port 2525

Start FakeSMTP using port `2525`:

```powershell
java -jar target\fakeSMTP-2.1-SNAPSHOT.jar -s -p 2525
```

If the JAR file name is different, run this PowerShell command to start the first JAR in the target folder:

```powershell
java -jar (Get-ChildItem target\*.jar | Select-Object -First 1).FullName -s -p 2525
```

Keep the FakeSMTP window open while testing the web application.

Project email settings:

```text
SMTP Host: localhost

SMTP Port: 2525

Authentication: false
SSL/TLS: false
```

The email flow should be:

```text
Jakarta EE application → localhost:2525 → FakeSMTP window
```

When the user creates a verification code or recovery code, the email should appear in the FakeSMTP window. The code can then be copied from FakeSMTP and entered into the web application.



## 11. Run the Project

In NetBeans:

```text
Right-click EBusinessSystem → Run
```

The app should open at:

```text
http://localhost:8080/EBusinessSystem/
```

If the page opens, the local setup is working.

---

## 12. Git Commit Workflow

Before working, always check your branch:

```cmd
git branch
```

Check changed files:

```cmd
git status
```

Add files:

```cmd
git add .
```

Commit with a meaningful message:

```cmd
git commit -m "Add product creation page layout"
```

Push your branch:

```cmd
git push
```

Use small meaningful commits. Do not commit everything in one large commit.

---

## 13. Application Architecture

The required architecture is:

```text
JSF / Jakarta Faces
↓
Backing Beans
↓
EJB Business Services
↓
JPA Entities / JPQL
↓
MySQL Database
```

Do not place database logic directly in JSF pages. JSF pages and backing beans should call the EJB business layer.

---

## 14. Important Notes

Each member should create the same local setup:

```text
Database: ebusiness_db
User: ebusiness_user
Password: Password123
JDBC Pool: EBusinessPool
JDBC Resource: jdbc/ebusiness_db
```

Because the JDBC pool uses:

```text
serverName = localhost
```

`localhost` means the current computer. So each team member's GlassFish connects to their own MySQL database.

For final demonstration, the demo computer must have:

```text
GlassFish running
MySQL running
ebusiness_db created
JDBC Pool working
JDBC Resource created
Application deployed successfully
```
