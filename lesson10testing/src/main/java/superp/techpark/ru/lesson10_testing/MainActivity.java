package superp.techpark.ru.lesson10_testing;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView myName = findViewById(R.id.my_name);
        final EditText newName = findViewById(R.id.update_name);
        Button updateBtn = findViewById(R.id.update_btn);

        final UserRepo repo = new UserRepo(this, new NameValidatorImpl());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repo.updateName(newName.getText().toString());
            }
        });

        repo.setOnUserNameListener(new UserRepo.UserNameListener() {
            @Override
            public void onNameChanged() {
                setName(myName, repo.getName());
            }
        });

        setName(myName, repo.getName());
    }

    private void setName(TextView view, String name) {
        if (TextUtils.isEmpty(name)) {
            view.setText(getString(R.string.empty_name));
        } else {
            view.setText(name);
        }
    }
}
