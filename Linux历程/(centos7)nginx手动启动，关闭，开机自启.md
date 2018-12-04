# Nginx 手动启动，关闭，开机自启

## 手动启动

1. 去到nginx安装目录（解压目录）下，bin/nginx

   ```bash
   nginx -c nginx配置文件地址 
   ```

   例如

```bash
/usr/local/nginx/bin/nginx -c /usr/local/nginx/conf/nginx.conf
```

## 手动重启

```
nginx -s reload
```



## 手动关闭

1. nginx -s stop
2. nginx -s quit

## 开机自启

https://blog.csdn.net/king_kgh/article/details/79576813

## 查看状态

1. ps -e | grep nginx



# 