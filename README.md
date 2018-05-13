## Dam —— 简易http服务器

Dam是一款轻量级的基于jvm平台的简易http服务器，采用Java为开发语言，多路复用IO进行通信，支持对静态资源的处理，简单动态脚本的解析。

项目使用**Github**进行版本控制，使用**Maven**进行构建

## 快速开始

从**Github**下载项目源码，使用**Maven**进行打包编译

    mvn clean package
    
打包之后的项目目录结构如下：

* `\bin`—— 内包含windows平台启动批处理文件run.bat或linux平台启动shell脚本run.sh
* `\config`—— 基本配置文件
* `\lib`—— 服务器运行jar包
* `\logs`—— 日志文件
* `\temp`—— 临时文件
* `\www`——web apps 目录文件
*  \README.md

根据运行平台选择**bin**目录下的**run.bat**或**run.sh**文件运行服务器。
打开浏览器运行<http://localhost:8080>可看到项目主页

## 使用Dam进行webapp开发
    web应用开发需使用Java语言，采用注解的方式，0配置即可开发web应用，简单，快捷，高效。

首先新建一个**Maven**项目，编辑`pom.xml`添加依赖

    <dependencies>
        <dependency>
            <groupId>com.dam</groupId>
            <artifactId>dam-annotation</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

或者自行添加jar包依赖**`dam-annotation-1.0-SNAPSHOT.`**

创建控制器开处理http请求:

    package com.dam.controller;
import com.dam.model.User;
import com.dam.service.UserService;
import org.dam.annotation.Parameter;
import org.dam.annotation.Request;
import org.dam.annotation.Toylet;
import org.dam.http.HttpResponse;
import org.dam.http.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    /**
    * Created by geeche on 2018/5/7.
    */
    @Toylet
    public class Index {

        private UserService userService = new UserService();

        @Request(url = "/index",method = "GET",resultType = "page")
        public String index(){
            return "html/index.html";
        }

        @Request(url = "/get",method = "GET",resultType = "dynamic")
        public String get(@Parameter(paramName = "uname")String userName,
                        @Parameter(paramName = "pwd")String password, HttpResponse response){
            List<String> list = new ArrayList<String>();
            list.add("listTest");
            response.addAttribute("list",list);
            User user = userService.getUserByUserName("objTest");
            response.addAttribute("user",user);
            response.addAttribute("var","varTest");
            return "tsps/Welcome.tsp";
        }

        @Request(url = "/redirect",method = "POST",resultType = "data")
        public String post(@Parameter(paramName = "uname")String userName,
                        @Parameter(paramName = "password")String password){
            User user = userService.getUserByUserName(userName);
            return user.toString();
        }

        @Request(url = "/getRed",method = "GET",resultType = "redirect")
        public String redirect(){
            return "/index";
        }

    }

注解说明：

 * **`@Toylet`** —— **类注解**,声明该类为一个可以处理http请求的控制器
 *  **`@Request`** —— **方法注解**,声明该方法所对应的http请求，其中参数**url**为对应的请求url，**method**为请求类型**GET**，**POST**等，**resultType**为返回类型，即(**page**-静态页面；**dynamic**-动态脚本；**redirect**-重定向)
 *  **`@Parameter`** —— 与请求传入字段的name一一对应，其中**defaultValue**为默认值


## 状态

Dam版本为v1.0.0，还缺少对**https**的支持，对**websocket**的支持，以及在安全，性能方面都需要完善。欢迎给我提issues或者创建gruop一起完善它。

## 项目结构

Dam源码目前的模块结构如下：

* **`/dam-annotation`**:包含了开发web app所需要的所有注解
*  **`/dam-bridge`**:将web app执行逻辑与服务器执行逻辑链接
*  **`/dam-dynamic-syntax`**:对简单动态脚本进行解析
*  **`/dam-exception`**:所有自定义异常
*  **`/dam-http`**:http相关，对http协议请求，响应类，以及http协议信息规定，参考RFC2616
*  **`/dam-io`**:多路复用io处理tcp请求，并将数据写回
*  **`/dam-server`**:接收io模块数据以及对数据进行处理和handler链，再将响应数据返回io模块
*  **`/dam-start`**:启动模块
*  **`/dam-util`**:工具模块

## 功能
Core

*   支持对静态文件的处理
*   支持缓存，压缩，重定向等基本http规范
*   支持cookie，seesion
*   支持对简单动态脚本的解析

##下载

 **Github：<https://github.com/dntbeSris/dam>**
    
