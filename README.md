# Network

## Description

This repository contains important components and utilities for the backend of all vellity networks.
Since we use the same microservices on each network and each microservice gets requests from each network, we always
have to keep the context in our mind while developing!

## Git strategy

For a better overview and simple identification of issues and commits, we use the following naming conventions
while committing:

1. **[Issue number] [Short Description]** - For commits that refer to an Jira-Issue
2. **noissue [Short Description]** - For commits that do not refer to an Jira-Issue

If you are working on an issue, you should create a new branch with the following naming convention:

1. **feature/[Issue number]** - For branches that refer to an Jira-Issue
2. **feature/[Sort Description]** - For branches that do not refer to an Jira-Issue

## Run microservices locally

New features should always be developed locally first. To run the microservices locally, you can just run the Run
Microservices configuration in IntelliJ. This will start all microservices against the configured database. To configure
the database and other environment variables, you can create a file called `environment.json` in the root directory of
the project.
The file should at least look like this:

```
{
  "vars": {
    "ENVIRONMENT": "develop",
    "MYSQL_HOST": "database.vellity.dev",
    "MYSQL_POST": "3306",
    "MYSQL_DATABASE": "vellity",
    "MYSQL_USER": "USERNAME",
    "MYSQL_PASSWORD": "PASSWORD",
    "REDIS_HOST": "database.vellity.dev",
    "REDIS_PORT": "7000",
    "REDIS_PASSWORD": "",
    "USERCACHE_HOST": "http://localhost:9007/",
    "USERCACHE_AUTH_KEY": "123456789"
  }
}
```

## HTTP-Testing with Postman

To test the HTTP-Endpoints of the microservices, you can use the Postman-Collection in the `docs/postman` directory. If
your local environment is configured correctly, you can just import the collection and start testing. After
changing/adding routes/requests in Postman, just export the Collection to the repository and commit your changes.

## Qodana

We use Qodana to analyze our code to find possible bugs, style issues and vulnerabilities. The analysis will be done
after the CI pipeline has finished. To view the reports you can go to
the [Qodana Dashboard](https://qodana.cloud/organizations/z8JEj/teams/z89QJ) or connect your IDE to Qodana and open the
Problems Tab. Sometimes the analysis shows some false positives, which can be ignored. If you think that the analysis is
wrong, you can exclude the file from the analysis by adding it to [qodana.yml](qodana.yaml). Please always add a
comment, why you excluded the file so other developers can understand it.