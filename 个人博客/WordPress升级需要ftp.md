 这应该是新安装完成WordPress踩得第一个坑，But我踩了两次，哈哈，安装完成看到一堆主题版本更新，点击更新，跳出FTP信息，我不用FTP啊，囧，下面就是解决办法。很简单：

不过Wordpress提示升级的时候，点击自动升级都是不能正常升级的，提示要输入FTP账号密码信息，我想应该是要安装FTP吧，SFTP应该不行吧，于是也木有去管这个，每次都是手动升级的。

朋友的阿里云被他折腾了下，可以自动升级了，也木有安装什么FTP，问怎么弄的，也就是瞎折腾了下，然后就好了，就能自动更新了，这个就难过了，于是得想想办法，摸索一下，看来SFTP还是可以的。

据LNMP一键安装包使用者反映wordpress提示升级时，点“自动升级”出现填写FTP信息的页面，像我们这些用LNMP的很多都不安装FTP服务器。其实出现这个的问题就是Nginx的执行身份非文件属主身份。

 解决方法：

假设你的wordpress安装目录为/home/wwwroot/lnmp.org

用Putty登录Linux VPS，执行：

chown -R www /home/wwwroot/lnmp.org

执行上面的命令就可以将/home/wwwroot/lnmp.org下所有文件的属主改为www，这样就可以解决自动更新必须填FTP的问题。

操作到这里，我的还是不行，依旧无法自动升级，

**推荐使用方法：**

还需要修改下Wordpress的配置文件,wp-config.php，加入这么一行：

define('FS_METHOD', "direct");

然后再进入后台，点击升级，发现升级成功了！