# YLCircleImageView
圆角
如果依赖失败，可以直接下载Library中的YLCircleImageView 和 style.xml放入项目中

[视频](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/SVID_20190122_155900_1.mp4)

# 功能
---
+ 具备描边功能
+ 具备图片和描边间距功能
+ 具备图片

# 代码
---

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
GIF
![GIF](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/15.gif)
![普通圆形](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/1.jpg)
![描边圆形](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/2.jpg)











