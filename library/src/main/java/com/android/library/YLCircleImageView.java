package com.android.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author 高延荣
 * @date 2019/1/22 14:54
 * 描述:
 */
public class YLCircleImageView extends android.support.v7.widget.AppCompatImageView {
    private Context context;

    /**
     * 用于绘制Layer
     */
    private Paint paint;
    /**
     * 用于绘制描边
     */
    private Paint borderPaint;

    /**
     * 半径
     */
    private float radius;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;

    /**
     * 不常用
     */
    private float topLeftRadius_x;
    private float topLeftRadius_y;
    private float topRightRadius_x;
    private float topRightRadius_y;
    private float bottomLeftRadius_x;
    private float bottomLeftRadius_y;
    private float bottomRightRadius_x;
    private float bottomRightRadius_y;

    /**
     * 描边
     */
    private float borderWidth;
    private float borderSpace;
    private int borderColor;
    /**
     * 是否有弧度，即是否需要绘制圆弧
     */
    private boolean circle;

    /**
     * 图片展示方式
     * 0 -- 图片顶部开始展示，铺满，如果Y轴铺满时，X轴大，则图片水平居中
     * 1 -- 图片中心点与指定区域中心重合
     * 2 -- 图片底部开始展示，铺满，如果Y轴铺满时，X轴大，则图片水平居中
     * 3 -- 图片完全展示
     */
    public static final int TOP = 0;
    public static final int CENTER = 1;
    public static final int BOTTOM = 2;
    public static final int FITXY = 3;

    private int styleType;


