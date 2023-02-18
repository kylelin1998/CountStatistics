### 简体中文 | [English](./README_en.md)

![License](https://img.shields.io/badge/license-MIT-green)
[![release](https://img.shields.io/github/v/release/kylelin1998/CountStatistics)](https://github.com/kylelin1998/CountStatistics/releases/latest)

## 简介
轻松使用, 支持在线人数统计和PV统计， 纯接口调用使用， 一款开源在线计数器

<https://count.kylelin1998.com>

## 关于
我的Telegram: <https://t.me/KyleLin1998>

我的Telegram频道(软件最新通知会在此频道通知， 欢迎关注): <https://t.me/KyleLin1998Channel>

我的邮箱: email@kylelin1998.com

## 安装 & 部署
项目依赖Redis, 需要自行安装

Dockerfile 与 jar文件放置到同一目录中进行打包镜像
```
docker build -t count-statistics .
```
需要在服务器上建立好对应目录， logs, config

需要先在config目录下创建一个application.yml文件
```
docker run --name count-statistics -p 9040:9040 -d -v $(pwd)/logs:/logs -v $(pwd)/count-statistics-1.0.0.jar:/app.jar -v $(pwd)/config:/config  --restart=always count-statistics
```

## 使用说明
application.yml示例:
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
* app.uri-expire-day -> 新建资源未使用超过X天则系统释放该资源
* app.rate.uri-count -> 新建资源者在间隔时间内总共可以创建的资源数量
* app.rate.interval-hour -> 间隔时间

域名: https://count.kylelin1998.com

{uri}: 自定义资源标识符, 不能超过15个字符, 仅支持大小写字母

PV统计接口:
* GET /api/count/{uri}/add -> 计数加1, 会立即返回总数数量
* GET /api/count/{uri}/count -> 查看总数数量

在线人数统计接口:
* GET /api/online/{uri}/heartbeat -> 心跳接口, 每60秒请求一次, 会立即返回在线人数数量
* GET /api/online/{uri}/count -> 查看在线人数数量