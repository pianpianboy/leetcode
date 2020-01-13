## Spring中的Bean是线程安全的吗？

#### Spring容器中的Bean可以分为5个范围
1. singleton: 默认，每个容器中只有一个bean的实例
2. protype: 为每一个bean请求提供一个实例
一般来说下面几种作用域，在开发的时候一般都不会用。99.99%的时候都是用singleton单例作用域
3. request：为每一个网络请求创建一个实例，在请求完成之后，bean会失效并被垃圾回收器回收
4. session：与request范围类似，确保每个session中有一个bean的实例，在session过期后，bean也会随之失效
5. global-session

#### Spring中的Bean **不是**线程安全的
SpringBean默认来说，singleton，都不是线程安全的，java web系统，一般来说很少在Spring bean里面放一些实例变量，一般来说他们都是多个组件互相调用，最终去访问数据库的。

如下：
```java
@Controller
public class MyController{
    @Resource
    private MyService myService;

    public void doRequest(){
        myService.doService();
    }
}

@Service
public class MyServiceImpl implements Myservice{
    public void doService(){
        //访问数据库
    }
}
```