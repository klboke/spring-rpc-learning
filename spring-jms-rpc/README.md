#activemq安全策略
默认activemq会有安全策略限制，会抛如下异常：

Caused by: java.lang.ClassNotFoundException: Forbidden class org.springframework.remoting.support.RemoteInvocation! This class is not trusted to be serialized as ObjectMessage payload. Please take a look at http://activemq.apache.org/objectmessage.html for more information on how to configure trusted classes.

具体解决办法参考我的博客：http://www.kailing.pub/article/index/arcid/193.html