package com.example.esercitazionebonusdl;

import static com.example.esercitazionebonusdl.LoginActivity.USER_EXTRA;
import static com.example.esercitazionebonusdl.User.users;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;

public class HomeActivityUser extends AppCompatActivity {

    private TextView homeCity, homeBirthDate, homeUsername, homePassword;
    private LinearLayout goToChangePassword, goToManageUsers;
    private Button logoutButton;
    private User user;
    private ImageView userProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        homeCity = findViewById(R.id.homeCity);
        homeBirthDate = findViewById(R.id.homeBirthDate);
        homeUsername = findViewById(R.id.homeUsername);
        homePassword = findViewById(R.id.homePassword);
        logoutButton = findViewById(R.id.logoutButton);
        goToChangePassword = findViewById(R.id.goToChangePassword);
        goToManageUsers = findViewById(R.id.goToManageUsers);
        userProfilePic = findViewById(R.id.userProfileImage);

        /* Prendo tutti gli intent passati a questa activity*/
        Intent intentGetter = getIntent();

        Serializable obj = intentGetter.getSerializableExtra(USER_EXTRA);

        /* Controllo che l'oggetto passato sia un User. Se non lo è lo istanzio*/
        if(obj instanceof User)
            user = (User) obj;
        else
            user = new User();

        updateProfile();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // astrazione che predice l'intenzione (activity attuale , activity di destinazione)
                Intent goToLogin = new Intent(HomeActivityUser.this, LoginActivity.class);

                startActivity(goToLogin);
                HomeActivityUser.this.finish();
            }
        });

        goToChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // astrazione che predice l'intenzione (activity attuale , activity di destinazione)
                Intent goToChangePasswordActivity = new Intent(HomeActivityUser.this, UserPasswordChangerActivity.class);

                goToChangePasswordActivity.putExtra(USER_EXTRA, user);
                startActivity(goToChangePasswordActivity);
                HomeActivityUser.this.finish();
            }
        });

        // quando un utente normale clicca su "gestisci utenti" gli viene mostrato un messaggio
        // di errore e non può accedere alla pagina
        goToManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // messaggio pop up di divieto di accesso
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivityUser.this);

                // se impostato su true, si può chiudere il dialog cliccando in un punto
                // qualsiasi dello schermo
                builder.setCancelable(false);

                builder.setTitle("Errore!");
                builder.setMessage("Non hai i privilegi necessari per accedere a questa pagina!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                // Cambio coloredi background del dialog
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                    }
                });
                dialog.show();
            }
        });
    }

    protected void updateProfile(){

        int numberOfUsers = users.size();

        for (int i = 0; i < numberOfUsers; i++){
            if(user.getUsername().equals(users.get(i).getUsername()) &&
                    user.getPassword().equals(users.get(i).getPassword())){

                homeUsername.setText("Benvenuto " + users.get(i).getUsername() + "!");
                homeCity.setText(users.get(i).getCity());
                homeBirthDate.setText(users.get(i).getBirthDate());
                homePassword.setText(users.get(i).getPassword());

                // Imposta la corretta immagine del profilo
                switch (users.get(i).getImgPath()){
                    case "@drawable/pic1":
                        userProfilePic.setImageResource(R.drawable.pic1);
                        break;
                    case "@drawable/pic2":
                        userProfilePic.setImageResource(R.drawable.pic2);
                        break;
                    case "@drawable/pic3":
                        userProfilePic.setImageResource(R.drawable.pic3);
                        break;
                    case "@drawable/pic4":
                        userProfilePic.setImageResource(R.drawable.pic4);
                        break;
                    case "@drawable/pic5":
                        userProfilePic.setImageResource(R.drawable.pic5);
                        break;
                    case "@drawable/pic6":
                        userProfilePic.setImageResource(R.drawable.pic6);
                        break;
                    default: break;
                }
            }
        }
    }

    // disattivo pulsante indietro
    @Override
    public void onBackPressed() {}
}