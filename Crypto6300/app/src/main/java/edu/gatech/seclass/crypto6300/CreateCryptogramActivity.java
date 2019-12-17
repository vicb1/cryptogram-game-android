package edu.gatech.seclass.crypto6300;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateCryptogramActivity extends AppCompatActivity {
    private EditText txtCryptoName;
    private EditText txtCryptoAnswer;
    private EditText txtCryptoEasy;
    private EditText txtCryptoNormal;
    private EditText txtCryptoHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cryptogram);

        txtCryptoName = findViewById(R.id.gui_crypto_name);
        txtCryptoAnswer = findViewById(R.id.gui_crypto_ans);
        txtCryptoEasy = findViewById(R.id.gui_crypto_num_allowed_easy);
        txtCryptoNormal = findViewById(R.id.gui_crypto_num_allowed_normal);
        txtCryptoHard = findViewById(R.id.gui_crypto_num_allowed_hard);
    }

    public void saveCryptogram(View view) {
        String cryptoName = txtCryptoName.getText().toString();
        String cryptoAnswer = txtCryptoAnswer.getText().toString();
        String cryptoEasy = txtCryptoEasy.getText().toString();
        String cryptoNormal = txtCryptoNormal.getText().toString();
        String cryptoHard = txtCryptoHard.getText().toString();

        boolean isValid = validateCryptogramInfo(txtCryptoName, txtCryptoAnswer, txtCryptoEasy, txtCryptoNormal, txtCryptoHard);
//        boolean isValid = true;

        if (isValid) {
            long rowID = Admin.CreateNewCryptogram(cryptoName, cryptoAnswer, cryptoEasy, cryptoNormal, cryptoHard);
            if (rowID == -1) {
                Toast.makeText(getApplicationContext(),
                        "Failed to create the cryptogram. Please try again.",
                        Toast.LENGTH_LONG).show();
            }
            else {
                txtCryptoName.setText("");
                txtCryptoAnswer.setText("");
                txtCryptoEasy.setText("");
                txtCryptoNormal.setText("");
                txtCryptoHard.setText("");
                createCryptogramSuccess();
            }
        }
    }

    private void createCryptogramSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setMessage("Successfully created a cryptogram");
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

    private boolean validateCryptogramInfo(EditText txtCryptoName, EditText txtCryptoAnswer, EditText txtCryptoEasy, EditText txtCryptoNormal, EditText txtCryptoHard) {
        boolean isValid = true;
        if (txtCryptoName.getText().length() == 0) {
            isValid = false;
            txtCryptoName.setError("Cryptogram name is required.");
        } else {
            if (Admin.isCryptogramNameUsed(txtCryptoName.getText().toString())) {
                isValid = false;
                txtCryptoName.setError("Cryptogram name is not unique.");
            }
        }

        if (txtCryptoAnswer.getText().length() == 0) {
            isValid = false;
            txtCryptoAnswer.setError("Answer name is required.");
        }

        if (txtCryptoEasy.getText().length() == 0) {
            isValid = false;
            txtCryptoEasy.setError("Easy level is required.");
        }

        if (txtCryptoNormal.getText().length() == 0) {
            isValid = false;
            txtCryptoNormal.setError("Normal level is required.");
        }

        if (txtCryptoHard.getText().length() == 0) {
            isValid = false;
            txtCryptoHard.setError("Hard level is required.");
        }

        return isValid;
    }
}