    public YLCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.YLCircleImageView);
        //  半径
        radius = ta.getDimension(R.styleable.YLCircleImageView_radiusYL, 0);
        topLeftRadius = ta.getDimension(R.styleable.YLCircleImageView_topLeftRadiusYL, 0);
        topRightRadius = ta.getDimension(R.styleable.YLCircleImageView_topRightRadiusYL, 0);
        bottomLeftRadius = ta.getDimension(R.styleable.YLCircleImageView_bottomLeftRadiusYL, 0);
        bottomRightRadius = ta.getDimension(R.styleable.YLCircleImageView_bottomRightRadiusYL, 0);
        //  展示类型
        styleType = ta.getInt(R.styleable.YLCircleImageView_scaleTypeYL, TOP);
        //  描边
        borderWidth = ta.getDimension(R.styleable.YLCircleImageView_borderWidthYL, 0);
        borderSpace = ta.getDimension(R.styleable.YLCircleImageView_borderSpaceYL, 0);
        borderColor = ta.getColor(R.styleable.YLCircleImageView_borderColorYL, Color.WHITE);

        //  不常用属性
        topLeftRadius_x = ta.getDimension(R.styleable.YLCircleImageView_topLeftRadius_xYL, 0);
        topLeftRadius_y = ta.getDimension(R.styleable.YLCircleImageView_topLeftRadius_yYL, 0);
        topRightRadius_x = ta.getDimension(R.styleable.YLCircleImageView_topRightRadius_xYL, 0);
        topRightRadius_y = ta.getDimension(R.styleable.YLCircleImageView_topRightRadius_yYL, 0);
        bottomLeftRadius_x = ta.getDimension(R.styleable.YLCircleImageView_bottomLeftRadius_xYL, 0);
        bottomLeftRadius_y = ta.getDimension(R.styleable.YLCircleImageView_bottomLeftRadius_yYL, 0);
        bottomRightRadius_x = ta.getDimension(R.styleable.YLCircleImageView_bottomRightRadius_xYL, 0);
        bottomRightRadius_y = ta.getDimension(R.styleable.YLCircleImageView_bottomRightRadius_yYL, 0);

        ta.recycle();

        initData();
    }

    public void initData() {
        initRadius();
        //  判断是否需要调用绘制函数
        circle = borderWidth != 0 || borderSpace != 0 ||
                topLeftRadius_x != 0 || topLeftRadius_y != 0 ||
                topRightRadius_x != 0 || topRightRadius_y != 0 ||
                bottomLeftRadius_x != 0 || bottomLeftRadius_y != 0 ||
                bottomRightRadius_x != 0 || bottomRightRadius_y != 0;

        //  设置画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);

        if (circle) {
            //  为什么设置这一条，因为Glide中，在into 源码内
            //  不同的 ScaleType 会对drawable进行压缩，一旦压缩了，我们在onDraw里面获取图片的大小就没有意义了
            setScaleType(ScaleType.MATRIX);
        }
    }

    /**
     * 初始化半径
     */
    private void initRadius() {
        //  该处便于代码编写 如XML设置 radius = 20，topLeftRadius = 10，最终结果是  10 20 20 20
        if (radius != 0) {
            topLeftRadius = topLeftRadius == 0 ? radius : topLeftRadius;
            topRightRadius = topRightRadius == 0 ? radius : topRightRadius;
            bottomLeftRadius = bottomLeftRadius == 0 ? radius : bottomLeftRadius;
            bottomRightRadius = bottomRightRadius == 0 ? radius : bottomRightRadius;
        }
        //  如果设置了 radius = 20，topLeftRadius = 10，topLeftRadius_x = 30,
        //  最终结果，topLeftRadius_x = 30，topLeftRadius_y = 10，其余 20
        topLeftRadius_x = topLeftRadius_x == 0 ? topLeftRadius : topLeftRadius_x;
        topLeftRadius_y = topLeftRadius_y == 0 ? topLeftRadius : topLeftRadius_y;

        topRightRadius_x = topRightRadius_x == 0 ? topRightRadius : topRightRadius_x;
        topRightRadius_y = topRightRadius_y == 0 ? topRightRadius : topRightRadius_y;

        bottomLeftRadius_x = bottomLeftRadius_x == 0 ? bottomLeftRadius : bottomLeftRadius_x;
        bottomLeftRadius_y = bottomLeftRadius_y == 0 ? bottomLeftRadius : bottomLeftRadius_y;

        bottomRightRadius_x = bottomRightRadius_x == 0 ? bottomRightRadius : bottomRightRadius_x;
        bottomRightRadius_y = bottomRightRadius_y == 0 ? bottomRightRadius : bottomRightRadius_y;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        //  使用局部变量，降低函数调用次数
        int vw = getMeasuredWidth();
        int vh = getMeasuredHeight();

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //  绘制描边
        if (borderWidth != 0) {
            RectF rectF = new RectF(paddingLeft, paddingTop, vw - paddingRight, vh - paddingBottom);
            //  描边会有一半处于框体之外
            float i = borderWidth / 2;
            //  移动矩形，以便于描边都处于view内
            rectF.inset(i, i);
            //  绘制描边，半径需要进行偏移 i
            drawPath(canvas, rectF, borderPaint, i);

        }

        if (drawableDisable(drawable) && circle) {
            RectF rectF = new RectF(paddingLeft, paddingTop, vw - paddingRight, vh - paddingBottom);
            //  矩形需要缩小的值
            float i = borderWidth + borderSpace;
            //  这里解释一下，为什么要减去一个像素，因为像素融合时，由于锯齿的存在和图片像素不高，会导致图片和边框出现1像素的间隙
            //  大家可以试一下，去掉这一句，然后用高清图就不会出问题，用非高清图就会出现
            i = i > 1 ? i - 1 : 0;
            //  矩形偏移
            rectF.inset(i, i);
            int layerId = canvas.saveLayer(rectF, null, Canvas.ALL_SAVE_FLAG);
            //  多边形
            drawPath(canvas, rectF, paint, i);
            //  设置像素融合模式
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            //  drawable转为 bitmap
            Bitmap bitmap = drawableToBitmap(drawable);
            //  根据图片的大小，控件的大小，图片的展示形式，然后来计算图片的src取值范围
            Rect src = getSrc(bitmap, (int) rectF.width(), (int) rectF.height());
            //  dst取整个控件，也就是表示，我们的图片要占满整个控件
            canvas.drawBitmap(bitmap, src, rectF, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(layerId);
        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 这里详细说一下，我们的目标就是在 bitmap 中找到一个 和 view 宽高比例相等的 一块矩形 tempRect，然后截取出来 放到整个view中
     * tempRect 总是会存在
     *
     * @param bitmap bitmap
     * @param rw     绘制区域的宽度
     * @param rh     绘制区域的高度
     * @return 矩形
     */
    private Rect getSrc(@NonNull Bitmap bitmap, int rw, int rh) {
        //  bw bh,bitmap 的宽高
        //  vw vh,view 的宽高
        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();

        int left = 0, top = 0, right = 0, bottom = 0;

        //  判断 bw/bh 与 rw/rh
        int temp1 = bw * rh;
        int temp2 = rw * bh;

        //  相似矩形的宽高
        int[] tempRect = {bw, bh};

        if (temp1 == temp2) {
            return new Rect(0, 0, bw, bh);
        }
        //  tempRect 的宽度比 bw 小
        else if (temp1 > temp2) {
            int tempBw = temp2 / rh;
            tempRect[0] = tempBw;
        }
        //  tempRect 的宽度比 bw 大
        else if (temp1 < temp2) {
            int tempBh = temp1 / rw;
            tempRect[1] = tempBh;
        }

        //  tempRect 的宽度与 bw 的比值
        Boolean compare = bw > tempRect[0];

        switch (styleType) {
            case TOP:
                //  从上往下展示，我们这里的效果是不止从上往下，compare = true，还要居中
                left = compare ? (bw - tempRect[0]) / 2 : 0;
                top = 0;
                right = compare ? (bw + tempRect[0]) / 2 : tempRect[0];
                bottom = tempRect[1];
                break;
            case CENTER:
                //  居中
                left = compare ? (bw - tempRect[0]) / 2 : 0;
                top = compare ? 0 : (bh - tempRect[1]) / 2;
                right = compare ? (bw + tempRect[0]) / 2 : tempRect[0];
                bottom = compare ? tempRect[1] : (bh + tempRect[1]) / 2;
                break;
            case BOTTOM:
                left = compare ? (bw - tempRect[0]) / 2 : 0;
                top = compare ? 0 : bh - tempRect[1];
                right = compare ? (bw + tempRect[0]) / 2 : tempRect[0];
                bottom = compare ? tempRect[1] : bh;
                break;
            case FITXY:
                left = 0;
                top = 0;
                right = bw;
                bottom = bh;
                break;
            default:
        }

        return new Rect(left, top, right, bottom);
    }

    /**
     * 绘制多边形
     *
     * @param canvas 画布
     * @param rectF  矩形
     * @param paint  画笔
     * @param offset 半径偏移量
     */
    private void drawPath(Canvas canvas, RectF rectF, Paint paint, float offset) {
        Path path = new Path();
        path.addRoundRect(rectF,
                new float[]{
                        offsetRadius(topLeftRadius_x, offset), offsetRadius(topLeftRadius_y, offset),
                        offsetRadius(topRightRadius_x, offset), offsetRadius(topRightRadius_y, offset),
                        offsetRadius(bottomRightRadius_x, offset), offsetRadius(bottomRightRadius_y, offset),
                        offsetRadius(bottomLeftRadius_x, offset), offsetRadius(bottomLeftRadius_y, offset)}, Path.Direction.CW);
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 计算半径偏移值，必须大于等于0
     *
     * @param radius 半径
     * @param offset 偏移量
     * @return 偏移半径
     */
    private float offsetRadius(float radius, float offset) {
        return Math.max(radius - offset, 0);
    }

    /**
     * drawable 转 bitmap
     * 这个函数可以放在Util类里面，算是一个公共函数
     *
     * @param drawable 要转换的Drawable
     * @return 转换完成的Bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * @param drawable true 可用  false 不可用
     * @return
     */
    private boolean drawableDisable(Drawable drawable) {
        return drawable != null && drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0;
    }

    /////////////////////////////  set/get   /////////////////////////////

    public void setRadius(float radius) {
        setTopLeftRadius(radius);
        setTopRightRadius(radius);
        setBottomLeftRadius(radius);
        setBottomRightRadius(radius);
    }

    public void setTopLeftRadius(float topLeftRadius) {
        setTopLeftRadius_x(topLeftRadius);
        setTopLeftRadius_y(topLeftRadius);
    }

    public void setTopRightRadius(float topRightRadius) {
        setTopRightRadius_x(topRightRadius);
        setTopRightRadius_y(topRightRadius);
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        setBottomLeftRadius_x(bottomLeftRadius);
        setBottomLeftRadius_y(bottomLeftRadius);
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        setBottomRightRadius_x(bottomRightRadius);
        setBottomRightRadius_y(bottomRightRadius);
    }

    public void setStyleType(int styleType) {
        this.styleType = styleType;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
    }

    public void setBorderSpace(float borderSpace) {
        this.borderSpace = borderSpace;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
    }

    public Paint getPaint() {
        return paint;
    }

    public Paint getBorderPaint() {
        return borderPaint;
    }

    public void setTopLeftRadius_x(float topLeftRadius_x) {
        this.topLeftRadius_x = topLeftRadius_x;
    }

    public void setTopLeftRadius_y(float topLeftRadius_y) {
        this.topLeftRadius_y = topLeftRadius_y;
    }

    public void setTopRightRadius_x(float topRightRadius_x) {
        this.topRightRadius_x = topRightRadius_x;
    }

    public void setTopRightRadius_y(float topRightRadius_y) {
        this.topRightRadius_y = topRightRadius_y;
    }

    public void setBottomLeftRadius_x(float bottomLeftRadius_x) {
        this.bottomLeftRadius_x = bottomLeftRadius_x;
    }

    public void setBottomLeftRadius_y(float bottomLeftRadius_y) {
        this.bottomLeftRadius_y = bottomLeftRadius_y;
    }

    public void setBottomRightRadius_x(float bottomRightRadius_x) {
        this.bottomRightRadius_x = bottomRightRadius_x;
    }

    public void setBottomRightRadius_y(float bottomRightRadius_y) {
        this.bottomRightRadius_y = bottomRightRadius_y;
    }
}
