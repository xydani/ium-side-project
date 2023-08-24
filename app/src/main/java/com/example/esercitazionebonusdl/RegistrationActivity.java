package com.example.esercitazionebonusdl;

import static com.example.esercitazionebonusdl.User.users;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText registrationUseraname, registrationPassword, registrationPasswordRepeat,
            registrationCity, registrationBirthDate;
    private TextInputLayout passwordLayout, passwordRepeatLayout;
    private Button registrationButton, selectImageButton;
    private User user;
    private TextView profilePicOk, profilePicNotOk, errorFound;
    private GridLayout imgSelectorLayout;
    private ImageView pic1, pic2, pic3, pic4, pic5, pic6;
    private String imgPath;
    boolean profilePicSelected;
    int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registrationButton = findViewById(R.id.registrationButton);
        registrationBirthDate = findViewById(R.id.registrationBirthDate);
        registrationCity = findViewById(R.id.registrationCity);
        registrationUseraname = findViewById(R.id.registrationUsername);
        registrationPassword = findViewById(R.id.registrationPassword);
        registrationPasswordRepeat = findViewById(R.id.registrationPasswordRepeat);
        passwordLayout = findViewById(R.id.passwordInputLayer);
        passwordRepeatLayout = findViewById(R.id.passwordRepeatInputLayer);
        selectImageButton = findViewById(R.id.imgSelectorButton);
        imgSelectorLayout = findViewById(R.id.imgSelectorLayout);
        profilePicOk = findViewById(R.id.profilePicOk);
        profilePicNotOk = findViewById(R.id.profilePicNotOk);
        errorFound = findViewById(R.id.errorFound);
        pic1 = findViewById(R.id.pic1);
        pic2 = findViewById(R.id.pic2);
        pic3 = findViewById(R.id.pic3);
        pic4 = findViewById(R.id.pic4);
        pic5 = findViewById(R.id.pic5);
        pic6 = findViewById(R.id.pic6);


        user = new User();

        imgSelectorListener();

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkRegistrationInput()) {

                    updateUser();
                    users.add(user);

                    /* messaggio pop up per conferma registrazione*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);

                    /* se impostato su true, si può chiudere il dialog cliccando in un punto
                    qualsiasi dello schermo */
                    builder.setCancelable(false);

                    builder.setTitle("Successo!");
                    builder.setMessage("Registrazione effettuata con successo, cliccando OK verrai" +
                            " indirizzato alla pagina di login dalla quale potrai effettuare l'accesso.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            /* astrazione che predice l'intenzione (activity attuale , activity di destinazione)*/
                            Intent returnToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);

                            startActivity(returnToLogin);
                            RegistrationActivity.this.finish();
                        }
                    });
                    builder.show();
                } else {
                    errorFound.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            errorFound.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
            }
        });

        registrationBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();
            }
        });

        registrationBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {
                    showDialog();
                }
            }
        });

        // Rende visibile l'icona per mostrare/nascondere la password, la quale
        // viene disattivata in caso di campo password vuoto, altrimenti si
        // avrebbe una sovrapposizione delle icone
        registrationPassword.addTextChangedListener(new TextWatcher() {
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

        // Rende visibile l'icona per mostrare/nascondere la password, la quale
        // viene disattivata in caso di campo password vuoto, altrimenti si
        // avrebbe una sovrapposizione delle icone
        registrationPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordRepeatLayout.setEndIconVisible(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Rende visibile il menu di selezione dell'immagine del profilo
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profilePicSelected = false;
                profilePicOk.setVisibility(View.GONE);
                imgSelectorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    void showDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    /* metodo che controlla se ci sono errori nel form di registrazione */
    protected boolean checkRegistrationInput() {

        int errors = 0;

        /* controllo su input username */
        if (registrationUseraname.getText().toString().length() == 0) {

            errors++;
            registrationUseraname.setError("Il campo username non può essere vuoto");
        }

        int numberOfUsers = users.size();

        for (int i = 0; i < numberOfUsers; i++) {
            if (registrationUseraname.getText().toString().equals(users.get(i).getUsername())) {
                errors++;
                registrationUseraname.setError("Username già in uso");
            }
        }

        /* controllo input password*/
        if (registrationPassword.getText().toString().length() < 8) {

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            passwordLayout.setEndIconVisible(false);
            registrationPassword.setError("La password deve essere lunga almeno 8 caratteri e" +
                    " contenere almeno 1 lettera minuscola, una maiuscola, un numero " +
                    "ed un carattere speciale.");
        } else if (!((registrationPassword.getText().toString()).matches(".*[0-9].*"))) {

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            passwordLayout.setEndIconVisible(false);
            registrationPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        } else if (!((registrationPassword.getText().toString()).matches(".*[a-z].*"))) {

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            passwordLayout.setEndIconVisible(false);
            registrationPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        } else if (!((registrationPassword.getText().toString()).matches(".*[A-Z].*"))) {

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            passwordLayout.setEndIconVisible(false);
            registrationPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        } else if ((registrationPassword.getText().toString()).matches("[a-zA-Z0-9]*")) {

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            passwordLayout.setEndIconVisible(false);
            registrationPassword.setError("La password deve contere almeno 1 numero, una lettera " +
                    "minuscola ed una lettera maiuscola ed un carattere speciale.");
        }

        if (!(registrationPassword.getText().toString().equals(registrationPasswordRepeat.getText().toString()))) {

            errors++;

            // se viene rilevato un errore, nascondo l'icona per mostrare/nascondere
            // la password, altrimenti si sovrapporrebbe con l'icona di errore
            passwordRepeatLayout.setEndIconVisible(false);
            registrationPasswordRepeat.setError("Le due password inserite non sono uguali.");
        }

        /* controllo su input città */
        if (registrationCity.getText().toString().length() == 0) {

            errors++;
            registrationCity.setError("Il campo città non può essere vuoto");
        }

        // Controllo su input data di nascita
        if (registrationBirthDate.getText().toString().length() == 0) {

            errors++;
            registrationBirthDate.setError("Il campo data di nascita non può essere vuoto");
        }

        // Controllo su selezione imamgine profilo
        if (!profilePicSelected) {
            errors++;
            profilePicNotOk.setVisibility(View.VISIBLE);
        }

        return errors == 0;
    }

    protected void updateUser() {

        String username = this.registrationUseraname.getText().toString();
        this.user.setUsername(username);

        String password = this.registrationPassword.getText().toString();
        String passwordRepeat = this.registrationPasswordRepeat.getText().toString();

        if (password.equals(passwordRepeat))
            this.user.setPassword(password);

        String city = this.registrationCity.getText().toString();
        this.user.setCity(city);

        this.user.setImgPath(imgPath);
    }

    public void doPositiveClick(Calendar date) {

        // Data odierna
        Calendar today = Calendar.getInstance();

        // Calcola l'età dell'utente
        age = today.get(Calendar.YEAR) - date.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < date.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        // Se l'utente non è maggiorenne dai errore
        if(age < 18){
            registrationBirthDate.setError("L'utente deve essere maggiorenne");
        } else {
            registrationBirthDate.setError(null);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            registrationBirthDate.setText(formato.format(date.getTime()));
            this.user.setBirthDate(formato.format(date.getTime()));
        }


    }

    // Metodo per impostare l'immagine del profilo selezionata dall'utente
    public void imgSelectorListener() {

        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "@drawable/pic1";
                imgSelectorLayout.setVisibility(View.GONE);
                profilePicOk.setVisibility(View.VISIBLE);
                profilePicNotOk.setVisibility(View.GONE);
                profilePicSelected = true;
            }
        });

        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "@drawable/pic2";
                imgSelectorLayout.setVisibility(View.GONE);
                profilePicOk.setVisibility(View.VISIBLE);
                profilePicNotOk.setVisibility(View.GONE);
                profilePicSelected = true;
            }
        });

        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "@drawable/pic3";
                imgSelectorLayout.setVisibility(View.GONE);
                profilePicOk.setVisibility(View.VISIBLE);
                profilePicNotOk.setVisibility(View.GONE);
                profilePicSelected = true;
            }
        });

        pic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "@drawable/pic4";
                imgSelectorLayout.setVisibility(View.GONE);
                profilePicOk.setVisibility(View.VISIBLE);
                profilePicNotOk.setVisibility(View.GONE);
                profilePicSelected = true;
            }
        });

        pic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "@drawable/pic5";
                imgSelectorLayout.setVisibility(View.GONE);
                profilePicOk.setVisibility(View.VISIBLE);
                profilePicNotOk.setVisibility(View.GONE);
                profilePicSelected = true;
            }
        });

        pic6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "@drawable/pic6";
                imgSelectorLayout.setVisibility(View.GONE);
                profilePicOk.setVisibility(View.VISIBLE);
                profilePicNotOk.setVisibility(View.GONE);
                profilePicSelected = true;
            }
        });
    }
}