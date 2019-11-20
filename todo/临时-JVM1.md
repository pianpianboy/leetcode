# JMAP 与JStat

## Jmap命令
jmap -dump:live,format=b,file=dump.hprof PID



## jhat命令
jhat在浏览器中分析堆转出快照
接着就可以使用jhat去分析堆快照了，jhat内置了web服务器，他会支持你通过浏览器来以图形化的方式分析堆转储快照
使用如下命令即可启动jhat服务器，还可以指定自己想要的http端口号，默认是7000端口号

- /Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/bin/jhat dump.hprof
- 在浏览器中访问 localhost:7000
