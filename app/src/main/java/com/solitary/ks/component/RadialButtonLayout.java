package com.solitary.ks.component;

import android.animation.Animator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import com.solitary.ks.R;

/**
 *
 */
public class RadialButtonLayout extends FrameLayout implements View.OnClickListener{

    private final static long DURATION_SHORT = 400;
    private WeakReference<Context> weakContext;


    View btnMain;

    View btnOrange;

    View btnYellow;

    View btnGreen;

    View btnBlue;

    View btnIndigo;

    private boolean isOpen = false;
    private Toast toast;
    private View view;
    /**
     * Default constructor
     * @param context
     */
    public RadialButtonLayout(final Context context) {
        this(context, null);
    }

    public RadialButtonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
        btnOrange.setOnClickListener(onClickListener);
        btnYellow.setOnClickListener(onClickListener);
        btnGreen.setOnClickListener(onClickListener);
        btnBlue.setOnClickListener(onClickListener);
        btnIndigo.setOnClickListener(onClickListener);

    }

    public RadialButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        weakContext = new WeakReference<Context>( context );
        view  = LayoutInflater.from(context).inflate( R.layout.layout_radial_buttons, this);

        btnMain = view.findViewById(R.id.btn_main);
        btnOrange =  view.findViewById(R.id.btn_orange);
        btnOrange.setVisibility(GONE);
        btnYellow = view.findViewById(R.id.btn_yellow);
        btnYellow.setVisibility(GONE);
        btnGreen =  view.findViewById(R.id.btn_green);
        btnGreen.setVisibility(GONE);
        btnBlue = view.findViewById(R.id.rating);
        btnIndigo =  view.findViewById(R.id.addToReminder);

        btnMain.setOnClickListener(this);


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            //
        } else {

        }
    }

    private void showToast( final int resId ) {
        if ( toast != null )
            toast.cancel();
        toast = Toast.makeText( getContext(), resId, Toast.LENGTH_SHORT );
        toast.show();
    }
    final OvershootInterpolator interpolator = new OvershootInterpolator();


    public void onMainButtonClicked(final View btn ) {

        int resId = 0;
        if ( isOpen ) {
            // close
            hide(btnOrange);
            hide(btnYellow);
            hide(btnGreen);
            hide(btnBlue);
            hide(btnIndigo);
            isOpen = false;
            resId = R.string.close;

            ViewCompat.animate(btn).
                    rotation(0.0f).
                    withLayer().
                    setDuration(300).
                    setInterpolator(interpolator).
                    start();
        } else {
            show(btnOrange, 1, 250);
            show(btnYellow, 2, 250);
            show(btnGreen, 3, 250);
            show(btnBlue, 4, 250);
            show(btnIndigo, 5, 250);
            isOpen = true;
            resId = R.string.open;
            ViewCompat.animate(btn).
                    rotation(135f).
                    withLayer().
                    setDuration(300).
                    setInterpolator(interpolator).
                    start();

        }
       // showToast( resId);
        btn.playSoundEffect( SoundEffectConstants.CLICK);


    }


    public void onButtonClicked( final View btn ) {
        int resId = 0;
        switch ( btn.getId() ) {
            case R.id.btn_orange:
                resId = R.string.orange;
                break;
            case R.id.btn_yellow:
                resId = R.string.yellow;
                break;
            case R.id.btn_green:
                resId = R.string.green;
                break;
            case R.id.rating:
                resId = R.string.blue;
                break;
            case R.id.addToReminder:
                resId = R.string.indigo;
                break;
            default:
                resId = R.string.undefined;
        }
        showToast(resId);
        btn.playSoundEffect( SoundEffectConstants.CLICK);
    }

    private final void hide( final View child) {
        child.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                child.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        })
                .setDuration(DURATION_SHORT)
                .translationX(0)
                .translationY(0)
                .start();
    }

    private final void show(final View child, final int position, final int radius) {
        btnOrange.setVisibility(GONE);

        btnYellow.setVisibility(GONE);

        btnGreen.setVisibility(GONE);
        float angleDeg = 120.0f;
        int dist = radius;
        switch (position) {
            case 1:
                angleDeg += 30.f;
                break;
            case 2:
                angleDeg += 60.f;
                break;
            case 3:
                angleDeg += 70.f;
                break;
            case 4:
                angleDeg += 80.f;
                break;
            case 5:
                angleDeg += 130.f;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                break;
        }

        final float angleRad = (float) (angleDeg * Math.PI / 180.f);

        final Float x = dist * (float) Math.cos(angleRad);
        final Float y = dist * (float) Math.sin(angleRad);
        child.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                child.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        })
                .setDuration(DURATION_SHORT)
                .translationX(x)
                .translationY(y)
                .start();
    }

    @Override
    public void onClick(View view) {
        onMainButtonClicked(view);
    }
}