# 根据网上的文章进行的记录

## 获取redis镜像

```shell
docker pull redis:<版本号>
```

不加版本号默认是latest

## 创建网卡

```shell
docker network create redis --subnet 172.38.0.0/16
```

## 创建n个配置

## 通过脚本创建6个redis配置

```shell
for port in $(seq 1 <n>); \
do \
mkdir -p /usr/local/sbin/redis/node-${port}/conf
touch /usr/local/sbin/redis/node-${port}/conf/redis.conf
cat << EOF >/usr/local/sbin/redis/node-${port}/conf/redis.conf
port 6379
bind 0.0.0.0
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 172.38.0.1${port}
cluster-announce-port 6379
cluster-announce-bus-port 16379
appendonly yes
EOF
done
```

这实际上循环创建了多个配置文件并设置了内容

## 通过脚本运行n个容器

```shell
for port in $(seq 1 <n>); \
do \
docker run -p 637${port}:6379 -p 1637${port}:16379 --name redis-${port} \
-v /usr/local/sbin/redis/node-${port}/data:/data \
-v /usr/local/sbin/redis/node-${port}/conf/redis.conf:/etc/redis/redis.conf \
-d --net redis --ip 172.38.0.1${port} redis:<版本号> redis-server set/etc/redis/redis.conf; \
done
```

## 进入一个容器中

```shell
docker exec -it redis-1 /bin/bash
```

## 执行创建集群命令

```shell
redis-cli --cluster create 172.38.0.11:6379 172.38.0.12:6379 172.38.0.13:6379 172.38.0.14:6379 172.38.0.15:6379 172.38.0.16:6379 --cluster-replicas 1
```

这里需要把集群用到的redis节点都加进来

## 原文链接

<https://cxyroad.blog.csdn.net/article/details/108439443>
文章里/bin/sh的地方请使用/bin/bash
