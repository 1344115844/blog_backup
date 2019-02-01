time=$(date "+%Y-%m-%d %H:%M:%S")
git add .
git commit -m  "auto deploy ${time}" 
git push origin master
