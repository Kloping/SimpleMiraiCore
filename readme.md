# 采用Spring式 开发 qq机器人

## 声明:

### 一切开发旨在学习，请勿用于非法用途

- 此框架由GitHhub: [Kloping](https://github.com/Kloping) 开发
- 此框架 是完全免费且开放源代码
- 且 仅用于 学习和娱乐<u><b><i>禁止用于非法用途</i></b></u>

长话短说:

首先 你需要了解过 [Mirai](https://github.com/mamoe/mirai)

其次 选择性阅读 [spt](https://github.com/Kloping/my-spring-tool)

如果 你学过 Spring 那这个框架 就最适合你不过了

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

### 更新 10/15 将 [Mirai-Core](https://github.com/mamoe/mirai/blob/dev/docs/README.md#jvm-%E5%B9%B3%E5%8F%B0-mirai-%E5%BC%80%E5%8F%91) 与 [Mirai-Console](https://github.com/mamoe/mirai/blob/dev/docs/UserManual.md#%E4%BD%BF%E7%94%A8%E7%BA%AF%E6%8E%A7%E5%88%B6%E5%8F%B0%E7%89%88%E6%9C%AC) 融合

#### 在Core运行时 可加载 Consloe 插件

使用方式与Console一致

若之前 拉取过项目 更新下 [pom.xml](https://github.com/Kloping/SimpleMiraiCore/blob/master/pom.xml) 并

创建包 Plugins
创建类  [Plugins.PluginLoader](hhttps://github.com/Kloping/SimpleMiraiCore/tree/master/src/main/core/com/hrs/Kloping/java/Plugins)

复制其内容 至本地

主类添加

    //加载插件
    PluginLoader.load(args);

即可

新增配置文件 conf.properties 避免重复打包

### 配置说明

```text
#QQ账号
qq=0
#QQ密码
pwd=0
#掉线是否重新登录
ReLogin=true
#登录协议
Protocol=ANDROID_PAD
```

### =========

使用说明: [编辑配置文件](https://github.com/Kloping/SimpleMiraiCore/blob/master/conf.properties)

启动程序即可

测试

在群里发送: 你好啊 若回复: 你好,你好 则启动成功

[触发语](https://github.com/Kloping/SimpleMiraiCore/blob/master/conf.properties)

