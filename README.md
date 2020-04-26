## req-logs-spring-boot-starter

### 一、简介

- 说明：接口请求日志记录
- 适用范围：springboot+springMVC项目


### 二、引入依赖

###### gradle：
```groovy
    compile 'com.opensource.component:req-logs-spring-boot-starter:1.0.0-SNAPSHOT'
```

###### maven:
```xml
    <dependency>
          <groupId>com.opensource.component</groupId>
          <artifactId>req-logs-spring-boot-starter</artifactId>
          <version>1.0.0-SNAPSHOT</version>
    </dependency>
```


### 三、配置

```yaml
component:
  req:
    logs:
      enabled: true
      slowReqElapsed: 3000
```

- enabled: 日志记录开关，默认true，开启拦截器记录请求日志，
- slowReqElapsed: 慢请求耗时，单位毫秒，默认3000ms，超过这个时间的请求为慢请求，记录慢请求日志。
- 以上参数可以不设置，默认开启日志记录，慢请求耗时为3000ms


### 四、接口请求日志输出样例

```shell
Start: /main/sendMsg
invoke /main/sendMsg, Params: {"fromId":["555"],"type":["555"],"cmd":["206"],"tioCmd":["3"],"groupId":["1249896320500555835"],"playerId":["null"],"data":["{\"zilionaire\":{\"playerId\":\"1531364846326\",\"headimg\":\"http://img.itmajor.cn/WJTX-MAN-20.jpg\",\"name\":\"12732638\",\"experience\":10281.20,\"status\":null,\"enablePlay\":true,\"agentId\":94,\"lineId\":79},\"clever\":null,\"leftPlayers\":[{\"playerId\":\"1531364844278\",\"headimg\":\"http://files.kf1999.cn:81/files/admin/WJTX-MAN-11.jpg\",\"name\":\"13870062\",\"experience\":9227.25,\"status\":null,\"enablePlay\":true,\"agentId\":67,\"lineId\":82},{\"playerId\":\"1531364845795\",\"headimg\":\"http://img.itmajor.cn/WJTX-MAN-03.jpg\",\"name\":\"245891\",\"experience\":9494.00,\"status\":null,\"enablePlay\":true,\"agentId\":99,\"lineId\":87},null],\"rightPlayers\":[{\"playerId\":\"1531364845833\",\"headimg\":\"http://img.itmajor.cn/WJTX-MSN-04.jpg\",\"name\":\"93790383\",\"experience\":10205.40,\"status\":null,\"enablePlay\":true,\"agentId\":96,\"lineId\":50},{\"playerId\":\"1531364844054\",\"headimg\":\"http://img.itmajor.cn/WJTX-MSN-21.jpg\",\"name\":\"03932091\",\"experience\":5534.15,\"status\":null,\"enablePlay\":true,\"agentId\":50,\"lineId\":59},{\"playerId\":\"1531364844245\",\"headimg\":\"http://img.itmajor.cn/WJTX-MAN-14.jpg\",\"name\":\"23457046\",\"experience\":6201.05,\"status\":null,\"enablePlay\":true,\"agentId\":72,\"lineId\":52}]}"]}
End /main/sendMsg, executeTime: 0 ms
```