### jenkins 安装指南
1、jenkins.io 获取安装教程，支持多种方式
2、安装好之后查看运行日志获取admin的密码，然后进入jenkins的基础配置，添加pipeline插件
3、创建用户名和密码
4、在jenkins的全局配置里 ```勾选 匿名用户具有可读权限 和 关闭 防止跨站点请求伪造``` 
5、添加jenkins item，输入任务名称 --> 流水线 --> 构建触发器：触发远程构建 (例如,使用脚本)，将JENKINS_URL/job/2qq1/build?token=TOKEN_NAME 或者 /buildWithParameters?token=TOKEN_NAME添加到Git/GitHub/Gitlab的WebHooks中
6、在流水线里添加执行命令