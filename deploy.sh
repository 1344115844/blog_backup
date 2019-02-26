time=$(date "+%Y-%m-%d %H:%M:%S")
git config core.autocrlf false
git pull origin master 
git add . -A
git commit -m "deploy at ${time}"
git push origin master 
