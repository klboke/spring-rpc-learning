# spring-rpc
什么是rpc？
rpc（Remote Procedure Call）封装了内部实现的远程调用过程就是rpc，rpc主要为了简化远程服务调用。java体系的rpc，除了dubbo，集java大成者spring还默认提供了如下等Rpc组件支持。本项目就是这些Rpc组件的接入使用示例，基础环境使用spring boot2.x版本
- spring-rmi-rpc
- spring-hessian-rpc
- spring-http-rpc
- spring-jms-rpc
- spring-amqp-rpc
- spring-ws-rpc

------------

> 基础环境

- spring boot 2.0.5.RELEASE
- activemq （spring-jms-rpc使用）
- RabbitMQ （spring-amqp-rpc使用）

> 项目结构说明

整个项目maven层级分为三级，最外层包含RPC模块和基本的api模块，api模块会被所有的RPC模块引用。RPC模块内包含对应的提供者和消费者，比如spring-rpc-jms模块下，有jms-consumer，jms-provider，其他的类推

> RPC实现说明

每个RPC都会实现api模块的抽象接口，如下：
```java
/**
 * @WebService 注解只用于ws 提供的RPC服务
 */
@WebService
public interface AccountService {
     Account getAccount(String name);
}
```
以spring-ws-rpc作为例子，服务提供者实现如下，用以暴露服务：
```java
@WebService(serviceName="AccountService",endpointInterface = "com.spring.rpc.api.AccountService")
@Service
public class AccountServiceImpl  extends SpringBeanAutowiringSupport implements  AccountService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @WebMethod
    public Account getAccount(String name) {
        logger.info("{} 请求获取账号！", name);
        Account account = new Account();
        account.setName(name + "的账号");
        return account;
    }
}
```
ws提供者配置：
```java
/**
 * Created by kl on 2018/9/29.
 * Content : ws服务提供者配置
 */
@Configuration
public class WsConfig {
    @Bean
    public SimpleJaxWsServiceExporter rmiServiceExporter() {
        SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
        exporter.setBaseAddress("http://127.0.0.1:8083/");
        return exporter;
    }
}
```
服务消费者，消费服务配置实现如下：
```java
/**
 * Created by kl on 2018/9/29.
 * Content :ws消费者（服务调用者）配置
 */
@Configuration
public class WsConfig {
    @Bean("accountService")
    public JaxWsPortProxyFactoryBean accountService()throws Exception{
        JaxWsPortProxyFactoryBean factoryBean = new JaxWsPortProxyFactoryBean();
        factoryBean.setServiceName("AccountService");
        factoryBean.setPortName("AccountServiceImplPort");
        factoryBean.setNamespaceUri("http://provider.ws.rpc.spring.com/");
        URL wsdlDocumentUrl = new URL("http://127.0.0.1:8083/AccountServiceImpl?WSDL");
        factoryBean.setWsdlDocumentUrl(wsdlDocumentUrl);
        factoryBean.setServiceInterface(AccountService.class);
        return factoryBean;
    }
}
```
如上,就可以在spring上下文环境中注入服务，调用服务：
```java
	@Autowired
	private AccountService accountService;

	@PostConstruct
	public void callRpcService(){
		System.out.println("RPC远程访问开始！");
		System.err.println(accountService.getAccount("kl").getName());
		System.out.println("RPC远程访问结束！");
	}
```
