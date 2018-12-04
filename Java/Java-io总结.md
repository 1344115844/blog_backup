![I:Oä½ç³".png](C:\Users\13441\Desktop\blog_backup\Java\Java-io总结.assets\1752522-adc85fb95f884363.png) 

![I/Oæµ](C:\Users\13441\Desktop\blog_backup\Java\Java-io总结.assets\1752522-ee60b12bd1f9a3dc.png)

**按来源/去向分类：**

1. File（文件）： FileInputStream, FileOutputStream, FileReader, FileWriter
2. byte[]：ByteArrayInputStream, ByteArrayOutputStream
3. Char[]: CharArrayReader, CharArrayWriter
4. String: StringBufferInputStream, StringReader, StringWriter
5. 网络数据流：InputStream, OutputStream, Reader, Writer

### InputStream

InputStream 为字节输入流，它本身为一个抽象类，必须依靠其子类实现各种功能，此抽象类是表示字节输入流的所有类的超类。 继承自InputStream 的流都是向程序中输入数据的，且数据单位为字节（8bit）；

InputStream是输入字节数据用的类，所以InputStream类提供了3种重载的read方法.Inputstream类中的常用方法：

- public abstract int read( )：读取一个byte的数据，返回值是高位补0的int类型值。若返回值=-1说明没有读取到任何字节读取工作结束。
- public int read(byte b[ ])：读取b.length个字节的数据放到b数组中。返回值是读取的字节数。该方法实际上是调用下一个方法实现的
- public int read(byte b[ ], int off, int len)：从输入流中最多读取len个字节的数据，存放到偏移量为off的b数组中。
- public int available( )：返回输入流中可以读取的字节数。注意：若输入阻塞，当前线程将被挂起，如果InputStream对象调用这个方法的话，它只会返回0，这个方法必须由继承InputStream类的子类对象调用才有用，
- public long skip(long n)：忽略输入流中的n个字节，返回值是实际忽略的字节数, 跳过一些字节来读取
- public int close( ) ：使用完后，必须对我们打开的流进行关闭。

来看看几种不同的InputStream：

1. FileInputStream把一个文件作为InputStream，实现对文件的读取操作
2. ByteArrayInputStream：把内存中的一个缓冲区作为InputStream使用
3. StringBufferInputStream：把一个String对象作为InputStream
4. PipedInputStream：实现了pipe的概念，主要在线程中使用
5. SequenceInputStream：把多个InputStream合并为一个InputStream

### OutputStream

OutputStream提供了3个write方法来做数据的输出，这个是和InputStream是相对应的。

- public void write(byte b[ ])：将参数b中的字节写到输出流。
- public void write(byte b[ ], int off, int len) ：将参数b的从偏移量off开始的len个字节写到输出流。
- public abstract void write(int b) ：先将int转换为byte类型，把低字节写入到输出流中。
- public void flush( ) : 将数据缓冲区中数据全部输出，并清空缓冲区。
- public void close( ) : 关闭输出流并释放与流相关的系统资源。

几种不同的OutputStream：

1. ByteArrayOutputStream：把信息存入内存中的一个缓冲区中
2. FileOutputStream：把信息存入文件中
3. PipedOutputStream：实现了pipe的概念，主要在线程中使用
4. SequenceOutputStream：把多个OutStream合并为一个OutStream

Reader和InputStream类似；Writer和OutputStream类似。

有两个需要注意的：

1. **InputStreamReader** ： 从输入流读取字节，在将它们转换成字符。
2. **BufferReader :**接受Reader对象作为参数，并对其添加字符缓冲器，使用readline()方法可以读取一行。

### 如何选择I/O流

 

1. 确定是输入还是输出
   输入:输入流 InputStream Reader
   输出:输出流 OutputStream Writer
2. 明确操作的数据对象是否是纯文本
   是:字符流 Reader，Writer
   否:字节流 InputStream，OutputStream
3. 明确具体的设备。
   - 文件：
     读：FileInputStream,, FileReader,
     写：FileOutputStream，FileWriter
   - 数组：
     byte[ ]：ByteArrayInputStream, ByteArrayOutputStream
     char[ ]：CharArrayReader, CharArrayWriter
   - String：
     StringBufferInputStream(已过时，因为其只能用于String的每个字符都是8位的字符串), StringReader, StringWriter
   - Socket流
     键盘：用System.in（是一个InputStream对象）读取，用System.out（是一个OutoutStream对象）打印
4. 是否需要转换流
   是，就使用转换流，从Stream转化为Reader、Writer：InputStreamReader，OutputStreamWriter
5. 是否需要缓冲提高效率
   是就加上Buffered：BufferedInputStream, BufferedOuputStream, BufferedReader, BufferedWriter