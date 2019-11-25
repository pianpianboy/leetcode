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


















##33、JavaNIO深入详解与体系分析