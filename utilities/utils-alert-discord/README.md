# Utils-Alert-Discord

## Description

This module contains utility classes for sending alerts to discord via webhooks.
We just provide the https://github.com/MinnDevelopment/discord-webhooks repository as a dependency.

## Usage

### Java

```
WebhookClientBuilder builder = new WebhookClientBuilder(url); // or id, token
builder.setThreadFactory((job) -> {
    Thread thread = new Thread(job);
    thread.setName("Hello");
    thread.setDaemon(true);
    return thread;
});
builder.setWait(true);
WebhookClient client = builder.build();
```

## Dependencies
