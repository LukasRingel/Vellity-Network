# Utils-Redis

## Description

This module contains utility classes for redis management.

## Decisions

- 15.06.2023: We use lettuce as redis client, since it is easy to use and has a good performance.

## Usage

### Kotlin

```
// create with default configuration
RedisConnectionFactory.create(RedisConnectionConfiguration("localhost", 6379, ""))

// create with custom configuration (recommended)
RedisConnectionFactory.create("PATH/FILENAME.json")
```

### Example Configuration

```
{
  "hostname": "redis.vellity.net",
  "port": 6379,
  "password": "STRONGPASSWORD",
}
```

## Dependencies

- utils-configuration ([README.md](../utils-configuration/README.md))
- Lettuce (https://lettuce.io/)