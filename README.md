|知识点|内容|
|-----|----|
|DrawerLayout|侧滑|
|toolbar|toolbar的按键设置(返回键,菜单),继承和复用|
|底部导航视图+fragment|自己实现底部导航视图|
|baseActivity|要有那些功能|
|关于drawable和drawable-v24|出现的错误  Binary XML file line #0: Error inflating class android.support.design.widget.BottomNavigationView|
|BottomNavigationView|在使用这个控件的时候,一般用到矢量图,需要添加  ` implementation 'com.android.support:design:26.1.0' `和     `implementation 'com.android.support:support-vector-drawable:26.1.0' `
和     最后这个在defaultconfigure中 `vectorDrawables.useSupportLibrary= true`|
|fragment使用|使用的相关问题|



### toolbar
- 定义单独的toolbar_layout实现复用效果
- 在styles文件中去除actionbar,去除actionbar的几种方法
- 几个主要属性app:title,subtitle,logo,navigationIcon,通过向toolbar布局中添加textview实现标题在中间的效果

### drawable 和 drawable-v24
- 不知道drawable-v24是什么作用,存疑,没有解决
- 图片和布局应该放在drawable中,如果只放在drawable-v24 ,那么xml中应用相关图片和布局的空间不能被实现,报错如下:

```
        Caused by: android.view.InflateException: Binary XML file line #0: Error inflating class android.support.design.widget.BottomNavigationView
```
  
- 上面这个报错的特点是 Binary XML file line #0,后面的控件BottomNavigationView找不到相关对象
