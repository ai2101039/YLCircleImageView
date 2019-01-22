# YLCircleImageView

如果依赖失败，可以直接下载Library中的YLCircleImageView 和 style.xml放入项目中

QQ:375984181

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

```
    implementation 'com.github.ai2101039:YLCircleImageView:v1.0.4'
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
