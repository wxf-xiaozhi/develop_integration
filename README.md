# 微服务公共集成包

## 微服务项目可集成此包，

### 1、本地环境与开发环境集成调试，
开发环境请求可路由到本地，本地处理完成后又可以回归到开发环境，做到集成调试时，本地只需要启动自己改动的服务，无需启动请求相关的所有服务。代码随写随测，无需提交本地代码到git仓库再进行部署，提升研发效率。
通过，feign拦截器解析器，自定义负载均衡器，防止开发环境请求被本地服务被本地服务污染，让本机可以只启动自己需要开发的服务，其他服务用线上来进行调试，请求时开发人员
### 2、自定义feign日志级别
### 3、tranceID穿透


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
 # 开发环境的ip
 defaultIp: 10.66.146.37
````


