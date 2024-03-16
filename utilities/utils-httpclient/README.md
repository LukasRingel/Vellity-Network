# Utils-HttpClient

## Description

This module contains utility classes for http client management.

## Decisions

- 15.06.2023: We use retrofit to provide a lightweight http client for our services.

## Usage

### Kotlin

```
// create http server
HttpClientFactory.create("HOSTNAME", 8080, "MYAPIKEY")
```

## Dependencies

- Retrofit (https://square.github.io/retrofit/)
- OkHttp (https://square.github.io/okhttp/)
- Gson-Converter (https://github.com/google/gson)