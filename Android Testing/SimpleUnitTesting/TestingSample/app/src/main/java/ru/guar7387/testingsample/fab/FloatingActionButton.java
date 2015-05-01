package ru.guar7387.testingsample.fab;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;

import ru.guar7387.testingsample.R;

public class FloatingActionButton extends View {

    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mDrawablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap mBitmap;
    private int mColor;

    private boolean mHidden = false;

    private float mYDisplayed = -1;
    private float mYHidden = -1;

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        createFromTypedArray(attrs);

        display(context);
    }

    private void createFromTypedArray(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FloatingActionButton);
        mColor = typedArray.getColor(R.styleable.FloatingActionButton_fabColor, Color.WHITE);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setColor(mColor);

        float radius = typedArray.getFloat(R.styleable.FloatingActionButton_shadowRadius, 10.0f);
        float dx = typedArray.getFloat(R.styleable.FloatingActionButton_shadowDx, 0.0f);
        float dy = typedArray.getFloat(R.styleable.FloatingActionButton_shadowDy, 3.5f);

        int color = typedArray.getInteger(R.styleable.FloatingActionButton_shadowColor, Color.argb(100, 0, 0, 0));

        mButtonPaint.setShadowLayer(radius, dx, dy, color);
        Drawable drawable = typedArray.getDrawable(R.styleable.FloatingActionButton_drawable);
        typedArray.recycle();
        if (null != drawable) {
            mBitmap = ((BitmapDrawable) drawable).getBitmap();
        }
    }

    private void display(Context context) {
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mYHidden = size.y;
    }

    public void setColor(int color) {
        mColor = color;
        mButtonPaint.setColor(mColor);
        invalidate();
    }

    public void setDrawable(Drawable drawable) {
        mBitmap = ((BitmapDrawable) drawable).getBitmap();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (float) (getWidth() / 2.6), mButtonPaint);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, (getWidth() - mBitmap.getWidth()) / 2,
                    (getHeight() - mBitmap.getHeight()) / 2, mDrawablePaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mYDisplayed == -1) {
            mYDisplayed = this.getY();
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int color;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            color = mColor;
        } else {
            color = darkenColor(mColor);
        }
        mButtonPaint.setColor(color);
        invalidate();
        return super.onTouchEvent(event);
    }

    public void hide(boolean hide) {
        if (mHidden != hide) {
            mHidden = hide;
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "Y", mHidden ? mYHidden : mYDisplayed);
            animator.setInterpolator(mInterpolator);
            animator.start();
        }
    }

    public void attachToListView(AbsListView listView) {
        if (listView != null) {
            listView.setOnScrollListener(new DirectionScrollListener(this));
        }
    }

    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }
}