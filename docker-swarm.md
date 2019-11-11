# Docker-Swarm集群环境搭建
docker network ls：查看docker网络列表

# 初始化swarm集群，创建管理节点
docker swarm init --advertise-addr 192.168.1.144
# 加入管理节点
docker swarm join --token SWMTKN-1-2tyafoa7dqputamdb572zg5wgxjvlbpnda9yqwv0cxm13rygh3-5mfeo1uut75gt8s4enbo4ei3l 192.168.1.144:2377
# 查看当前节点状态
docker node ls  
# 退出节点
docker swarm leave
# 提升节点为leader
docker node promote centos-swarm02
# 创建一个服务
docker service create --name test1 alpine ping www.baidu.com
# 查看当前服务
docker service ls
# 创建一个nginx服务
docker service create --name nginx nginx
# 同时创建3个服务运行在3个容器里
docker service create --network zxb-overlay --replicas 3 -p 8080:80 --name nginx nginx
# 修改nginx服务
docker service update --publish-add 8080:80 nginx
# Docker扩展一个或多个复制服务
docker service scale nginx=3
# 创建网络
docker network create -d overlay zxb-overlay
# 加入自定义网络
docker service create --network zxb-overlay --name nginx -p 8080:80 --detach=false nginx
# 服务伸缩，增加或减少服务
docker service scale nginx=2
# 创建dns rr 模式的网络服务，无法对外暴露端口，只能容器间访问，通过容器名称访问
docker service create --network zxb-overlay --name nginx-b --endpoint-mode dnsrr nginx
# 创建服务组
docker stack deploy -c server.yml test
# 列出组
docker stack ls
# 查看服务组
docker stack services test
