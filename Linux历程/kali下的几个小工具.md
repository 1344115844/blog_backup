## kali下的几个小工具

原创： 轩雨 [kali黑客教学](javascript:void(0);) *5天前*

![img](C:\Users\13441\Desktop\md\Linux历程\kali下的几个小工具.assets\640.gif)

**kali下的几个小工具**

1

## 防火墙识别工具wafw00f

#### 一：简介

`wafw00f` 是python脚本,用于检测网络服务器是否处于网络应用的防火墙(WAF,web application firewall)保护状态

#### 二：使用

```
wafw00f www.baidu.com
```

![img](C:\Users\13441\Desktop\md\Linux历程\kali下的几个小工具.assets\640-1528260578993)

2

## 

## zip密码破解小脚本

#### 一：文件源码

```
import zipfile
import optparse
from threading import Thread
def extractFile(zFile,password):
    try:
        zFile.extractall(pwd=password)
        print '[+] Password = ' + password + '\n'
    except:
        pass
def main():
    parser = optparse.OptionParser("usage%prog " + "-f <zipfile> -d <dictionary>")
    parser.add_option('-f', dest='zname', type='string', help='specify zip file')
    parser.add_option('-d', dest='dname', type='string', help='specify zip file')
    (options, args) = parser.parse_args()
    if(options.zname == None) | (options.dname == None):
        print parser.usage
        exit(0)
    else:
        zname = options.zname
        dname = options.dname
    zFile = zipfile.ZipFile(zname)
    passFile = open(dname)
    for line in passFile.readlines():
        password = line.strip('\n')
        t = Thread(target=extractFile, args=(zFile,password))
        t.start()
if __name__ == '__main__':
    main()
```

#### 二：使用教程

将文件保存为`bbskali.py`在执行下面命令

```
python bbskali.py -f 文件名 -d 字典名
```

![img](C:\Users\13441\Desktop\md\Linux历程\kali下的几个小工具.assets\640)