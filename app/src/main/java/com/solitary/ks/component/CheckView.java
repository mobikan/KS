

package com.solitary.ks.component;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import com.solitary.ks.R.styleable;

public class CheckView extends View {
    private static final String TAG = CheckView.class.getSimpleName();
    private static final boolean DEBUG = false;
    private static final long CHECK_ANIM_DURATION = 500L;
    private static final long SCALE_ANIM_DELAY = 280L;
    private static final long SCALE_ANIM_DURATION = 250L;
    private static final float DEFAULT_STROKE_WIDTH = 8.0F;
    private static final int DEFAULT_STROKE_COLOR = -15029504;
    private static final float SCALE_MIN = 0.8F;
    private Interpolator mCheckInterpolator;
    private Path mPathCircle;
    private Path mPathCheck;
    private float mMinorContourLength;
    private float mMajorContourLength;
    private float mStrokeWidth = 8.0F;
    private int mStrokeColor = -15029504;
    private int unCheckColor = -15029504;
    private RectF mDrawingRect;
    private RectF mCircleRect;
    private Paint mPaint;
    private Paint unCheckPaint;
    private PathMeasure mPathMeasure;
    private float[] mPoint;
    private PointF mCheckStart;
    private PointF mCheckPivot;
    private PointF mCheckEnd;
    private PointF mCircleStart;
    private ValueAnimator mCheckAnimator;
    private ValueAnimator mCircleAnimator;
    private ValueAnimator mScaleAnimator;
    private boolean mChecked = false;
    private final AnimatorUpdateListener mCheckAnimatorListener = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            float fraction = animation.getAnimatedFraction();
            CheckView.this.setCheckPathPercentage(fraction);
            CheckView.this.invalidate();
        }
    };
    private final AnimatorUpdateListener mCircleAnimatorListener = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            float fraction = animation.getAnimatedFraction();
            CheckView.this.setCirclePathPercentage(fraction);
            CheckView.this.invalidate();
        }
    };
    private final AnimatorUpdateListener mScaleAnimatorListener = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (Float)animation.getAnimatedValue();
            CheckView.this.setScaleX(value);
            CheckView.this.setScaleY(value);
            CheckView.this.invalidate();
        }
    };


    public boolean isChecked()
    {
      return mChecked;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public CheckView(Context context) {
        super(context);
        this.init(context, (AttributeSet)null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public CheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public CheckView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void init(Context context, @Nullable AttributeSet attrs) {
        this.resolveAttributes(context, attrs);
        this.mPathCheck = new Path();
        this.mPathCircle = new Path();
        this.mDrawingRect = new RectF();
        this.mCircleRect = new RectF();
        this.mPathMeasure = new PathMeasure();
        this.mPoint = new float[2];
        this.mCheckStart = new PointF();
        this.mCheckPivot = new PointF();
        this.mCheckEnd = new PointF();
        this.mCircleStart = new PointF();
        this.mCheckAnimator = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
        this.mCircleAnimator = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
        this.mScaleAnimator = ValueAnimator.ofFloat(new float[]{1.0F, 0.8F, 1.0F});
        this.mCheckInterpolator = this.createCheckInterpolatorCompat();
        this.mPaint = this.createPaint(this.mStrokeColor, this.mStrokeWidth);
        this.unCheckPaint =  this.createUncheckPaint(this.unCheckColor, this.mStrokeWidth);
        check();
        uncheck();

    }

    private void resolveAttributes(Context c, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = c.getTheme().obtainStyledAttributes(attrs, styleable.CheckView, 0, 0);

            try {
                this.mStrokeWidth = a.getDimension(styleable.CheckView_checkView_strokeWidth, 8.0F);
                this.mStrokeColor = a.getColor(styleable.CheckView_checkView_strokeColor, -15029504);
                this.unCheckColor= a.getColor(styleable.CheckView_checkView_unCheckColor, -15029504);
            } finally {
                a.recycle();
            }

        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            this.mDrawingRect.left = (float)this.getPaddingLeft();
            this.mDrawingRect.top = (float)this.getPaddingTop();
            this.mDrawingRect.right = (float)(this.getMeasuredWidth() - this.getPaddingRight());
            this.mDrawingRect.bottom = (float)(this.getMeasuredHeight() - this.getPaddingBottom());
            this.mCheckStart.x = this.mDrawingRect.left + this.mDrawingRect.width() / 4.0F;
            this.mCheckStart.y = this.mDrawingRect.top + this.mDrawingRect.height() / 2.0F;
            this.mCheckPivot.x = this.mDrawingRect.left + this.mDrawingRect.width() * 0.426F;
            this.mCheckPivot.y = this.mDrawingRect.top + this.mDrawingRect.height() * 0.66F;
            this.mCheckEnd.x = this.mDrawingRect.left + this.mDrawingRect.width() * 0.75F;
            this.mCheckEnd.y = this.mDrawingRect.top + this.mDrawingRect.height() * 0.3F;
            this.mMinorContourLength = distance(this.mCheckStart.x, this.mCheckStart.y, this.mCheckPivot.x, this.mCheckPivot.y);
            this.mMajorContourLength = distance(this.mCheckPivot.x, this.mCheckPivot.y, this.mCheckEnd.x, this.mCheckEnd.y);
            this.mCircleRect.left = this.mDrawingRect.left + this.mStrokeWidth / 2.0F;
            this.mCircleRect.top = this.mDrawingRect.top + this.mStrokeWidth / 2.0F;
            this.mCircleRect.right = this.mDrawingRect.right - this.mStrokeWidth / 2.0F;
            this.mCircleRect.bottom = this.mDrawingRect.bottom - this.mStrokeWidth / 2.0F;
            this.mCircleStart.x = this.mCircleRect.right;
            this.mCircleStart.y = this.mCircleRect.bottom / 2.0F;
        }

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mChecked) {
            canvas.drawPath(this.mPathCheck, this.mPaint);
            canvas.drawPath(this.mPathCircle, this.mPaint);
        }
        else
        {
            canvas.drawPath(this.mPathCheck, this.unCheckPaint);
        }
    }

    public void check() {
        this.mChecked = true;
        this.mCheckAnimator.removeAllUpdateListeners();
        this.mCheckAnimator.setDuration(CHECK_ANIM_DURATION).setInterpolator(this.mCheckInterpolator);
        this.mCheckAnimator.addUpdateListener(this.mCheckAnimatorListener);
        this.mCircleAnimator.removeAllUpdateListeners();
        this.mCircleAnimator.setDuration(300L).setInterpolator(this.mCheckInterpolator);
        this.mCircleAnimator.addUpdateListener(this.mCircleAnimatorListener);
        this.mScaleAnimator.removeAllUpdateListeners();
        this.mScaleAnimator.setDuration(250L).setStartDelay(280L);
        this.mScaleAnimator.setInterpolator(new FastOutSlowInInterpolator());
        this.mScaleAnimator.addUpdateListener(this.mScaleAnimatorListener);
        this.mCheckAnimator.start();
        this.mCircleAnimator.start();
        this.mScaleAnimator.start();
    }

    public void uncheck() {
        this.mChecked = false;
        this.invalidate();
    }

    private Paint createPaint(@ColorInt int color, float strokeWidth) {
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Style.STROKE);
        p.setStrokeWidth(strokeWidth);
        p.setStrokeJoin(Join.ROUND);
        p.setAntiAlias(true);
        p.setStrokeCap(Cap.ROUND);
        return p;
    }

    private Paint createUncheckPaint(@ColorInt int color, float strokeWidth) {
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Style.STROKE);
        p.setStrokeWidth(strokeWidth);
        p.setStrokeJoin(Join.ROUND);
        p.setAntiAlias(true);
        p.setStrokeCap(Cap.ROUND);
        return p;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private Interpolator createCheckInterpolatorCompat() {
        return (Interpolator)(VERSION.SDK_INT >= 21 ? new PathInterpolator(0.755F, 0.05F, 0.855F, 0.06F) : new AccelerateInterpolator());
    }

    private void setCheckPathFull() {
        this.mPathCheck.reset();
        this.mPathCheck.moveTo(this.mCheckStart.x, this.mCheckStart.y);
        this.mPathCheck.lineTo(this.mCheckPivot.x, this.mCheckPivot.y);
        this.mPathCheck.lineTo(this.mCheckEnd.x, this.mCheckEnd.y);
    }

    private void setCheckPathPercentage(@FloatRange(from = 0.0D,to = 1.0D) float percent) {
        this.setCheckPathFull();
        float totalLength = this.mMinorContourLength + this.mMajorContourLength;
        float pivotPercent = this.mMinorContourLength / totalLength;
        float minorPercent;
        float distance;
        if (percent > pivotPercent) {
            minorPercent = percent - pivotPercent;
            distance = totalLength * minorPercent;
            this.mPathCheck.reset();
            this.mPathCheck.moveTo(this.mCheckPivot.x, this.mCheckPivot.y);
            this.mPathCheck.lineTo(this.mCheckEnd.x, this.mCheckEnd.y);
            this.mPathMeasure.setPath(this.mPathCheck, false);
            this.mPathMeasure.getPosTan(distance, this.mPoint, (float[])null);
            this.mPathCheck.reset();
            this.mPathCheck.moveTo(this.mCheckStart.x, this.mCheckStart.y);
            this.mPathCheck.lineTo(this.mCheckPivot.x, this.mCheckPivot.y);
            this.mPathCheck.lineTo(this.mPoint[0], this.mPoint[1]);
        } else if (percent < pivotPercent) {
            minorPercent = percent / pivotPercent;
            distance = this.mMinorContourLength * minorPercent;
            this.mPathMeasure.setPath(this.mPathCheck, false);
            this.mPathMeasure.getPosTan(distance, this.mPoint, (float[])null);
            this.mPathCheck.reset();
            this.mPathCheck.moveTo(this.mCheckStart.x, this.mCheckStart.y);
            this.mPathCheck.lineTo(this.mPoint[0], this.mPoint[1]);
        } else if (percent == pivotPercent) {
            this.mPathCheck.lineTo(this.mCheckPivot.x, this.mCheckPivot.y);
        }

    }

    private void setCirclePathPercentage(@FloatRange(from = 0.0D,to = 1.0D) float percent) {
        this.mPathCircle.reset();
        this.mPathCircle.moveTo(this.mCircleStart.x, this.mCircleStart.y);
        this.mPathCircle.addArc(this.mCircleRect, 0.0F, 360.0F);
        this.mPathMeasure.setPath(this.mPathCircle, false);
        float distance = this.mPathMeasure.getLength() * percent;
        this.mPathMeasure.getPosTan(distance, this.mPoint, (float[])null);
        this.mPathCircle.reset();
        this.mPathCircle.moveTo(this.mCircleStart.x, this.mCircleStart.y);
        this.mPathCircle.arcTo(this.mCircleRect, 0.0F, 359.0F * percent);
    }

    private static float distance(float x1, float y1, float x2, float y2) {
        float xAbs = Math.abs(x1 - x2);
        float yAbs = Math.abs(y1 - y2);
        return (float)Math.sqrt((double)(yAbs * yAbs + xAbs * xAbs));
    }
}
