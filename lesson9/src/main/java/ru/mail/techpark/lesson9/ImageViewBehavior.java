package ru.mail.techpark.lesson9;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import superp.techpark.ru.lesson9.R;

public class ImageViewBehavior extends CoordinatorLayout.Behavior<View> {

    public ImageViewBehavior() {
    }

    public ImageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent,
                                   @NonNull View imageView,
                                   @NonNull View dependency) {
        return super.layoutDependsOn(parent, imageView, dependency) || dependency.getId() == R.id.trigger_button;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent,
                                          @NonNull View imageView,
                                          @NonNull View dependency) {

        if (dependency.getId() == R.id.trigger_button) {
            android.util.Log.d("check_coordinator", "button top:" + String.valueOf(dependency.getTop()));
            android.util.Log.d("check_coordinator", "imageview bottom:" + String.valueOf(imageView.getBottom()));
            imageView.setTranslationY(Math.min(0, dependency.getTop() - imageView.getBottom() - 200));
        }

        return super.onDependentViewChanged(parent, imageView, dependency);
    }
}
