# virtualbox 创建仅主机网络失败

## Could not find Host Interface Networking driver! Please reinstall

### 1.原因：

lz在更新完了win10的时候，仅主机网络突然不见了。

> - 出现这种情况通常的原因是升级了win10 导致驱动程序丢失。或者删除上一个virtualbox 时把之前卸载虚拟网卡删除了。

## 2.解决步骤

在安装virtualbox目录../Oracle VM VirtualBox中的 drivers\ network\ netadp6目录下有三个文件 

VBoxNetAdp6.cat 
VBoxNetAdp6.inf 
VBoxNetAdp6.sys 

右击VBoxNetAdp6.inf，点安装即可