package com.example.esercitazionebonusdl;

import static com.example.esercitazionebonusdl.LoginActivity.USER_EXTRA;
import static com.example.esercitazionebonusdl.User.users;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;

public class AdminPasswordChangerActivity extends AppCompatActivity {

    private User user;
    private TextView newPasswordOk;
    private Button goBackHomeButton, setNewPasswordButton;
    private TextInputEditText oldPassword, newPassword, newPasswordRepeat;
    private TextInputLayout oldPasswordLayout, newPasswordLayout, newPasswordRepeatLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_password_changer);

        goBackHomeButton = findViewById(R.id.goBackHomeButton);
        setNewPasswordButton = findViewById(R.id.validationNewPasswordButton);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        newPasswordRepeat = findViewById(R.id.newPasswordRepeat);
        oldPasswordLayout = findViewById(R.id.oldPasswordInputLayer);
        newPasswordLayout = findViewById(R.id.newPasswordInputLayer);
        newPasswordRepeatLayout = findViewById(R.id.newPasswordRepeatInputLayer);
        newPasswordOk = findViewById(R.id.newPasswordOk);

        // Prendo tutti gli intent passati a questa activity
        Intent intentGetter = getIntent();

        Serializable obj = intentGetter.getSerializableExtra(USER_EXTRA);

        // Controllo che l'oggetto passato sia un User. Se non lo è lo istanzio
        if(obj instanceof User)
            user = (User) obj;
        else
            user = new User();

        // Convalida della nuova password
        setNewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkNewPassword()){

                    // Metto la nuova password all'user che andrà passato all'activity
                    user.setPassword(newPassword.getText().toString());

                    // Metto la nuova password all'user salvato nella lista
                    for (int i = 0; i < users.size(); i++){
                        if(user.getUsername().equals(users.get(i).getUsername())){
                            users.get(i).setPassword(newPassword.getText().toString());
                        }
                    }

                    // Mostro messaggio di avvenuto aggiornamento della password
                    newPasswordOk.setVisibility(View.VISIBLE);
                }
            }
        });

        // Torna alla home dell'utente
        goBackHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // astrazione che predice l'intenzione (activity attuale , activity di destinazione)
                Intent returnToHomePage = new Intent(AdminPasswordChangerActivity.this, HomeActivityAdmin.class);

                returnToHomePage.putExtra(USER_EXTRA, user);

                startActivity(returnToHomePage);
                AdminPasswordChangerActivity.this.finish();
            }
        });

        // Rende visibile l'icona per mostrare/nascondere la password, la quale
        // viene disattivata in caso di campo password vuoto, altrimenti si
        // avrebbe una sovrapposizione delle icone
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oldPasswordLayout.setEndIconVisible(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Rende visibile l'icona per mostrare/nascondere la password, la quale
        // viene disattivata in caso di campo password vuoto, altrimenti si
        // avrebbe una sovrapposizione delle icone
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPasswordLayout.setEndIconVisible(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Rende visibile l'icona per mostrare/nascondere la password, la quale
        // viene disattivata in caso di campo password vuoto, altrimenti si
        // avrebbe una sovrapposizione delle icone
        newPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPasswordRepeatLayout.setEndIconVisible(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // Metodo per il controllo della modifica della passwordù
    protected boolean checkNewPassword(){

        int errors = 0;

        // controllo su input vecchia password
        if(!(oldPassword.getText().toString().equals(user.getPassword()))){

            errors++;
            oldPasswordLayout.setEndIconVisible(false);
            oldPassword.setError("La password attuale non corrisponde");
        }

        // controllo input nuova password
        if(newPassword.getText().toString().length() < 8){

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            newPasswordLayout.setEndIconVisible(false);
            newPassword.setError("La password deve essere lunga almeno 8 caratteri e" +
                    " contenere almeno 1 lettera minuscola, una maiuscola, un numero " +
                    "ed un carattere speciale.");
        } else if (!((newPassword.getText().toString()).matches(".*[0-9].*"))){

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            newPasswordLayout.setEndIconVisible(false);
            newPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        } else if (!((newPassword.getText().toString()).matches(".*[a-z].*"))){

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            newPasswordLayout.setEndIconVisible(false);
            newPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        } else if (!((newPassword.getText().toString()).matches(".*[A-Z].*"))){

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            newPasswordLayout.setEndIconVisible(false);
            newPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        }

        else if ((newPassword.getText().toString()).matches("[a-zA-Z0-9]*")){

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            newPasswordLayout.setEndIconVisible(false);
            newPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        }

        if (!(newPassword.getText().toString().equals(newPasswordRepeat.getText().toString()))){

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            newPasswordRepeatLayout.setEndIconVisible(false);
            newPasswordLayout.setEndIconVisible(false);
            newPassword.setError("Le due password inserite non sono uguali.");
            newPasswordRepeat.setError("Le due password inserite non sono uguali.");
        }

        if(newPassword.getText().toString().equals(user.getPassword())){
            errors++;
            newPasswordLayout.setEndIconVisible(false);
            newPassword.setError("La nuova password non può essere uguale alla vecchia!");
        }

        return errors == 0;
    }

    @Override
    public void onBackPressed() {}
}
