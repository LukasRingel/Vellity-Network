# Utils-Configuration

## Description

This module contains utility classes for configuration management.

## Decisions

- 15.06.2023: We use json as configuration format, since it is easy to read and write for humans and machines.
- 04.07.2023: We use the Environment Wrapper to get the vars from the environment or from the configuration file.

## Usage

### Kotlin

```
// create configuration
var configuration = ConfigurationFactory.create(
  "PATH/FILENAME.json",
  TYPE::class.java
)

// update configuration
configuration.update()
```

## Dependencies

- Gson (https://github.com/google/gson)
