package com.example.assignment.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.assignment.R;

public class CustomProgressDialog extends Dialog {

    private Context context;
    private ImageView imageView;

    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);

        this.context = context;
        WindowManager.LayoutParams windowLayoutManagerParams = getWindow().getAttributes();
        windowLayoutManagerParams.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(windowLayoutManagerParams);
        setTitle(null);
        setCancelable(false);
        //setCanceledOnTouchOutside(false);
        //setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120,120);
        imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.loading_blue);
        layout.addView(imageView, params);
        addContentView(layout, params);
    }


    @Override
    public void show() {
        super.show();

        RotateAnimation anim = new RotateAnimation(
                0.0f, 360.0f ,
                Animation.RELATIVE_TO_SELF, .5f,
                Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(5000);
        imageView.setAnimation(anim);
        imageView.startAnimation(anim);
    }

}