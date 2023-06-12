# 采用Spring式 开发 qq机器人

## 声明:

### 一切开发旨在学习，请勿用于非法用途

- 此框架由GitHhub: [Kloping](https://github.com/Kloping) 开发
- 此框架 是完全免费且开放源代码
- 且 仅用于 学习和娱乐<u><b><i>禁止用于非法用途</i></b></u>

长话短说:

首先 你需要了解过 [Mirai](https://github.com/mamoe/mirai)

其次 选择性阅读 [spt](https://github.com/Kloping/my-spring-tool)

如果 你学过 spring 则这个框架 就最适合你不过了

没学过也没关系 阅读一下 [spt](https://github.com/Kloping/my-spring-tool) 基本入门

最后拉取本项目 开始使用

**说明:**<br>

    Controllers/* 控制器 接收 消息 返回处理结果

    BotStater 设置 配置 启动机器人

    services 业务的定义 通常 由Controller 调用 处理
    
    service.impl 业务的实现 

    具体简单见代码

tips: 程序打jar包时 若确定 打包无误且仍无法加载主类<br>
可用压缩工具 打开jar包 将 META-INF\下 BCxxxx.DSA .DF 类似的文件删除 即可正常运行

<hr> 

- 项目通过内置启动 Mirai Console Terminal 的方式 来并登录启动bot
- 通过 GlobalEventChannel INSTANCE 来注册基本业务处理代码

<hr>

启动后 一般情况下 与mcl登录方法一致

登录成功即可测试

测试

在群里发送: 你好啊 若回复: 你好,你好

则启动成功
