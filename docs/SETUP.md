# Local Development Setup

This guide explains how to run the SmartTech E-Business Management System locally without relying on machine-specific paths or shared credentials.

## Prerequisites

Install the following software:

- JDK 11 or a compatible newer JDK
- Apache Maven
- GlassFish 7
- MySQL Server 8
- MySQL Connector/J
- A local SMTP testing server such as FakeSMTP
- Git
- Apache NetBeans or another Jakarta EE-compatible IDE

Check Java and Maven from a terminal:

```bash
java -version
mvn -version
```

## 1. Clone and Open the Project

```bash
git clone https://github.com/JeraldBucud/smarttech-ebusiness-system.git
cd smarttech-ebusiness-system/EBusinessSystem
```

Open the `EBusinessSystem` directory as a Maven web project in your IDE.

## 2. Create the Local Database

Create a local database user with a password that is unique to your own development environment.

```sql
CREATE DATABASE IF NOT EXISTS ebusiness_db;

CREATE USER IF NOT EXISTS 'ebusiness_user'@'localhost'
IDENTIFIED BY '<choose-a-local-password>';

GRANT ALL PRIVILEGES ON ebusiness_db.*
TO 'ebusiness_user'@'localhost';

FLUSH PRIVILEGES;
```

Do not commit your real database password to this repository.

## 3. Install MySQL Connector/J

Copy the MySQL Connector/J JAR into the GlassFish domain library or the GlassFish installation library, depending on your local setup.

A common domain-level location is:

```text
<glassfish-directory>/glassfish/domains/domain1/lib/
```

Restart GlassFish after copying the driver.

```bash
asadmin stop-domain domain1
asadmin start-domain domain1
```

## 4. Configure the JDBC Connection Pool

Open the GlassFish Administration Console, usually available at:

```text
http://localhost:4848
```

Create a JDBC connection pool with these settings:

| Setting | Value |
| --- | --- |
| Pool name | `EBusinessPool` |
| Resource type | `javax.sql.DataSource` |
| Database driver vendor | MySQL |
| Datasource classname | `com.mysql.cj.jdbc.MysqlDataSource` |

Add the following properties:

| Property | Value |
| --- | --- |
| `databaseName` | `ebusiness_db` |
| `serverName` | `localhost` |
| `portNumber` | `3306` |
| `user` | `ebusiness_user` |
| `password` | Your locally chosen password |
| `useSSL` | `false` |
| `allowPublicKeyRetrieval` | `true` |
| `serverTimezone` | `UTC` |

Ping the connection pool and confirm that GlassFish reports a successful connection.

## 5. Create the JDBC Resource

Create a JDBC resource with these values:

| Setting | Value |
| --- | --- |
| JNDI name | `jdbc/ebusiness_db` |
| Pool name | `EBusinessPool` |
| Status | Enabled |

The application references this resource from `persistence.xml`:

```xml
<jta-data-source>jdbc/ebusiness_db</jta-data-source>
```

## 6. Start the Local SMTP Server

The development email service expects an SMTP server on:

```text
Host: localhost
Port: 2525
Authentication: disabled
TLS: disabled
```

Start FakeSMTP or another compatible local SMTP testing server on port `2525` before testing registration, verification or password recovery.

Do not use these development SMTP settings for a production deployment.

## 7. Build the Application

From the `EBusinessSystem` directory, run:

```bash
mvn clean package
```

The generated WAR file will be placed in:

```text
target/EBusinessSystem-1.0-SNAPSHOT.war
```

## 8. Deploy to GlassFish

Deploy the generated WAR through NetBeans, the GlassFish Administration Console or the command line.

Example:

```bash
asadmin deploy --force=true target/EBusinessSystem-1.0-SNAPSHOT.war
```

After deployment, open:

```text
http://localhost:8080/EBusinessSystem/
```

## Troubleshooting

### GlassFish cannot connect to MySQL

- Confirm that MySQL is running.
- Confirm the database username and locally selected password.
- Confirm that Connector/J is available to the GlassFish domain.
- Ping the JDBC pool from the Administration Console.

### Verification or recovery email is not received

- Confirm that the local SMTP server is running on port `2525`.
- Confirm that another application is not already using the port.
- Check the GlassFish server log for Jakarta Mail errors.

### Maven compiles with the wrong Java version

The current Maven configuration targets Java 11. Confirm that Maven is using a compatible JDK:

```bash
mvn -version
```

If the output points to an unexpected Java installation, update `JAVA_HOME` before building.
