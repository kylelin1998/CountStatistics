### [简体中文](./README.md) | English

![License](https://img.shields.io/badge/license-MIT-green)
[![release](https://img.shields.io/github/v/release/kylelin1998/CountStatistics)](https://github.com/kylelin1998/CountStatistics/releases/latest)

## Introduction
Easy use it! Two API requests let you use PV statistics and online statistics.
Fast request, and support cross-origin, between of them you maybe love one of them to use.

<https://count.kylelin1998.com>

## About
My Telegram: <https://t.me/KyleLin1998>

My Telegram Channel(Software, if have a new version, will be in this channel to notify everyone. Welcome to subscribe to it.): <https://t.me/KyleLin1998Channel>

My email: email@kylelin1998.com

## Install & Deploy
Must install Redis service, dependent on it for running the program.

Dockerfile and Jar file to save the same directory for building docker image.
```
docker build -t count-statistics .
```
You need to build logs, config directory on your personal server.

Then, Need to create a file named application.yml in config directory.
```
docker run --name count-statistics -p 9040:9040 -d -v $(pwd)/logs:/logs -v $(pwd)/count-statistics-1.0.0.jar:/app.jar -v $(pwd)/config:/config  --restart=always count-statistics
```

## Usage
application.yml:
```yml
{

  spring:
    application:
      name: count-statistics
    mvc:
      favicon:
        enabled: false
    thymeleaf:
      cache: true

    redis:
      database: 0
      expire: 1800
      host: localhost
      password: ''
      pool:
        max:
          active: 8
          idle: 8
          wait: -1
        min:
          idle: 0
      port: 6379
      timeout: 5000

  server:
    port: 9040
    servlet:
      context-path: /

  logging:
    config: classpath:logback-spring.xml

  app:
    request-log: false
    uri-expire-day: 90
    rate:
      enabled: true
      uri-count: 5
      interval-hour: 24
}
```
* app.uri-expire-day -> A statistics resource that will remove it if not used it more than X days
* app.rate.uri-count -> Whichever statistics resource owner only within interval time for total number by created statistics resource.
* app.rate.interval-hour -> Interval time

Domain: https://count.kylelin1998.com

{uri}: You can custom URI identity, the length can't be more than 15 letters and only support upper letters or lower letters.

PV statistics API:
* GET /api/count/{uri}/add -> Every time to request will add 1 and return the count.
* GET /api/count/{uri}/count -> Query the count

Online people statistics API:
* GET /api/online/{uri}/heartbeat -> Heartbeat, every 60 seconds to request one time, and return online people count
* GET /api/online/{uri}/count -> Query online people count