# 采用Spring式 开发 qq机器人

## 声明:

### 一切开发旨在学习，请勿用于非法用途

- 此框架由GitHhub: [Kloping](https://github.com/Kloping) 开发
- 此框架 是完全免费且开放源代码
- 且 仅用于 学习和娱乐<u><b><i>禁止用于非法用途</i></b></u>

[无声教程](http://49.232.209.180:20041/intro.mp4)

长话短说:

首先 你需要了解过 [Mirai](https://github.com/mamoe/mirai)

其次 选择性阅读 [文章](https://github.com/Kloping/my-spring-tool)

如果 你学过 Spring 那这个框架 就最适合你不过了

没学过也没关系 阅读一下 [文章](https://github.com/Kloping/my-spring-tool) 基本入门

最后拉取本项目 开始使用

**说明:**<br>

    Controllers/* 控制器 接收 消息 返回处理结果

    BotStater 设置 配置 启动机器人

    services 业务的定义 通常 由Controller 调用 处理
    
    service.impl 业务的实现 

    具体简单见代码


=====================================

###更新 10/15 将 [Mirai-Core](https://github.com/mamoe/mirai/blob/dev/docs/README.md#jvm-%E5%B9%B3%E5%8F%B0-mirai-%E5%BC%80%E5%8F%91) 与 [Mirai-Console](https://github.com/mamoe/mirai/blob/dev/docs/UserManual.md#%E4%BD%BF%E7%94%A8%E7%BA%AF%E6%8E%A7%E5%88%B6%E5%8F%B0%E7%89%88%E6%9C%AC) 融合

####在Core运行时 可加载 Consloe 插件

使用方式与Console一致

若之前 拉取过项目 更新下 [pom.xml](https://github.com/Kloping/SimpleMiraiCore/blob/master/pom.xml) 并 
 
创建包 Plugins 创建类  [Plugins.PluginLoader](https://github.com/Kloping/SimpleMiraiCore/blob/master/pom.xml)

复制其内容 即可


