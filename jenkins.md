### jenkins 安装指南
1、jenkins.io 获取安装教程，支持多种方式
2、安装好之后查看运行日志获取admin的密码，然后进入jenkins的基础配置，添加pipeline插件
3、创建用户名和密码
4、在jenkins的全局配置里 ```勾选 匿名用户具有可读权限 和 关闭 防止跨站点请求伪造``` 
5、添加jenkins item，输入任务名称 --> 流水线 --> 构建触发器：触发远程构建 (例如,使用脚本)，将JENKINS_URL/job/2qq1/build?token=TOKEN_NAME 或者 /buildWithParameters?token=TOKEN_NAME添加到Git/GitHub/Gitlab的WebHooks中
6、在流水线里添加执行命令
7、遇到mvn没有编译没有命令：script.sh: mvn: not found
    解决方式:
    在Jenkins的设置中可以设置全局变量, jenkins主页面->Manage Jenkins->Configure System->Global Properties 中, 将Environment variables复选框选中,会出来List of variables, 填入以下内容:
    name: JAVA_HOME value:XXX
    name: M2_HOME value:/usr/cyz/apache-maven-3.6.1
    name: PATH+EXTRA value: $M2_HOME/bin
8、相关脚本
```
    ## 流水线脚本
    #!groovy
    pipeline {
    	agent any
    
    	environment {
    		repository="https://github.com/zhongxuebinwin8/micro-service.git"
    		module="user-edge-service"
    		script_path="/home/zxb/scripts/"
    	}
    
    	stages {
    		stage('pull code') {
    			steps {
    				echo "start fetch code from git:${repository}"
    				deleteDir()
    				git "${repository}"
    			}
    		}
    
    		stage('code static check') {
    			steps {
    				echo "start code check"
    			}
    		}
    
    		stage('compile code and unit test') {
    			steps {
    				echo "start compile"
    				sh "mvn -U -pl ${module} -am clean package"
    			}
    		}
    
    		stage('build image') {
    			steps {
    				echo "start bulid image"
    				sh "${script_path}/build-images.sh ${module}"
    			}
    		}
    
    		stage('release system') {
    			steps {
    				echo "start deploy"
    				sh "${script_path}/deploy.sh user-service-deployment ${module}"
    			}
    		}
    	}
    }
```
```
    ## 编译脚本
    #!/bin/bash
    MODULE=$1
    TIME=`date "+%Y%m%d%H%M"`
    GIT_REVISION=`git log -1 --pretty=format:"%h"`
    IMAGE_NAME=hub.zxb.com:8080/micro-service/${MODULE}:${TIME}_${GIT_REVISION}
    
    cd ${MODULE}
    
    docker build -t ${IMAGE_NAME} .
    cd -
    
    docker login -u admin -p Harbor12345  hub.zxb.com:8080
    docker push ${IMAGE_NAME}
    echo "${IMAGE_NAME}" > IMAGE_NAME        

```
```
    ## 发布脚本  
    #!/bin/bash
    IMAGE=`cat IMAGE_NAME`
    DEPLOYMENT=$1
    MODULE=$2
    echo "update image to:${IMAGE}"
    kubectl set image deployment ${DEPLOYMENT} ${MODULE}=${IMAGE}
    echo "command:kubectl set image deploy ${DEPLOYMENT} ${MODULE}=${IMAGE}"
```  
