## 突破百度云上传限速

原创： 轩雨 [kali黑客教学](javascript:void(0);) *昨天*

> 说明：上传慢是正常的，这跟百度云没多大关系，而是你宽带的运营商的事，一般宽带都会有一个上传速度和下载速度：
> 1.一般家用宽带，上传速度一般只有下载速度的八分之一，比如你是10M宽带，下载速度有1M/S，但是上传，可能就只有一两百，这很正常。
> 2.而且，很多运营商也对用户进行了上传限制，一般控制在2M/S，也就是你最多不会超过256KB/S

那么下面就开始进行一些加速的步骤吧：
**步骤一**：进入`C:\Windows\System32` ，找到`cmd.exe` ，并且用管理员的身份运行

![img](C:\Users\13441\Desktop\md\效率\windows突破百度云上传限速.assets\640.jpg)

**步骤二**：输入

```
netsh interface tcp set global autotuning=disabled
```

等待确定的出现后可关闭

![img](C:\Users\13441\Desktop\md\效率\windows突破百度云上传限速.assets\640-1529673495316.jpg)

**步骤三：**
关掉界面后打开运行命令框输入register点击打开注册表编辑

**步骤四：**

进入注册表界面
路径：

```
HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\services\AFD
```

找到文件名为Parameters的文件夹

![img](data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==)

**步骤六：**
然后在右方空白区域
鼠标右键点击新建
选择`DWORD（32）`

![img](data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==)

**步骤七：*

然后将名字命名为`DefaultSendWindow`
修改数值为`1640960`
设置完成重启电脑

![img](C:\Users\13441\Desktop\md\效率\windows突破百度云上传限速.assets\640-1529673500200.jpg)