# Blog

这个项目是关于我的个人博客，我的博客名字是`IL MARE`，之所以取这个名字是因为多年前看过一部韩国爱情片《触不到的恋人》我非常喜欢，里面男女主角居住的屋子就叫IL MARE。这个名字是意大利语中`海`的意思，我个人也非常喜欢大海，因此就把我的个人博客命名为`IL MARE`。我博客的地址是[IL MARE](http://www.ilmareblog.com/blog)。我主要在博客上发表一些技术类的文章，偶尔也会发一篇自己的心情。博客还有“状态”功能，能够帮助我记录自己实时的状态。

## 动机

做这个博客第一是因为自己曾经是一个JavaWeb的爱好者，学习完相关的知识点之后一直想着能够做一些实际的应用。第二是因为某个巧合看到了github上一个大神“冬瓜”的个人博客[Desgard](http://www.desgard.com)。这两个原因的综合作用下就有了这个web2.0时代的产物。

## 版本控制

目前`IL MARE`已经在1.1的版本上运行了一年之久，项目最开始发布的版本是1.0。后来为了移动端的接入在1.0版本上集成了一部分网络接口，迭代出了目前这个1.1版本。今年笔者计划继续开发出2.0版本，新的版本计划更新UI设计，新增加评论功能。

## 界面

我的博客主要分为前端展示部分和后端管理两部分。前端展示负责向用户展示博客的内容，后端管理部分负责管理博客的文章，状态和个人信息等。博客的前端的界面分为四个主要部分：

* 首页摘要信息界面：负责为读者综合展示博主的个人信息，头像，状态和文章发布情况

* 文章列表界面：主要负责为读者展示笔者所发布的文章。

* 状态展示界面：主要为读者集中展示博主所发表过的所有状态信息。

* About Me：为读者展示博主的声明。

首页摘要信息界面

![index](https://github.com/yhswjtuILMARE/Blog/blob/master/image/index.jpg)

文章列表界面：

![blog](https://github.com/yhswjtuILMARE/Blog/blob/master/image/blog.jpg)

状态展示界面：

![status](https://github.com/yhswjtuILMARE/Blog/blob/master/image/status.jpg)

About Me界面：

![About Me](https://github.com/yhswjtuILMARE/Blog/blob/master/image/about.jpg)

后端管理界面：

![manage](https://github.com/yhswjtuILMARE/Blog/blob/master/image/manage_1.jpg)

以上是PC端的界面设计，在手机wap端ILMARE也做了相应的适配，在这里就只展示wap首页：

![wapindex](https://github.com/yhswjtuILMARE/Blog/blob/master/image/wap.png)

## 软件和硬件

这个博客是基于腾讯的云服务器，每个月10块钱租了一台云主机。云主机的性能平平，内存只有1G，硬盘只有40G。不久前在putty上查看了一下，仅仅运行基本的服务器软件和数据库就消耗了七百余兆内存，很难说在大并发条件下会不会出现拒绝服务的情况。

在软件方面，在云主机操作系统上我选择的是性能稳定的Linux的一个很流行的版本CentOS。之所以用这个版本主要是因为这个版本之前上课的时候用过，各方面都比较熟悉。在服务器软件方面，我选择的是apache研发的tomcat。数据库方面，选择的是mysql数据库。总的来说这样的硬件和软件配置不是很高，但是我想对于一个博客网站来说还是够用的。
