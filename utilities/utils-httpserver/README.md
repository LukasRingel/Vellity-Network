# Utils-HttpServer

## Description

This module contains utility classes for http server management.

## Decisions

- 15.06.2023: We use the sun http-server to provide a lightweight http server for our services.
- 15.06.2023: We only use Spring Boot for the http server, if we need a more complex http server.

## Usage

### Kotlin

```
// create http server
HttpServerFactory.create(8080, "MYAPIKEY")
```

### Example Configuration

```
{
  "port": 8008,
  "authKey": "STRONGPASSWORD",
}
```

## Dependencies

- utils-configuration ([README.md](../utils-configuration/README.md))