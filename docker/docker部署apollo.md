# docker部署apollo

官方文档地址：<https://ctripcorp.github.io/apollo/#/zh/deployment/distributed-deployment-guide?id=_23-docker%e9%83%a8%e7%bd%b2>

## Apollo Config Service

```shell
docker pull apolloconfig/apollo-configservice:${version}
```

```shell
docker run -p 8080:8080 \
    -e SPRING_DATASOURCE_URL="jdbc:mysql://${fill-in-the-correct-server:3306}/ApolloConfigDB?characterEncoding=utf8" \
    -e SPRING_DATASOURCE_USERNAME=${FillInCorrectUser} -e SPRING_DATASOURCE_PASSWORD=${FillInCorrectPassword} \
    -d -v /tmp/logs:/opt/logs --name apollo-configservice apolloconfig/apollo-configservice:${version}
```

参数说明：
SPRING_DATASOURCE_URL: 对应环境ApolloConfigDB的地址
SPRING_DATASOURCE_USERNAME: 对应环境ApolloConfigDB的用户名
SPRING_DATASOURCE_PASSWORD: 对应环境ApolloConfigDB的密码

## Apollo Admin Service

```shell
docker pull apolloconfig/apollo-adminservice:${version}
```

```shell
docker run -p 8090:8090 \
    -e SPRING_DATASOURCE_URL="jdbc:mysql://${fill-in-the-correct-server:3306}/ApolloConfigDB?characterEncoding=utf8" \
    -e SPRING_DATASOURCE_USERNAME=${FillInCorrectUser} -e SPRING_DATASOURCE_PASSWORD=${FillInCorrectPassword} \
    -d -v /tmp/logs:/opt/logs --name apollo-adminservice apolloconfig/apollo-adminservice:${version}
```

参数说明：
SPRING_DATASOURCE_URL: 对应环境ApolloConfigDB的地址
SPRING_DATASOURCE_USERNAME: 对应环境ApolloConfigDB的用户名
SPRING_DATASOURCE_PASSWORD: 对应环境ApolloConfigDB的密码

## Apollo Portal

```shell
docker pull apolloconfig/apollo-portal:${version}
```

```shell
docker run -p 8070:8070 \
    -e SPRING_DATASOURCE_URL="jdbc:mysql://${fill-in-the-correct-server:3306}/ApolloPortalDB?characterEncoding=utf8" \
    -e SPRING_DATASOURCE_USERNAME=${FillInCorrectUser} -e SPRING_DATASOURCE_PASSWORD=${FillInCorrectPassword} \
    -e APOLLO_PORTAL_ENVS=dev,pro \
    -e DEV_META=http://${fill-in-dev-meta-server:8080} -e PRO_META=http://${fill-in-pro-meta-server:8080} \
    -d -v /tmp/logs:/opt/logs --name apollo-portal apolloconfig/apollo-portal:${version}
```

参数说明：
SPRING_DATASOURCE_URL: 对应环境ApolloPortalDB的地址
SPRING_DATASOURCE_USERNAME: 对应环境ApolloPortalDB的用户名
SPRING_DATASOURCE_PASSWORD: 对应环境ApolloPortalDB的密码
APOLLO_PORTAL_ENVS(可选): 对应ApolloPortalDB中的apollo.portal.envs配置项，如果没有在数据库中配置的话，可以通过此环境参数配置
DEV_META/PRO_META(可选): 配置对应环境的Meta Service地址，以${ENV}_META命名，需要注意的是如果配置了ApolloPortalDB中的apollo.portal.meta.servers配置，则以apollo.portal.meta.servers中的配置为准
