# NIO知识分享

## Linux下的五种I/O模型

1.阻塞I/O（blocking I/O）
2.非阻塞I/O （nonblocking I/O）
3.I/O多路复用 （I/O multiplexing）
4.信号驱动I/O（signal driven I/O (SIGIO)）
5.异步I/O（asynchronous I/O (the POSIX aio_functions)）

### BIO (blocking I/O)

同步阻塞I/O模式，数据的读取写入必须阻塞在一个线程内等待其完成
**阻塞过程**：当用户线程发出IO请求之后，内核会去查看数据是否就绪，如果没有就绪就会等待数据就绪，而用户线程就会处于阻塞状态，用户线程交出CPU。当数据就绪之后，内核会将数据拷贝到用户线程，并返回结果给用户线程，用户线程才解除block状态
**阻塞原因**：用户线程无法访问内核空间，只能访问用户空间
**阻塞本质**：调用io函数时能否立即返回结果

### NIO（non-blocking I/O）

同步非阻塞I/O。不管是否有获取到数据，都会立马获取结果，如果没有获取数据的话、那么就不间断的循环重试(实际上还是要去轮询数据准没准备好)

### I/O多路复用（I/O multiplexing）

虽然用了这个名字，但是确实已经发现不少对这个翻译的吐槽。简单来说，就是OS提供了I/O流的注册、跟踪和管理的机制，从而将轮询这个操作从用户线程中释放出来

#### select、poll、epoll

<https://www.cnblogs.com/aspirant/p/9166944.html>

### 信号驱动I/O（signal driven I/O (SIGIO)）

在信号驱动IO模型中，当用户线程发起一个IO请求操作，会给对应的socket注册一个信号函数，然后用户线程会继续执行，当内核数据就绪时会发送一个信号给用户线程，用户线程接收到信号之后，便在信号函数中调用IO读写操作来进行实际的IO请求操作

### AIO（asynchronous I/O）

在异步IO模型中，当用户线程发起read操作之后，立刻就可以开始去做其它的事。而另一方面，从内核的角度，当它收到一个asynchronous read之后，它会立刻返回，说明read请求已经成功发起了，因此不会对用户线程产生任何block。然后，内核会等待数据准备完成，然后将数据拷贝到用户线程，当这一切都完成之后，内核会给用户线程发送一个信号，告诉它read操作完成了。也就说用户线程完全不需要知道实际的整个IO操作是如何进行的，只需要先发起一个请求，当接收内核返回的成功信号时表示IO操作已经完成，可以直接去使用数据了

### 信号驱动I/O与AIO

信号驱动I/O在接收到信号之后，仍要调用IO读写操作，但AIO不用

### 非阻塞的实质

别人帮你实现了轮询

## Java的NIO与AIO

JDK1.4提供的是同步非阻塞IO，属于I/O多路复用（从Selector这个概念就能看出来）。JDK1.7以后是异步非阻塞,AIO

### Channel、Buffer、Selector

![avatar](javanio.png)

这是一篇讲Java NIO的文章，蛮全的
<https://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html>
