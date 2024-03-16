# Utils-Redis

## Description

This module contains utility classes for mysql connections.

## Decisions

- 15.06.2023: We use hikaricp as mysql connection pool, since it is easy to use and has a good performance.

## Usage

### Kotlin

```
// create with default configuration
MySqlConnectionFactory.createMySqlConnection(CONFIGFILE)

// create with custom configuration (recommended)
MySqlConnectionFactory.createMySqlConnection("PATH/FILENAME.json")
```

### Example Configuration

```
{
  "hostname": "database.vellity.net",
  "port": 3306,
  "database": "vellity",
  "username": "vellity",
  "password": "v3ll1t4",
  "maxPoolSize": 10
}
```

## Dependencies

- utils-configuration ([README.md](../utils-configuration/README.md))
- HikariCP (https://github.com/brettwooldridge/HikariCP)