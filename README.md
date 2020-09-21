# wangdefa_graduation_project

王德发的毕业设计

## 整体设计架构

### springcloud Alibaba



#### 8.20日志

gateway中web 和webflux不兼容

必须排除其中一个



小目标

- gateway鉴权

 

**nacos分布式配置中心**

==dataId设置==

```
在springcloud中data的格式
${prefix}-${spring.profiles.active}.${file-extension}
```

- `prefix` 默认为 `spring.application.name` 的值，也可以通过配置项 `spring.cloud.nacos.config.prefix`来配置。
- `spring.profiles.active` 即为当前环境对应的 profile，详情可以参考 [Spring Boot文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html#boot-features-profiles)。 **注意：当 `spring.profiles.active` 为空时，对应的连接符 `-` 也将不存在，dataId 的拼接格式变成 `${prefix}.${file-extension}`**

- `file-exetension` 为配置内容的数据格式，可以通过配置项 `spring.cloud.nacos.config.file-extension` 来配置。目前只支持 `properties` 和 `yaml` 类型。
- 通过 Spring Cloud 原生注解 `@RefreshScope` 实现配置自动更新

## _WebFlux_



3. 与openfegin请求路径引发冲突,导致报错,修改openfien