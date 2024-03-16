# Web-Service Chatlog Viewer

## Description

This web service is responsible for viewing chat logs in the browser

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-httpserver-spring ([README.md](../../utilities/utils-httpserver/README.md))

## Configuration
```text
# Redis
CHATLOG_VIEW_BASE_URL=https://chatlog.vellity.net
```