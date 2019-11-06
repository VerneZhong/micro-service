# Docker 公有仓库（hub.docker.io）
docker tag zhongxuebin/redis:latest
docker login
docker push zhongxuebin/redis:latest

# Docker 私有仓库（registry）
docker pull registry:2.7
docker run -d -p 5000:5000 --restart always --name registry registry:2.7

# 使用私有仓库
docker pull ubuntu
docker tag ubuntu localhost:5000/ubuntu
docker push localhost:5000/ubuntu

# 使用harbor
1、安装Harbor：https://github.com/goharbor/harbor/releases
2、修改harbor.yml的配置文件，hostname和port
3、执行./install脚本安装
4、安装完成后，设置docker支持http方式：vim /etc/docker/daemon.json
{
  # 设置镜像地址
  "registry-mirrors": ["http://hub-mirror.c.163.com"],
  # 设置http不安全的地址列表
  "insecure-registries": ["192.168.1.139:8080"]
}
之后，执行以下命令
systemctl daemon-reload
systemctl restart docker