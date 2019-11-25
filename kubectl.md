# 添加命令
[root@server02 bin]# chmod +x calicoctl
[root@server02 bin]# cp calicoctl /usr/local/bin/

kubernetes 集群搭建参考：https://github.com/liuyi01/kubernetes-starter/blob/master/docs/1-pre.md

# Kubectl 常用命令
# 获取所有工作节点
kubectl get nodes 

# 获取pods
kubectl get pods

# 查看pods更多信息
kubectl get pods -o wide

# 根据label查看pods
kubectl get pods -l app=nginx

# 创建应用
kubectl run kubernetes-bootcamp --image=jocatalin/kubernetes-bootcamp:v1 --port=8080

# 查看pods日志
kubectl logs -f podName[kubernetes-bootcamp-6b7849c495-7fl22]

# 查看集群部署的应用
kubectl get deployments

# 删除应用
kubectl delete deployments kubernetes-bootcamp

# 查看所有deploy详细信息
kubectl describe deploy

# 查看deploy的应用详细信息
kubectl describe deploy kubernetes-bootcamp

# 查看pods的应用详细信息
kubectl describe pods kubernetes-bootcamp

# 通过proxy访问应用，本机启动一个监听端口，测试方式访问pods应用
kubectl proxy
curl 127.0.0.1:8001/api/v1/proxy/namespaces/default/pods/kubernetes-bootcamp-6b789c495-94fdj/

# 扩缩容
kubectl scale deploy kubernetes-bootcamp --replicas=4

# 更新应用镜像
kubectl set image deploy kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2
kubectl set image pods nginx nginx=1.7.9
# 查看镜像更新结果
kubectl rollout status deploy kubernetes-bootcamp

# 更新回滚，错误操作后可回滚deploy   
kubectl rollout undo deploy kubernetes-bootcamp

# 根据yaml文件，创建pods或是deploy
kubectl create -f nginx-pod.yaml 

# 根据yaml文件，修改并创建一个应用
kubectl apply -f nginx-pods.yaml

# yaml文件：
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  containers:
    - name: nginx
      image: nginx:1.7.9
      ports:
      - containerPort: 80
      
# deploy文件
apiVersion: apps/v1beta1
kind: Deployment
metadata:
  name: nginx-deploy
spec:
  replicas: 2
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.7.9
        ports:
          - containerPort: 80
# service 文件
apiVersion: v1
kind: Service
metadata:
  name: nginx-service
spec:
  ports:
  - port: 8080
    targetPort: 80
    nodePort: 20000
  selector:
    app: nginx
  type: NodePort

# 提供外部访问
kubectl proxy

# 访问暴露的服务 kubectl proxy
curl http://localhost:8001/api/v1/proxy/namespaces/default/pods/nginx/

# 查看kubernetes服务
kubectl get services

# 暴露deploy，以供kube-proxy访问
kubectl expose deploy kubernetes-bootcamp --type="NodePort" --target-port=8080 --port=80

# 工作节点启动了Kube-proxy后，就可以访问任意节点动服务了

# 通过命名空间获取service
kubectl -n kube-system get svc

# 进入pod容器
kubectl exec -it kubernetes-bootcamp-6b7849c495-7fl22 bash

# 查看 service account
kubectl get serviceaccount/sa

# 查看service account yaml/json文件
kubectl get sa -o yaml/json

# 启动沙盒测试，进入容器内部
kubectl run busybox --rm=true --image=busybox --restart=Never --tty -i