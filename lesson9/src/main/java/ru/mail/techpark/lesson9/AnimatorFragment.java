package ru.mail.techpark.lesson9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import superp.techpark.ru.lesson9.R;

/**
 * Пример демонстрирует работу с {@link ValueAnimator} и {@link ObjectAnimator}
 */
public class AnimatorFragment extends Fragment {

    TextView target;

    int startFontSize;

    int endFontSize;

    private ValueAnimator animator;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_animator, container, false);
        target = inflate.findViewById(R.id.target);
        startFontSize = getResources().getDimensionPixelSize(R.dimen.start_font_size);
        endFontSize = getResources().getDimensionPixelSize(R.dimen.end_font_size);
        inflate.findViewById(R.id.rotate_and_alpha).setOnClickListener(v -> onRotateAndAlphaClick());
        inflate.findViewById(R.id.rotate_and_reverse).setOnClickListener(v -> onRotateAndReverseClick());
        inflate.findViewById(R.id.text_color).setOnClickListener(v -> onTextColorClick());
        inflate.findViewById(R.id.text_size).setOnClickListener(v -> onTextColorSize());
        return inflate;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reset();
    }

    /**
     * Вращает текст и одновременно меняет прозрачность
     */
    void onRotateAndAlphaClick() {
        reset();

        final float endValue = 360f;
        animator = ValueAnimator.ofFloat(0, endValue);
        animator.setDuration(1000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                target.setRotation(value);
                target.setAlpha(1f - value / endValue);
            }
        });
        animator.start();
    }

    /**
     * Триджы вращает текст на 360 градусов со сменой направления вращения
     */
    void onRotateAndReverseClick() {
        reset();

        animator = ObjectAnimator.ofFloat(target, View.ROTATION, 0, 360);
        animator.setDuration(1000L);
        animator.setRepeatCount(2);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    /**
     * Анимированно меняет цвет текста
     */
    void onTextColorClick() {
        reset();

        animator = ValueAnimator.ofArgb(0xFFFF0000, 0xFF00FF00, 0xFF0000FF);
        animator.setDuration(6000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                target.setTextColor((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * Анимированно меняет размер шрифта
     */
    void onTextColorSize() {
        reset();

        animator = ValueAnimator.ofFloat(startFontSize, endFontSize);
        animator.setDuration(3000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                target.setTextSize((float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void reset() {
        if (animator != null) {
            animator.cancel();
        }
        target.setTextColor(Color.BLACK);
        target.setTextSize(startFontSize);
        target.setRotation(0);
        target.setAlpha(1f);
    }

}
