# Git知识分享

## git init

创建git仓库，本地新项目一般可能会用到
建议在有gitlab/github的情况下，先去把项目建了，填好readme再拉下来

## git config

查看或修改git配置项，包括但不局限于仓库地址和用户信息，可控制是否全局生效
或者你可以手动去改.git目录下的文件

## git clone

理解成下载项目就行了

## git pull，git fetch

前者下载远程代码并合并，后者只下载不合并

## git commit

提交到本地仓库
慎用git commit -a

## git push

推送到远程仓库

## git branch

查看分支

## git checkout

切换分支

## git merge

分支合并，经常伴随着代码冲突

## git cherry-pick

git cherry-pick \<commitHash>
一般用于将一个分支的部分提交应用于另一个分支

## git remote

git remote update origin 常用于同步远程分支、tag变化

## git reset

git reset \<commitHash>
回退到指定版本

## git revert

冲抵某次提交

## branch、tag及开发流程

tag常用于保存某个稳定版本，供长期使用，有的时候可以理解为一个不再有commit的分支
多分支对于一个项目来说是常见的，不同团队对于分支的管理也是不一样的。总的来说，按上线流程可以分为开发、测试、生产分支。按开发周期有长期分支短期分支，这两个分支在一个较长的周期内应该是并行的，当全部开发任务结束后通常会进行合并。同时也存在版本分支划分，每个版本分支都会有一个较长的维护时间（多半是改bug）

## 建议

建议使用工具进行git操作，简单，而且当需要对比或跟踪的时候效果良好。
不必为难自己。。。
