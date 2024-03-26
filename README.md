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

### traceId打印进阶用法


常规用法仅支持web请求携带traceId，进阶用法将增加其他支持类型。

1. 支持<code>@Scheduled</code>调度方法或者其他非web请求携带<code>traceId</code>。标注<code>@LogTrace</code>注解即可，如

```
    @Scheduled(fixedRate = 5000)
    @LogTrace
    public void function1(){
    ...
    }
```

2. 支持子线程携带traceId。使用<code>AbstractLogTraceRunnable</code>代替<code>Runnable</code>,
   <code>AbstractLogTraceCallable</code>代替<code>Callable</code>即可，如

```
 executorService.execute(new AbstractLogTraceRunnable() {
      @Override
      public void logTraceRun() {
          try {
              log.info("多线程方法执行");
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  });
```

3. 支持@Async注解标注的方法携带traceId。需要自定义线程池时，指定ThreadFactory为<code>LogTraceThreadFactory</code>,如：

```
//定义线程池
@Configuration
public class SomeConfig {
   @Bean
   public ThreadPoolTaskExecutor myTask() {
      ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
      taskExecutor.setCorePoolSize(2);
      taskExecutor.setMaxPoolSize(10);
      taskExecutor.setQueueCapacity(10);
      taskExecutor.setKeepAliveSeconds(10);
      taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
      taskExecutor.setThreadFactory(new LogTraceThreadFactory("logTraceThreadPool-"));
      return taskExecutor;
   }
}

//使用@Async注解
@Async("myTask")
public void async() {
   log.info("async方法执行");
}
```


#### 请求url，请求人的打印

1. 开启即可打印请求url。
2. 某些请求url设置为不打印。

```properties
log.request.no-filter=/api/log/nofilter/**

```

注意：不写时，默认值为

```properties
["/swagger-resources", "/swagger-resources/**", "/v2/api-docs", "/v2/api-docs-ext","/error", "
/doc.html", "/webjars/**", "/ping", "/api/prometheus"]
```

3. 如果想打印请求人，请实现配置接口<code>LoginUserConfigurer</code>自定义获取登录人的方式。如：

```java

import org.springframework.stereotype.Component;

@Component
public class LoginUserConfig implements LoginUserConfigurer {
   @Override
   public String getCurrentName() {
      return LoginUserHelper.getCurrentUserName();
   }
}

```



