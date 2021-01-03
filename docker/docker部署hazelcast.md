# docker部署hazelcast

注意hazelcast版本

```shell
docker pull hazelcast/management-center
docker run -d --net redis --ip 172.38.0.13 -p 8080:8080 --name="hazelcast_management_center" hazelcast/management-center
docker pull hazelcast/hazelcast
docker run -d --net redis --ip 172.38.0.12 --name="hazelcast_client" -e JAVA_OPTS="-Dhazelcast.local.publicAddress=172.38.0.12:5701 -Dhazelcast.rest.enabled=true -Xms128M -Xmx256M" -p 5703:5701 hazelcast/hazelcast
```
