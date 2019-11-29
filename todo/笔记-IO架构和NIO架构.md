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

##38、NIO堆外内存与零拷贝深入讲解
```java
//例子
public class Test9 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while(true){
            buffer.clear();
            int read = inputChannel.read(buffer);
            System.out.println("read: "+read);

            if(-1==read){
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}
```
```java
ByteBuffer.allocateDirect(512)
---->
public static ByteBuffer allocateDirect(int capacity){
        return new DirectByteBuffer(capacity)
}
---->
DirectByteBuffer.java
DirectByteBuffer extends MappedByteBuffer extends ByteBuffer  extends  Buffer

private final Cleaner cleaner;
//构造函数
DirectByteBuffer(int cap) {
    base = unsafe.allocateMemory(size);
    unsafe.setMemory(base, size, (byte) 0);
    address = base + ps - (base & (ps - 1));
    cleaner = Cleaner.create(this, new Deallocator(base, size, cap));
}

Buffer.java{
    在Buffer类中有个long类型的变量address
    // Used only by direct buffers
    long address;
}

```
- HeapByteBuffer是一个纯粹的java对象没有调用unsafe方法
- 关于直接缓存主要由两部分组成：
    + JVM 堆中 DirectByteBuffer对象（在调用ByteBuffer.allocateDirecte()时候会执行 new DirectByteBuffer(), new出来的对象都是分配到JVM的堆中）
    + native堆中 ，因为在DirectByteBuffer的构造函数中调用unsafe.allocateMemory(size);
- 在DirectByteBuffer对象中有一个变量能引用native内存中的数据，这个引用就是address
- 参考图：netty-directbytebuffer-38.jpg

### 问题：为什么不把DirectByteBuffer引用的数据放在JVM的堆内存中呢？
- 答：实际上是出于效率考虑
    + 在使用heapByteBuffer进行数据写入会发生什么事情？
        * 使用HeapByteBuffer时，HeapByteBuffer对象及对象封装的字节数组都是分配到JAVA堆中欧过的，然而操作系统并不是直接处理HeapByteBuffer在java堆上封装的字节数组
        * 实际上是操作系统在JAVA内存结构外面开辟一块内存区域，将HeapByteBuffer里面的数据拷贝到这块开辟的内存区域，然后再把这块区域的数据直接拿出来跟IO设备打交道，进行数据的读取或者数据的写入，或者说我们使用HeapByteBuffer，我们多了一次拷贝
    + 如果使用的是DirectByteBuffer进行数据写入？
        * JVM堆上面就不会存在一个直接数组了。因为真正的数据已经在堆外放着了
        * 如果进行数据读写的话，直接由堆外内存和IO设备打交道
        * 这也叫零拷贝

### 问题：为什么操作系统不直接的操作JVM堆上的数据？
- 答：其实操作系统是可以访问JVM堆上的内存的，在内核态的一个场景下是可以访问任何一块内存区域的。既然能访问，为什么又要拷贝一份出来呢？
    + 操作系统访问JVM堆上的内存，一定是通过JNI的方式去访问的，这个访问的前提就是这块内存区域是确定的。
    + 然而你正在访问这块内存区域的时候，突然在这块区域进行了垃圾回收GC
        * 垃圾回收有多种回收算法，除了CMS（标记清除算法）外其它的垃圾回收算法都会进行先标记再压缩的过程。而这个压缩过程涉及到内存拷贝。
        * 比如YGC就是复制清除算法。
        * 如果native正在操作这个数据，然而这个数据发生了移动的话，可能出现数据错误，甚至出现outofMemory这样的错误
    + 所以由两种办法：
        * 一种是让native操作jvm堆中对象的时候，让这个对象固定，这不太现实的
        * 把这个内存的对象拷贝到堆外，而这个拷贝的动作是很快的，而IO又是比较慢，所以这个拷贝动作对于IO操作来说是比较高效的

### 问题：对于零拷贝（堆外内存）的数据是如何进行回收（释放的）？
- 因为address会维护堆外内存的一个引用
- 对于jvm堆而言：当DirectByteBuffer被回收掉之后，是能根据address找到堆外内存
- 然后通过JNI的方式释放掉堆外内存
- 内存溢出的情况是不会发生的

##39、NIO中Scattering与Gathering深度解析
- MappedBgteBuffer.java
    + MappedByteBuffer是一个文件的直接内存缓冲区域 A direct byte buffer whose content is a memory-mapped region of a file.
    + 这个映射是一直存在的，知道buffer被GC 。 A mapped byte buffer and the file mapping that it represents remain valid until the buffer itself is garbage-collected.
    + 也就是我们只需要操作内存，就能将修改直接写到磁盘文件中
    + MappedByteBuffer就是：内存映射文件就是一种容许java从内存访问的文件。我们可以将整个文件或者文件的一部分映射到内存中，由操作系统将内存修改写入磁盘文件中，我们的应用程序只需要处理内存数据，这样可以实现非常迅速的IO操作，用于内存映射的文件的内存本身是在JVM堆外，也就是堆外内存
- 例子:关于scattering与gathering的例子

```java
public class Test10 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);

        serverSocketChannel.socket().bind(address);

        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 2+3+4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        while(true){
            int bytesRead = 0;
            while(bytesRead<messageLength){
                long r = socketChannel.read(buffers);
                bytesRead += r;
                System.out.println("bytesRead: "+ bytesRead);

                Arrays.asList(buffers).stream().map(buffer->
                        "position: "+buffer.position()+",limit:"+buffer.limit()).forEach(System.out::println);
            }


            Arrays.asList(buffers).forEach(buffer->{
                buffer.flip();
            });

            long bytesWriten = 0;
            while(bytesWriten<messageLength){
                long r = socketChannel.write(buffers);
                bytesWriten += r;
            }
            Arrays.asList(buffers).forEach(buffer->{
                buffer.clear();
            });

            System.out.println("bytesRead: "+bytesRead+
                    ",bytesWrittens: "+bytesWriten+",messageLength"+messageLength);
        }

    }
}
```


String 优化
并发：
算法--极客动态规划
jvm--总结
kafka--如何保证不丢失消息
Flink yarn部署

晚上：
并发编程
性能调优
