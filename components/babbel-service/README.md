# Service Babbel

## Description

This microservice is responsible for translations and player language settings.

## Decisions

- 01.07.2023: We disable auto-updates, since we create a automatic deployment to the cluster via GitLab CI/CD on the
  locales repository. That reduces the load to the GitLab server.

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- babbel-common ([README.md](../../components/babbel-common/README.md))

## Configuration

```text
# Redis
REDIS_HOST=database.vellity.net
REDIS_PORT=7000
REDIS_PASSWORD=secret

# MySQL
MYSQL_HOST=database.vellity.net
MYSQL_PORT=3306
MYSQL_DATABASE=access
MYSQL_USER=access
MYSQL_PASSWORD=secret

# Explorer
EXPLORER_HOST=api.vellity.net/explorer
EXPLORER_AUTH_KEY=secret

# Language Repository
REPOSITORY_HOST=https://git.vellity.net/locales.git
REPOSITORY_USERNAME=git
REPOSITORY_PASSWORD=secret
REPOSITORY_BRANCH=main
REPOSITORY_LOCAL_PATH=locales/
```

## Database Setup

### table creation

```sql
create table babbel_users_locales
(
    id        int auto_increment primary key,
    player_id char(36)                    null,
    context   int                         not null,
    locale    varchar(10) default 'en_US' not null,
    constraint player_id
        unique (player_id, context)
);
```