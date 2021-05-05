package ru.mail.techpark.lesson9;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import superp.techpark.ru.lesson9.R;

/**
 * Пример показывает как можно комбинировать работу нескольких анимаций с помощью разных API
 */
public class AnimatorSetFragment extends Fragment {

    View root;
    View ball1;
    View ball2;
    View ball3;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_animator_set, container, false);
        root = inflate.findViewById(R.id.root);
        ball1 = inflate.findViewById(R.id.ball1);
        ball2 = inflate.findViewById(R.id.ball2);
        ball3 = inflate.findViewById(R.id.ball3);

        inflate.findViewById(R.id.play).setOnClickListener(v -> onPlayClick());
        inflate.findViewById(R.id.play_with_builder).setOnClickListener(v -> onPlayWithBuilderClick());

        return inflate;
    }

    void onPlayClick() {
        reset();

        final int targetY = 2 * root.getHeight() / 3;
        final AnimatorSet together = new AnimatorSet();
        final long duration = 2000L;
        together.playTogether(
                ObjectAnimator.ofFloat(ball1, View.TRANSLATION_Y, 0, targetY).setDuration(duration),
                ObjectAnimator.ofFloat(ball3, View.TRANSLATION_Y, 0, targetY).setDuration(duration)
        );
        final AnimatorSet all = new AnimatorSet();
        all.playSequentially(together, ObjectAnimator.ofFloat(ball2, View.TRANSLATION_Y, 0, targetY).setDuration(duration));
        all.start();
    }

    void onPlayWithBuilderClick() {
        reset();

        final int targetY = 2 * root.getHeight() / 3;
        final long duration = 2000L;
        final AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(ball1, View.TRANSLATION_Y, 0, targetY)
                        .setDuration(duration))
                .with(ObjectAnimator.ofFloat(ball3, View.TRANSLATION_Y, 0, targetY).setDuration(duration))
                .after(ObjectAnimator.ofFloat(ball2, View.TRANSLATION_Y, 0, targetY).setDuration(duration));
        set.start();
    }

    private void reset() {
        ball1.setTranslationY(0);
        ball2.setTranslationY(0);
        ball3.setTranslationY(0);
    }
}
