package com.shanchuang.ssf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.shanchuang.ssf.R;


public class ImageViewPlus extends AppCompatImageView {
    /**
     * android.widget.ImageView
     */
    public static final int TYPE_NONE = 0;
    /**
     * 圆形
     */
    public static final int TYPE_CIRCLE = 1;
    /**
     * 圆角矩形
     */
    public static final int TYPE_ROUNDED_RECT = 2;
    public static final int TYPE_ROUNDED = 3;

    private static final int DEFAULT_TYPE = TYPE_NONE;
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_RECT_ROUND_RADIUS = 0;
    private static final float DEFAULT_RECT_ROUND_RADIO = 1.0f;
    private boolean mLeftBottomRoundRadius = false;
    private boolean mRightBottomRoundRadius = false;
    private boolean mLeftTopRectRoundRadius = false;
    private boolean mRightTopRoundRadius = false;

    private int mType;
    private int mBorderColor;
    private int mBorderWidth;
    private int mRectRoundRadius;

    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mRectBorder = new RectF();
    private RectF mRectBitmap = new RectF();

    private Bitmap mRawBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();

    private boolean mIsUseImageColor = false;
    private int mImageColor = Color.TRANSPARENT;
    private float[] rids = {10.0f, 10.0f, 10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f,};
    /**
     * 图片宽和高的比例
     */

    private
    float ratio = 1.5f;

