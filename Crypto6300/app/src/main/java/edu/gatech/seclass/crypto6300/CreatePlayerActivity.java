package edu.gatech.seclass.crypto6300;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreatePlayerActivity extends AppCompatActivity {
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtUserName;
    private RadioButton rbEasy;
    private RadioButton rbNormal;
    private RadioButton rbHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        txtFirstName = findViewById(R.id.create_player_txt_fn);
        txtLastName = findViewById(R.id.create_player_txt_ln);
        txtUserName = findViewById(R.id.create_player_txt_un);
        rbEasy = findViewById(R.id.create_player_rb_easy);
        rbNormal = findViewById(R.id.create_player_rb_normal);
        rbHard = findViewById(R.id.create_player_rb_hard);
    }

    public void savePlayer(View view) {
        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String username = txtUserName.getText().toString();

        boolean isValid = validatePlayerInfo(txtFirstName, txtLastName, txtUserName);

        Difficulty difficulty;

        if (rbEasy.isChecked()) {
            difficulty = Difficulty.EASY;
        }
        else if (rbNormal.isChecked()) {
            difficulty = Difficulty.NORMAL;
        }
        else {
            difficulty = Difficulty.HARD;
        }

        if (isValid) {
            long rowID = Admin.CreateNewPlayer(firstName, lastName, username, UserType.Player, difficulty);
            if (rowID == -1) {
                Toast.makeText(getApplicationContext(),
                        "Failed to create the player. Please try again.",
                        Toast.LENGTH_LONG).show();
            }
            else {
                txtFirstName.setText("");
                txtLastName.setText("");
                txtUserName.setText("");
                rbEasy.setChecked(true);
                createPlayerSuccess();
            }
        }
    }

    private void createPlayerSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setMessage(R.string.create_player_success);
        builder.setPositiveButton(
                R.string.OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean validatePlayerInfo(EditText txtFirstName, EditText txtLastName, EditText txtUserName) {
        boolean isValid = true;
        if (txtFirstName.getText().length() == 0) {
            isValid = false;
            txtFirstName.setError("First name is required.");
        }

        if (txtLastName.getText().length() == 0) {
            isValid = false;
            txtLastName.setError("Last name is required.");
        }

        if (txtUserName.length() == 0) {
            isValid = false;
            txtUserName.setError("Username is required.");
        }
        else {
            if (Admin.isUsernameUsed(txtUserName.getText().toString())) {
                isValid = false;
                txtUserName.setError("Username is not unique.");
            }
        }
        return isValid;
    }
}
