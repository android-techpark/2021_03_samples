package superp.techpark.ru.lesson3.fragment_part.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Базовый фрагмент, логирующий методы жизненного цикла.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getLogTag(), "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getLogTag(), "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(getLogTag(), "onCreateView");
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getLogTag(), "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getLogTag(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getLogTag(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getLogTag(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getLogTag(), "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getLogTag(), "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getLogTag(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getLogTag(), "onDetach");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getLogTag(), "onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(getLogTag(), "onViewStateRestored");
    }

    protected String getLogTag() {
        return getClass().getSimpleName();
    }
}
