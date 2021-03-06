package com.gtdev5.geetolsdk.mylibrary.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.gtdev5.geetolsdk.R;

/**
 * Created by cheng
 * PackageName APP_Lock
 * 2018/1/8 9:28
 *          Dialog基类
 */

public abstract class BaseDialog extends Dialog {

    protected Context mContext;
    protected DisplayMetrics dm;
    protected float widthScale = 1;

    protected AnimatorSet enterAnim;
    protected AnimatorSet exitAnim;

    protected int width;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.DialogTransparent);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewId() <= 0){
            new RuntimeException("layout resId undefind");
        }
        setContentView(getContentViewId());
        dm = mContext.getResources().getDisplayMetrics();
        init();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();

        widthScale = setWidthScale();
        if (widthScale == 0){
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }else {
            width = (int) (dm.widthPixels * widthScale);
        }

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        window.setAttributes(layoutParams);

        enterAnim = setEnterAnim();
        if (enterAnim != null) {
            enterAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                superDismiss();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            enterAnim.start();
        }

    }

    @Override
    public void dismiss() {
        exitAnim = setExitAnim();
        if (exitAnim == null){
            superDismiss();
            return;
        }
        exitAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            superDismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            superDismiss();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        exitAnim.start();
    }

    public void superDismiss(){
        super.dismiss();
    }

    //设置横向缩放比例
    protected abstract float setWidthScale();

    //设置进入动画
    protected abstract AnimatorSet setEnterAnim();

    //设置退出动画
    protected abstract AnimatorSet setExitAnim();

    //初始化
    protected abstract void init();

    //设置布局id
    protected abstract int getContentViewId();
}
