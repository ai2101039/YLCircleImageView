# YLCircleImageView

[![](https://www.jitpack.io/v/ai2101039/YLCircleImageView.svg)](https://www.jitpack.io/#ai2101039/YLCircleImageView)

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

### 现在最新版本是 v1.0.7，解决了AndroidX的属性检查问题
    
```
    implementation 'com.github.ai2101039:YLCircleImageView:v1.0.7'
```

```
    <com.android.library.YLCircleImageView
        android:layout_width="100dp"
        android:layout_height="100dp"
        app:radiusYL="50dp"/>
```

### 在v1.0.6中增加XML设置

增加了对Circle属性变量的设置，原本想做成 build模式，后来想了想没有必要。

大家记得如果你要绘制圆角，一定要 circle 设置为 true。 

```
                //  设置图片格式
                ylCircleImageView.setStyleType(YLCircleImageView.TOP);
                //  设置圆角，这里可设置八个角度大小
                ylCircleImageView.setRadius(20F);
                //  设置描边宽度
                ylCircleImageView.setBorderWidth(10F);
                //  设置描边颜色
                ylCircleImageView.setBorderColor(Color.WHITE);
                //  设置描边与图片的间距
                ylCircleImageView.setBorderSpace(5F);
                //  重要：设置circle为真，切记一定要带着这一条
                ylCircleImageView.setCircle(true);
                //  重绘
                ylCircleImageView.invalidate();
                
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
            app:radiusYL="50dp"
            app:topLeftRadiusYL="10dp"/>
```
```
这种写法表示，左上X轴20，左上Y轴10，其他半径50
        <com.android.library.YLCircleImageView
            android:layout_width="100dp"
            android:layout_height="100dp"
            app:radiusYL="50dp"
            app:topLeftRadiusYL="10dp"
            app:topLeftRadius_xYL="20dp"/>
```

![属性图](https://github.com/ai2101039/YLCircleImageView/blob/master/screenshots/1.png)
