|知识点|内容|
|-----|----|
|DrawerLayout|侧滑|
|toolbar|toolbar的按键设置(返回键,菜单),继承和复用,toolbar的navigation导航按钮设置位置|
|底部导航视图+fragment|自己实现底部导航视图|
|baseActivity|要有那些功能|
|关于drawable和drawable-v24|出现的错误  Binary XML file line #0: Error inflating class android.support.design.widget.BottomNavigationView|
|BottomNavigationView|在使用这个控件的时候,一般用到矢量图,需要添加  ` implementation 'com.android.support:design:26.1.0' `和     `implementation 'com.android.support:support-vector-drawable:26.1.0' `和     最后这个在defaultconfigure中 `vectorDrawables.useSupportLibrary= true`|
|fragment使用|使用的相关问题|
|recycleview使用|对item整体的监听|
|textview添加省略|` android:maxLines="1"  android:ellipsize="end"  android:singleLine="true"`|
|**避免在include中添加id,否则layout中的id不能直接findviewbyid**在包含id的include xml代码块中寻找被包含layout的中控件id|因为include的xml代码块中包含id,不能直接用findbyid(R.id.toolbar),需要inflate一个layout,例如,` View layout = getLayoutInflater().inflate(R.layout.toolbar_layout,null);toolbar = layout.findViewById(R.id.toolbar);`|
|在baseActivity中初始化控件是一个错误的做法,尤其是需要在onCreate中对控件进行操作时|因为要findViewById操作,所以需要子类{@link #setContentView(View)}方法执行之后才能初始化toolbar,所以不能在baseActivity的{@link #onCreate(Bundle)} 方法中执行下面这个方法|
|ConstraintLayout|布局倾向于top,bottom,start,end|
|**mysql**数据库安装|三种安装方式(1)apt-get 安装<https://dev.mysql.com/doc/mysql-apt-repo-quick-guide/en/>(2)下载对应的deb安装包,例如mysql-server_8.0.11-1ubuntu16.04_amd64.deb-bundle,参考网址(3)从二进制源码包安装,tar包或者tar.gz包,本次采用第一种方式|
|okhttp使用总结|http中的header学习**各种header,及其意义**,formbody,requestBody,post,get几种实现方式|
|编码问题|汉字在网络传输容易出现编码问题,对文字采用URIEncoding.encode(name,"utf-8")的方式编码,解码URIEncoding.decode(name,"utf-8"),通过response.setCharacterEncoding("utf-8")设置返回的response的编码格式是很有必要的,不同的编码格式会影响返回数据的解码.当输入的编码格式字符串不正确时,会发生io.UnsupportedEncodingException|
|UncaughtExceptionHandler,处理为捕获异常|在application中实现thread中的这个接口,重写方法|
|Md5|md5中每一步骤的意义|
|jdbc驱动找不到|web工程中,jar包要放在根目录webContent的lib包中,[参考](https://blog.csdn.net/benjaminlee1/article/details/62216028)|
|mysql编码问题|设置数据库默认的编码吗格式为utf-8,[参考](https://blog.csdn.net/liunian_siyu/article/details/53605802),default-character-set已经过时,可以不设置[官网](https://dev.mysql.com/doc/refman/8.0/en/charset-applications.html),[参考](https://blog.csdn.net/zhuoxiong/article/details/7453773),最后要`service mysql restart`重启一下,对于已经建立的数据库可以通过如[官网中命令行操作](https://dev.mysql.com/doc/refman/8.0/en/charset-database.html),改为utf-8,已经存在的数据乱码不会被修正,乱码数据应该清除操作|
|okhttp3中cookie管理和使用|接收和发送重写两个即可[参考](https://blog.csdn.net/chen19960724/article/details/52355820)|
|singleTask,singleTop启动模式下,对生命周期,和任务栈的影响|不会调用onCreate方法,会调用onNewIntent方法|
|聊天界面中,右侧布局使用relativeLayout,使用LinearLayout需要使用gravity = right ,然后会出现奇怪的错误,textview过长会溢出左侧边界,溢出长度为textview右侧控件的长度,不知道原因||

### toolbar
- 定义单独的toolbar_layout实现复用效果
- 在styles文件中去除actionbar,去除actionbar的几种方法
- 几个主要属性app:title,subtitle,logo,navigationIcon,通过向toolbar布局中添加textview实现标题在中间的效果
- Toolbar setNavigationIcon无效原因：  1.设置在setSupportActionBar( mToolbar );之前无效；  2.设置在DrawerLayout.setDrawerListener(ActionBarDrawerToggle);之前也无效；
- 下面这几行代码的作用,代码测试
 ```
     //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
     //        getSupportActionBar().setHomeButtonEnabled(true);
     //        getSupportActionBar().setDisplayShowHomeEnabled(true);
   ```
### drawable 和 drawable-v24
- 不知道drawable-v24是什么作用,存疑,没有解决
- 图片和布局应该放在drawable中,如果只放在drawable-v24 ,那么xml中应用相关图片和布局的空间不能被实现,报错如下:

```
        Caused by: android.view.InflateException: Binary XML file line #0: Error inflating class android.support.design.widget.BottomNavigationView
```

- 上面这个报错的特点是 Binary XML file line #0,后面的控件BottomNavigationView找不到相关对象

### fragment
- 直接的使用碎片,例如在<fragment>标签中使用name制定类,使用layout制定布局,layout属性不必须,因为那么制定的类就已经会实现布局
- 定义一个framelayout容器,动态添加fragment布局,replace方法,还有add,remove方法,涉及tag标签
- fragment 中使用recycleview,传递参数

### recycleview
- 对item整体监听,有两种考虑   (1) 对整个item的layout布局设置监听 (2) 在adapter中添加一个接口,包含onClick(int position)和onItemClick两个接口方法

### include layout
- 写代码测试include中包含layout,在寻找layout中的id的方法,**代码测试**

### mysql安装
- apt-get安装方式,sudo apt-get install mysql-server mysql-client
    - 数据库目录：/var/lib/mysql/

    - 配置文件：/usr/share/mysql（命令及配置文件） ，/etc/mysql（如：my.cnf）

    - 相关命令：/usr/bin(mysqladmin mysqldump等命令) 和/usr/sbin

    - 启动脚本：/etc/init.d/mysql（启动脚本文件mysql的目录）
- mysql的启动,停止,**常用的操作**
- Java链接mysql数据库[mysql官方Java驱动](https://dev.mysql.com/downloads/connector/j/),Java操作数据库测试,可以看如下[链接](http://www.runoob.com/java/java-mysql-connect.html)

### okhttp
- 传递数据的方式 header,cookie,formbody,requestBody
- header 可以传递键值对数据,通常具有通用的的键值对形式,也可以自定义,常用的例如:content-type,set-cookie.相应中可以getheader获取传递的数据,*需要学习header设计目的,等待查阅,不应该是传递数据的*
- cookie 浏览器中的常用格式,传递键值对,okhttp具有自动管理,不过domain,path等属性不清楚用途,对于cookie的正确用法存疑
- formbody 在http请求中传递键值对数据,也可以叫做表单,是requestBody的子类
- requestBody 传递所有的数据





### 用户数据库设计
#### user_table 服务器 用户信息表
- 用户注册,插入个人信息,
- 用户登录,检测信息是否匹配
- token 存储在本地本身具有危险性

|id|username|userId|password|token|imagePath
|--|--|--|--|--|--|
|自增|用户本身名字|hash或者md5|密码,md5|验证登录,服务器session生成|用户头像|

#### friend_table 用户好友关系表,用户添加好友的行为,删除好友的行为

|id|userId|friendId|
|--|--|--|
|自增|用户id|好友id|

#### message_table 消息表

|id|userId|friendId|message|MsgDate|MsgTime|
|--|--|--|--|--|--|
|id|用户id|朋友id|消息|日期|时间点|

#### add_friend_table 请求消息列表,检查是否已经是好友

|id|userId|friendId|
|---|--|---|
|id|用户id|朋友id|

### 长连接方案
- 长连接+心跳
- http 采用keep alive 实现长连接
- websocket 实现长连接
- mqtt实现长连接
- socket实现长连接 + 心跳包
- 这里采用socket实现长连接,仅仅因为socket最熟悉.
#### 长连接的具体实现
 1.每次开启app,建立一个和服务器的socket连接,建立socket连接后.并且长期持有输入流,输出流,到关闭app的时候关闭输入输出流和socket连接
 2.客户端通过循环等待read输入流的方式,等待消息到来
 3. setSoTimeout    `public void setSoTimeout(int timeout)`    `throws SocketException` [详见](http://cuisuqiang.iteye.com/blog/1725348)
    - 启用/禁用带有指定超时值的 SO_TIMEOUT，以毫秒为单位。将此选项设为非零的超时值时，在与此 Socket 关联的 InputStream 上调用 read() 将只阻塞此时间长度。  
    - 如果超过超时值，将引发 java.net.SocketTimeoutException，虽然 Socket 仍旧有效。选项必须在进入阻塞操作前被启用才能生效。  
 4. 长连接,要考虑服务端的退出机制,因为客户端掉线后,服务端socket连接可能在阻塞等待,不会直接主动关闭.
    - 如果客户端网络正常,退出时应该通知服务端关闭处理线程,或者通过上线下线标志关闭线程
    - 如果客户端不正常,比如突然断网,没有机会向服务端发送数据,服务端应该有主动关闭机制,可以超过一定时长关闭,也可以经过网络探测关闭.
 5. 建立socket连接后,客户端通知服务器用户上线(发送一个标志),同时服务器初始化,输入流阻塞等待,输出流检查数据库返回数据.

### app 的缺点,要考虑的问题
- token 存储在本地本身具有危险性,token本身安全问题
- 没有监听网络状态,无网络情况完全没有考虑,网络模块是基础模块.任何网络操作都要考虑没有网络状态,怎么应对,在使用网络功能前,检查网络状态是必必须的.
- 添加好友,直接双向好友通过
- 数据库选择不合适,关系型数据库不适合即时通讯app
- 对于所有的请求没有验证token,对于非法请求不能识别,存在安全隐患.
- 文字传送时,用了byte数组,数组长度是2048,限制了文字的长度,文字长度限制在1024个,字母2048个.
- 聊天界面,消息发送日期时间的变化没有实现

### app设计点
- 服务器不存储密码,md5加密
- token:服务器应该返回token和时间戳,时间戳用来设置token过期时间,本地保存token加密方式,token加密保存
- 什么时候检查用户身份,启动时候不能立刻检查用户身份,因为如果用户网络不存在,则无法启动,而且不能不让用户在没有网络时不能使用app.又网络和没有网络的情况应该进行考虑.没有网络应该禁止用户的一切网络操作
- 网络状态监听,是app的基础模块,应用应该实时进行网络监听.

#### 注册过程设计,注意点
 1. 密码存储,服务器不存储密码,不明文存储密码,存储md5值
 2. 网络连接失败,提示用户操作
 3. 服务器返回token,可以通过cookie,还可通过别的方式
 4. cookie 管理,服务器返回cookie,本地保存cookie
 5. 注册后返回登录界面,登录界面launchMode,singleTask,onNewIntent方法
 6. 数据库查询是否已经存在用户,检查用户名,密码格式是否符合规则
 7. 服务器返回结果类型:(1)用户名已经存在 (2)注册成功 (3)注册失败,服务器错误
 
#### 登录过程设计
 1. 每次登录采用密码和用户名登录验证身份,更新服务器token及token时间戳
 2. 检查用户名合法性
 3. 服务器返回结果类型:(1)账号密码错误 (2)登录成功 (3)服务器错误
 4. 网络问题
 5. 数据库中查询用户是否存在,密码是否正确
 6. 登录后,初始化用户全局信息
 7. 登录后,把用户的必要信息保存在本地,持久化保存
 8. 有登陆操作,就要有退出登录的操作,二者应该同事设计
 
#### 添加好友
 1. 检查已经注册的用户中是否存在好友
 2. 检查是否已经是好友关系,不能重复注册
 3. 本地数据库存储好友信息

#### 发送消息
 1. 一个消息体要包含那些信息,朋友姓名,id,自己姓名,id,消息,日期这些那些是必要的,那些是不必要的.
 2. 消息的本地存储
 3. 用户是否在线状态是一个重要的标志,表明用户能否接收消息
 4. 用户打开app后,需要向服务器发送一个空消息,与服务器建立长连接.
 5. 消息的时间考虑,应该用服务端接收的到时间,还是客户端发送的时间
 
### 网络接口
 |操作|request|response|备注|
 |--|--|--|--|
 |注册|username,password|token|token在cookie中返回|
 |登录|username,password|token,loginResult:1,loginResult:0|登录状态是返回json字符串|
 |添加好友|userId,friendId|hasFriend:true or false,isMyFriend : true or false,isAddSuccess :true or false|hasFriend 是否存在好友名字,isMyFriend是否已经是我的好友,isAddSuccess好友是否添加成功|
 |检查身份,身份认证|token|isLogin:true,false|
 |发送消息|userId,friendId,msg,date,time|||
 |接收消息|friendId,msg,date,time|
 |客户端上线,通知服务端初始化接口|friendId = 0,msg = 0,userId||用户的在线状态应该存储到数据库|
 |客户端下线,通知服务端关闭接口,停止线程|friendId = 1,msg = 0,userId||用户的在线状态应该存储到数据库|