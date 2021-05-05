package ru.mail.techpark.lesson9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import superp.techpark.ru.lesson9.R;

/**
 * Демонстрирует разницу между Property и View Animation. Обратите внимание на кликабельность
 * шарика в начале и в конце анимации.
 */
public class ComparisonFragment extends Fragment {

    private static final long ANIMATION_DURATION = 3000L;

    RadioButton radioViewAnim;

    View ball;

    Button playButton;

    int distance;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_comparision, container, false);

        playButton = inflate.findViewById(R.id.play);
        playButton.setOnClickListener(v -> onPlayClick());
        radioViewAnim = inflate.findViewById(R.id.radio_view_anim);
        ball = inflate.findViewById(R.id.ball);
        ball.setOnClickListener(v -> onBallClick());
        distance = getResources().getDimensionPixelSize(R.dimen.anim_distance);

        return inflate;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void onPlayClick() {
        if (radioViewAnim.isChecked()) {
            playViewAnimation();
        } else {
            playPropertyAnimation();
        }
    }

    void onBallClick() {
        Toast.makeText(getContext(), "Ball clicked", Toast.LENGTH_SHORT).show();
    }

    private void playViewAnimation() {
        reset();
        final TranslateAnimation animation = new TranslateAnimation(0, 0, 0, distance);
        animation.setDuration(ANIMATION_DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                playButton.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                playButton.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }
        });
        ball.startAnimation(animation);
    }

    private void playPropertyAnimation() {
        reset();
        playButton.setEnabled(false);
        ball.animate()
                .translationY(distance)
                .setDuration(ANIMATION_DURATION)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        playButton.setEnabled(true);
                    }
                }).start();
    }

    private void reset() {
        ball.clearAnimation();
        ball.setTranslationY(0);
    }
}
