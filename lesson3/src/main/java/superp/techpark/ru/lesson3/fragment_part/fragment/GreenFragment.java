package superp.techpark.ru.lesson3.fragment_part.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import superp.techpark.ru.lesson3.R;


/**
 * Fragment, демонстрирующий передачу параметра аргументом
 */
public class GreenFragment extends BaseFragment {
    private static final String EXTRA_PARAM = "some_param";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_green, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String text = "no value supplied";
        Bundle arguments = getArguments();
        if (arguments != null) {
            text = arguments.getString(EXTRA_PARAM);
        }
        ((TextView)view.findViewById(R.id.my_green_text)).setText(text);
    }

    public static GreenFragment newInstance(int param) {
        GreenFragment fragment = new GreenFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PARAM, String.valueOf(param));
        fragment.setArguments(bundle);
        return fragment;
    }
}
