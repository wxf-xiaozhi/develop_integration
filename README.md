# integration项目

## 微服务项目可集成此包，

### 1、自定义负载均衡器，防止线上服务被本地服务污染，让本机可以只启动自己需要开发的服务，其他服务用线上
### 2、header头添加自己服务，程序员用来调试、
### 3、自定义feign日志级别
### 4、tranceID穿透


## 功能开关配置模版

````
custom:
 feignconfig:
  # 通用的feignconfig功能开启
  isOpen: true
  # feign调用日志的格式设置
  loggerFormat: BASIC
  # feign调用日志级别设置
  loggerLevel: info
  # header头自定义请求的服务的功能开关
  customfeign: true
  # feign调用的tranceID功能开启
  tranceId: false
  

# 防止污染线上环境请求功能开启
loadbalancer:
 # 线上环境的ip
 defaultIp: 10.66.146.37
````


