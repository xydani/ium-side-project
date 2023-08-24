package com.example.esercitazionebonusdl;

import static com.example.esercitazionebonusdl.User.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText insertUsername, insertPassword;
    private TextInputLayout passwordLayout;
    private Button accediButton, goToRegistrationButton;
    private User user;
    private TextView loginError;
    public static final String USER_EXTRA = "com.example.esercitazionebonusdl.user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        User adminUsr = new User("admin", "admin", "system", "", "01/01/0000", "true");

        insertUsername = findViewById(R.id.insertUsername);
        insertPassword = findViewById(R.id.insertPassword);
        accediButton = findViewById(R.id.accediButton);
        goToRegistrationButton = findViewById(R.id.goToRegistrationButton);
        loginError = findViewById(R.id.loginErrorMessage);
        passwordLayout = findViewById(R.id.passwordInputLayer);

        /* istanzio l'oggetto utente*/
        user = new User();

        /* Cliccando su "login" viene effettuato il controllo delle credenziali
         e poi indirizzati alla home page dell'utente */
        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkLoginInput()) {

                    if (checkUserData()){

                        updateUserData();

                        Intent goToHomePage;

                        if(isAdmin()) {
                            goToHomePage = new Intent(LoginActivity.this, HomeActivityAdmin.class);
                        } else {
                            goToHomePage = new Intent(LoginActivity.this, HomeActivityUser.class);
                        }
                        /* informazioni aggiuntive che passano insieme all'intent*/
                        goToHomePage.putExtra(USER_EXTRA, user);
                        startActivity(goToHomePage);
                    } else {
                        loginError.setVisibility(View.VISIBLE);
                        String loginErrorMsg = "Username o Password errati!";
                        loginError.setText(loginErrorMsg);
                    }
                }
            }
        });

        /* Cliccando su "registrati" si viene indirizzati alla pagina di registrazione*/
        goToRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* astrazione che predice l'intenzione (activity attuale , activity di destinazione)*/
                Intent goToRegistration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(goToRegistration);
            }
        });

        // Rende visibile l'icona per mostrare/nascondere la password, la quale
        // viene disattivata in caso di campo password vuoto, altrimenti si
        // avrebbe una sovrapposizione delle icone
        insertPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordLayout.setEndIconVisible(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /* metodo che controlla se ci sono errori nel form di login */
    protected boolean checkLoginInput(){

        int errors = 0;

        /* controllo su input username */
        if(insertUsername.getText().toString().length() == 0){

            errors++;
            insertUsername.setError("Inserisci un username");
        }

        /* controllo input password*/
        if(insertPassword.getText().toString().length() == 0) {

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            passwordLayout.setEndIconVisible(false);
            insertPassword.setError("Inserisci una password");
        }

        return errors == 0;
    }

    protected boolean checkUserData(){
        int numberOfUsers = users.size();

        for (int i = 0; i < numberOfUsers; i++){
            if(insertUsername.getText().toString().equals(users.get(i).getUsername()) &&
                insertPassword.getText().toString().equals(users.get(i).getPassword())){

                return true;
            }
        }
        return false;
    }

    protected void updateUserData(){

        String username = insertUsername.getText().toString();
        this.user.setUsername(username);

        String password = insertPassword.getText().toString();
        this.user.setPassword(password);
    }

    protected boolean isAdmin(){
        int numberOfUsers = users.size();
        boolean flag = false;

        for (int i = 0; i < numberOfUsers; i++){
            if(this.user.getUsername().equals(users.get(i).getUsername())){

                if(users.get(i).getIsAdmin().equals("true")) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}