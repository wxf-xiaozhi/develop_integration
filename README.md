# 微服务公共集成包

## 微服务项目可集成此包，

### 1、本地环境与开发环境集成调试，
开发环境请求可路由到本地，本地处理完成后又可以回归到开发环境，做到集成调试时，本地只需要启动自己改动的服务，而无需启动请求相关的所有服务，解决由于本地机器性能限制，导致无法启动相关所有服务的问题，且代码可随写随测，无需提交本地代码进行部署在进行集成测试，提升研发效率。
通过，feign拦截器解析器，自定义负载均衡器，防止开发环境请求被本地服务被本地服务污染，让本机可以只启动自己需要开发的服务，其他服务用线上来进行调试，请求时开发人员需要在postman的请求的header上配置本机启动的服务名和IP，另外每个服务配置文件中需要配置开发环境默认的IP如下;
#### postman配置
<img width="1358" alt="image" src="https://github.com/wxf-xiaozhi/develop_integration/assets/18194835/8266668a-20a5-4e63-a61c-8f579975e59b">

#### 每个服务的配置
````
loadbalancer:
 # 开发环境的ip
 defaultIp: 10.66.146.37
````
### 2、自定义feign日志级别
支持重新设置feign的日志级别和格式，线上建议设置成basic和info
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
````

### 3、tranceID穿透
服务间tranceId传递，基于feign的请求都可以基于此传递



