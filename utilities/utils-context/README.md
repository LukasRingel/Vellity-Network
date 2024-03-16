# Utils-Context

## Description

This module contains the context enum and utility classes for context management.
We use the context to separate information about players, stats, etc. for our different networks.

## Decisions

- 15.06.2023: We use enum ordinals for database ids, since they are unique and easy to use.
- 15.06.2023: We use the ALL context for global stats and other things that are not network specific to keep UNKNOWN free for error handling.
- 22.07.2023: We add a POSTMAN Context to identify requests from postman for development and stage testing

## Usage

### Kotlin

```
// get current context
Context.current()

// get current context id (Integer)
Context.currentId()

// update context
Context.update(Context.YOURNEWCONTEXT)
```

## Dependencies
