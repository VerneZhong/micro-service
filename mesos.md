# Mesos记录
1、Mesos-master
   拉取镜像：docker pull mesosphere/mesos-master
   docker运行脚本：
        #!/bin/bash
        docker run -d --net=host \
          --hostname=192.168.1.140 \
          -e MESOS_PORT=5050 \
          -e MESOS_ZK=zk://192.168.1.119:2181/mesos \
          -e MESOS_QUORUM=1 \
          -e MESOS_REGISTRY=in_memory \
          -e MESOS_LOG_DIR=/var/log/mesos \
          -e MESOS_WORK_DIR=/var/tmp/mesos \
          -v "$(pwd)/log/mesos:/var/log/mesos" \
          -v "$(pwd)/tmp/mesos:/var/tmp/mesos" \
          mesosphere/mesos-master:1.7.1 --no-hostname_lookup --ip=192.168.1.140
   mesos-master 需要服务注册到zookeeper注册中心，通过浏览器访问控制台管理页面http://192.168.1.140:5050/
   master通常需要在主机上运行，保证服务的高可用
2、Mesos-slave
    拉取镜像：docker pull mesosphere/mesos-slave
    docker运行脚本：
        #!/bin/bash
        docker run -d --net=host --pid=host --privileged \
          --hostname=192.168.1.141 \
          -e MESOS_PORT=5051 \
          -e MESOS_MASTER=zk://192.168.1.119:2181/mesos \
          -e MESOS_SWITCH_USER=0 \
          -e MESOS_CONTAINERIZERS=docker,mesos \
          -e MESOS_LOG_DIR=/var/log/mesos \
          -e MESOS_WORK_DIR=/var/tmp/mesos \
          -v "$(pwd)/log/mesos:/var/log/mesos" \
          -v "$(pwd)/tmp/mesos:/var/tmp/mesos" \
          -v /var/run/docker.sock:/var/run/docker.sock \
          -v /sys:/sys \
          -v /usr/bin/docker:/usr/local/bin/docker \
          mesosphere/mesos-slave:1.7.1 --no-systemd_enable_support \
          --no-hostname_lookup --ip=192.168.1.141
    mesos-slave 也是需要注册到zookeeper到mesos-master服务上，可以通过master管理页面查看slave服务器，以及分配给slave的资源
3、Mesos-marathon
    拉取镜像：docker pull mesosphere/marathon:latest
    docker运行脚本：
        #!/bin/bash
        docker run -d --net=host \
        	 mesosphere/marathon:v1.9.100 \
                 --master zk://192.168.1.119:2181/mesos \ 
        	 --zk zk://192.168.1.119:2181/marathon
    页面访问：http://192.168.1.140:8080/
4、Mesos-marathon-lb（marathon负载均衡以及服务发现）
    拉取镜像：docker pull mesosphere/marathon-lb:v1.14.1
    docker运行脚本：
    #!/bin/bash
    docker run -d --net=host \
    	 -e PORTS=9090 mesosphere/marathon-lb:v1.14.1 sse \
     	 --group external \
    	 --marathon http://192.168.1.140:8080
    页面访问：http://192.168.1.140:9090/haproxy?stats ;
    因为marathon-lb添加了外部group，所以marathon的应用都需要加上Label配置：HAPROXY_GROUP=external
5、如果API-Gateway无法访问绑定到域名，需要安装在marathon-lb那台主机上安装dns：yum install dnsmasq
    配置mesos-slave主机的docker配置：
        {
          "registry-mirrors": ["http://hub-mirror.c.163.com"],
          "insecure-registries": ["192.168.1.139:8080"],
          "dns": ["192.168.1.140"]
        }