package edu.gatech.seclass.crypto6300;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditText = findViewById(R.id.login_input);
        mButton = findViewById(R.id.login_button);
        prepareDatabase();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mEditText.getText().toString();
                if (userName!= null && !userName.isEmpty()){
                    User user = User.login(userName);
                    if (user == null){
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.login_fail),
                                Toast.LENGTH_LONG).show();
                    } else {
                        if (user.getUserType().equals(UserType.Admin)) {
                            launchAdminActivity(user);
                        } else {
                            launchPlayerActivity(((Player)user));
                        }
                    }
                }
            }
        }
        );
    }

    private void launchAdminActivity(User user){
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra(AdminActivity.ADMIN_ID, user.getUserId());
        startActivity(intent);
    }

    private void launchPlayerActivity(Player player){
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.PLAYER, player);
        startActivity(intent);
    }

    private void  prepareDatabase(){
        DbHelper.setConnection(new SqliteConnection(getApplicationContext()));
    }
}
