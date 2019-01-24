# YLCircleImageView

如果依赖失败，可以直接下载Library中的YLCircleImageView 和 style.xml放入项目中

QQ:375984181

https://juejin.im/post/5c4826446fb9a049ef26ecb4

# 功能

+ 具备描边功能
+ 具备图片和描边间距功能
+ 具备图片四个角，`每个角均可单独设置 X Y 轴方向的半径`

# 代码

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

### 现在最新版本是 v1.0.5，以后可能会更新，所以大家需要看一下 release版本
    
```
    implementation 'com.github.ai2101039:YLCircleImageView:v1.0.5'
```

```
    <com.android.library.YLCircleImageView
        android:layout_width="100dp"
        android:layout_height="100dp"
        app:radius="50dp"/>
```

# 效果图

![GIF](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/12.gif)

![GIF](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/13.gif)

# 其他

```
这种写法表示，左上半径10，其他半径50
        <com.android.library.YLCircleImageView
            android:layout_width="100dp"
            android:layout_height="100dp"
            app:radius="50dp"
            app:topLeftRadius="10dp"/>
```
```
这种写法表示，左上X轴20，左上Y轴10，其他半径50
        <com.android.library.YLCircleImageView
            android:layout_width="100dp"
            android:layout_height="100dp"
            app:radius="50dp"
            app:topLeftRadius="10dp"
            app:topLeftRadius_x="20dp"/>
```

![属性图](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/1.png)
