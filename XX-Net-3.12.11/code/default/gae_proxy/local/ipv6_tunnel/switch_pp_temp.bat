:output
netsh interface ipv6 set prefixpolicy 2001::/32 3 5
netsh interface ipv6 add prefixpolicy 2001::/16 5 11
@call :output>C:\Users\Administrator.DESKTOP-72RDCCB\Downloads\Compressed\XX-Net-3.12.11\data\gae_proxy\ipv6_tunnel.log