    public ImageViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        //取xml文件中设定的参数
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageViewPlus);
        mType = ta.getInt(R.styleable.ImageViewPlus_type, DEFAULT_TYPE);
        mBorderColor = ta.getColor(R.styleable.ImageViewPlus_borderColor, DEFAULT_BORDER_COLOR);
        mBorderWidth = ta.getDimensionPixelSize(R.styleable.ImageViewPlus_borderWidth, dip2px(DEFAULT_BORDER_WIDTH));
        mRectRoundRadius = ta.getDimensionPixelSize(R.styleable.ImageViewPlus_rectRoundRadius, dip2px(DEFAULT_RECT_ROUND_RADIUS));
        mLeftTopRectRoundRadius = ta.getBoolean(R.styleable.ImageViewPlus_rectLeftTopRoundRadius, false);
        mRightTopRoundRadius = ta.getBoolean(R.styleable.ImageViewPlus_rectRightTopRoundRadius, false);
        mLeftBottomRoundRadius = ta.getBoolean(R.styleable.ImageViewPlus_rectLeftBottomRoundRadius, false);
        mRightBottomRoundRadius = ta.getBoolean(R.styleable.ImageViewPlus_rectRightBottomRoundRadius, false);
        ratio = ta.getFloat(R.styleable.ImageViewPlus_radios, DEFAULT_RECT_ROUND_RADIO);
        ta.recycle();
    }

    private int dip2px(int dipVal) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipVal * scale + 0.5f);
    }

    /**
     * 获取类型
     *
     * @return TYPE_CIRCLE(圆形) TYPE_ROUNDED_RECT(圆角矩形)
     */
    public int getType() {
        return this.mType;
    }

    /**
     * 设置类型(自动重绘)
     *
     * @param type TYPE_CIRCLE(圆形) TYPE_ROUNDED_RECT(圆角矩形)
     * @see #setType(int type, boolean fUpdateView)
     */
    public void setType(int type) {
        setType(type, true);
    }

    /**
     * 设置类型
     *
     * @param type        TYPE_CIRCLE(圆形) TYPE_ROUNDED_RECT(圆角矩形)
     * @param fUpdateView 是否自动重绘
     */
    public void setType(int type, boolean fUpdateView) {
        if (type != this.mType
                && (type == TYPE_CIRCLE || type == TYPE_ROUNDED_RECT || type == TYPE_ROUNDED)) {
            this.mType = type;
            if (fUpdateView) {
                invalidate();
            }
        }
    }

    /**
     * 获取边缘宽度
     *
     * @return 边缘宽度(像素)
     */
    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    /**
     * 设置边缘宽度(自动重绘)
     *
     * @param width 边缘宽度(像素)
     * @see #setBorderWidth(int width, boolean fUpdateView)
     */
    public void setBorderWidth(int width) {
        setBorderWidth(width, true);
    }

    /**
     * 设置边缘宽度
     *
     * @param width       边缘宽度(像素)
     * @param fUpdateView 是否自动重绘
     */
    public void setBorderWidth(int width, boolean fUpdateView) {
        if (width != this.mBorderWidth
                && width >= 0
                && width <= Math.min(getWidth(), getHeight()) / 2) {
            this.mBorderWidth = width;
            if (fUpdateView) {
                invalidate();
            }
        }
    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        // 父容器传过来的宽度方向上的模式

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        // 父容器传过来的高度方向上的模式

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        // 父容器传过来的宽度的值

        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();

        // 父容器传过来的高度的值

        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft() - getPaddingRight();


        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && ratio != 0.0f) {

            // 判断条件为，宽度模式为Exactly，也就是填充父窗体或者是指定宽度；

            // 且高度模式不是Exaclty，代表设置的既不是fill_parent也不是具体的值，于是需要具体测量

            // 且图片的宽高比已经赋值完毕，不再是0.0f

            // 表示宽度确定，要测量高度

            height = (int) (width / ratio + 0.5f);

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,

                    MeasureSpec.EXACTLY);

        } else if (widthMode != MeasureSpec.EXACTLY

                && heightMode == MeasureSpec.EXACTLY && ratio != 0.0f) {

            // 判断条件跟上面的相反，宽度方向和高度方向的条件互换

            // 表示高度确定，要测量宽度

            width = (int) (height * ratio + 0.5f);


            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,

                    MeasureSpec.EXACTLY);

        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap rawBitmap = getBitmap(getDrawable());

        if (rawBitmap != null && mType != TYPE_NONE) {
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            int viewMinSize = Math.min(viewWidth, viewHeight);
            float dstWidth = mType == TYPE_CIRCLE ? viewMinSize : viewWidth;
            float dstHeight = mType == TYPE_CIRCLE ? viewMinSize : viewHeight;
            float halfBorderWidth = mBorderWidth / 2.0f;
            float doubleBorderWidth = mBorderWidth * 2;

            if (mShader == null || !rawBitmap.equals(mRawBitmap)) {
                mRawBitmap = rawBitmap;
                mShader = new BitmapShader(mRawBitmap, TileMode.CLAMP, TileMode.CLAMP);
            }
            if (mShader != null) {
                mMatrix.setScale((dstWidth - doubleBorderWidth) / rawBitmap.getWidth(), (dstHeight - doubleBorderWidth) / rawBitmap.getHeight());
                mShader.setLocalMatrix(mMatrix);
            }

            mPaintBitmap.setShader(mShader);
            mPaintBorder.setStyle(Paint.Style.STROKE);
            mPaintBorder.setStrokeWidth(mBorderWidth);
            mPaintBorder.setColor(mBorderWidth > 0 ? mBorderColor : Color.TRANSPARENT);

            if (mType == TYPE_CIRCLE) {
                float radius = viewMinSize / 2.0f;
                canvas.drawCircle(radius, radius, radius - halfBorderWidth, mPaintBorder);
                canvas.translate(mBorderWidth, mBorderWidth);
                canvas.drawCircle(radius - mBorderWidth, radius - mBorderWidth, radius - mBorderWidth, mPaintBitmap);
            } else if (mType == TYPE_ROUNDED_RECT) {
                mRectBorder.set(halfBorderWidth, halfBorderWidth, dstWidth - halfBorderWidth, dstHeight - halfBorderWidth);
                mRectBitmap.set(0.0f, 0.0f, dstWidth - doubleBorderWidth, dstHeight - doubleBorderWidth);
                float borderRadius = mRectRoundRadius - halfBorderWidth > 0.0f ? mRectRoundRadius - halfBorderWidth : 0.0f;
                float bitmapRadius = mRectRoundRadius - mBorderWidth > 0.0f ? mRectRoundRadius - mBorderWidth : 0.0f;
                canvas.drawRoundRect(mRectBorder, borderRadius, borderRadius, mPaintBorder);
                canvas.translate(mBorderWidth, mBorderWidth);
                canvas.drawRoundRect(mRectBitmap, bitmapRadius, bitmapRadius, mPaintBitmap);
                if (mLeftTopRectRoundRadius) {
                    canvas.drawRect(0, 0, bitmapRadius, bitmapRadius, mPaintBitmap);
                }
                if (mLeftBottomRoundRadius) {
                    canvas.drawRect(mRectBorder.right - bitmapRadius, 0, mRectBorder.right, bitmapRadius, mPaintBitmap);
                }
                if (mRightTopRoundRadius) {
                    canvas.drawRect(0, mRectBorder.bottom - bitmapRadius, bitmapRadius, mRectBorder.bottom, mPaintBitmap);
                }
                if (mRightBottomRoundRadius) {
                    canvas.drawRect(mRectBorder.right - bitmapRadius, mRectBorder.bottom - bitmapRadius, mRectBorder.right, mRectBorder.bottom, mPaintBitmap);
                }
            }
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (mIsUseImageColor || drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = mIsUseImageColor ? mImageColor : ((ColorDrawable) drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            return null;
        }
    }

    public void setRatio(float ratio) {

        this.ratio = ratio;

    }

    /**
     * 获取边缘颜色
     *
     * @return 边缘颜色(color - int)
     */
    public int getBorderColor() {
        return this.mBorderColor;
    }

    /**
     * 设置边缘颜色(自动重绘)
     *
     * @param colorid 边缘颜色(color-int)
     * @see #setBorderColor(int colorid, boolean fUpdateView)
     */
    public void setBorderColor(int colorid) {
        setBorderColor(colorid, true);
    }

    /**
     * 设置边缘颜色
     *
     * @param colorid     边缘颜色(color-int)
     * @param fUpdateView 是否自动重绘
     */
    public void setBorderColor(int colorid, boolean fUpdateView) {
        if (colorid != this.mBorderColor) {
            this.mBorderColor = colorid;
            if (fUpdateView) {
                invalidate();
            }
        }
    }

    /**
     * 获取圆角矩形弧度半径
     *
     * @return 弧度半径(像素)
     */
    public int getRectRoundRadius() {
        return this.mRectRoundRadius;
    }

    /**
     * 设置圆角矩形弧度半径(自动重绘)
     *
     * @param radius 弧度半径(像素)
     * @see #setRectRoundRadius(int radius, boolean fUpdateView)
     */
    public void setRectRoundRadius(int radius) {
        setRectRoundRadius(radius, true);
    }

    /**
     * 设置圆角矩形弧度半径
     *
     * @param radius      弧度半径(像素)
     * @param fUpdateView 是否自动重绘
     */
    public void setRectRoundRadius(int radius, boolean fUpdateView) {
        if (this.mType == TYPE_ROUNDED_RECT
                && radius != this.mRectRoundRadius
                && radius >= 0
                && radius <= Math.min(getWidth(), getHeight()) / 2) {
            this.mRectRoundRadius = radius;
            if (fUpdateView) {
                invalidate();
            }
        }
    }

    /**
     * 设置图像为纯色(自动重绘)
     *
     * @param colorid color-int
     */
    public void setImageColor(int colorid) {
        setImageColor(colorid, true);
    }

    /**
     * 设置图像为纯色
     *
     * @param colorid     color-int
     * @param fUpdateView 是否自动重绘
     */
    public void setImageColor(int colorid, boolean fUpdateView) {
        if (!mIsUseImageColor || colorid != mImageColor) {
            this.mImageColor = colorid;
            mIsUseImageColor = true;
            if (fUpdateView) {
                invalidate();
            }
        }
    }

    /**
     * 重置图像为非纯色模式
     */
    public void resetImageColor() {
        mIsUseImageColor = false;
        mImageColor = Color.TRANSPARENT;
    }
}
