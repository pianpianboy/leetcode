#

##32、IO体系架构系统与装饰者模式
java io 从功能上分为两大类：输入流和输出流
从流结构上可分为字节流（以字节为处理单位）和字符流（以字符为处理单位）
字节流的输入和输出流基础是InputStream和OutputStream这两个抽象类，字节流的输入输出操作由这两个类的子类实现
ByteStreams : InputStream OutputStream
CharacterSteams : Reader和Writer

### 对于Java IO也就是阻塞的IO的读取数据的逻辑：
- open a stream
- while more information
- read information
- close the stream

### 对于Java IO也就是阻塞的IO的写数据的逻辑：
- open a stream
- while more information
- write information
- close the stream

### InputStream
- 钟包含一套字节输入流需要的方法，可以完成最基本的从输入流读入数据的功能。当java程序需要外设的数据时，可根据数据的不同形式，创建一个适当的InputSteam子类类型的对象来完成与该外设的连接。
- Input Stream Chain
    + file-->FileInputStream-->BufferedInputSteam-->DataInputStream-->数据
    + 文件-->从文件钟获取输入字节-->增加了缓冲的功能-->增加了读取Java基本数据类型的功能

### OutputStream Chain
- Output Stream Chain
- 数据-->DataOutputStream-->BufferedInputSteam-->FileOnputStream-->file
- 可以往输出流中写入Java基本类型数据-- 提供数据写入到缓存区的功能--将数据写入到文件

### Decorator（装饰）设计模式
- Java的IO库提供了一个称作链接的机制，可以将一个流与另一个流首尾相接，形成一个流管道的链接，这种机制实际上是一种被称为 Decorator（装饰）设计模式
- 装饰模式又名包装（Wrapper）模式
- 装饰模式以对客户端透明的方式扩展**对象**的功能，是继承关系的一个替代方案，继承是对类的一个扩展
- 装饰模式以对客户透明的方式*动态*的给一个对象附加上更多的责任，换句话，客户端并不会觉得对象在装饰前和装饰后有什么不同
- **装饰模式可以在不创造更多之类的情况下，将对象的功能加以扩展**
- 装饰模式是在不必改变原类文件和使用继承的情况下动态的扩展一个对象的功能。他是通过创建一个包装对象，也就是装饰来包裹真实的对象。
-
#### 装饰模式的特点
- 装饰对象和真实对象有相同的接口，这样客户端哦对象就可以和真实对象相同的方式和装饰对象交互。
- *装饰对象包含一个真实对象的引用*
- 装饰对象接收所有来自客户端的请求，它把这些请求转发给真实的对象
- 装饰对象可以在转发这些请求以前或以后增加一些附加功能，这样确保了在运行时，不用修改给定兑现个的结构就可以在外部增加附加功能。在面向对象的设计钟，通常是通过继承来实现给定 类的功能扩展。

#### 编码
```java
//抽象构建角色(Component): 给出一个抽象接口
public interface Component {//可以理解为inputStream
    //抽象的构建角色
    void doSomething();
}
```
```java
//具体构件角色（Concrete Component）：定义一个将要接收附加责任的类
public class ConcreteComponent implements Component{
    //具体的构建角色
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
```

```java
//装饰角色（Decorator）：
public class Decorator  implements Component{
    //装饰角色: 1、装饰角色必须实现抽象构建角色，2、装饰角色要持有一个抽象构建角色的引用
    private Component component;

    public Decorator(Component component){
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }

}
```

```java
//具体装饰角色（Concrete Decorator）
public class ConcreteDecorator1 extends Decorator{
    //具体的装饰角色
    public ConcreteDecorator1(Component component) {
        super(component);
    }
    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnoThing();
    }
    private void doAnoThing(){
        System.out.println("功能B");
    }
}
```
#### 装饰模式VS继承
- 装饰模式
    + 用来扩展特定 **对象**的功能
    + 不需要子类
    + **动态**
    + 对于一个给定的对象，同时可能有不同的装饰对象，客户端可以通过它的需要选择何时的装饰对象发送消息
- 继承
    + 用来扩展 **一类对象的功能（就是扩展类）**
    + 需要子类
    + **静态**

装饰模式的适用性：

- 想要透明并且动态地给对象增加新的职责（方法）而又不会影响其他对象
- 给对象增加的方法在未来可能会发生改变
- 用子类扩展功能不实际的情况下

#### javaIO中装饰模式
inputStream --> FilterInputStream --> BufferedInputStream



##33、JavaNIO深入详解与体系分析


##34、Buffer中各个重要状态属性的含义与关系图解


##35、Java NIO核心类源码解读与分析



String 优化
并发：
算法--极客动态规划
jvm--总结
kafka--如何保证不丢失消息
Flink yarn部署

晚上：
并发编程
性能调优
