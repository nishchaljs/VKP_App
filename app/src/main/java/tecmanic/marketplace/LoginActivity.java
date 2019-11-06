package tecmanic.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


//import android.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;

    private ProgressBar progressBar;
    private TextView btnReset;
    private RelativeLayout btnSignup, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);


    }
}