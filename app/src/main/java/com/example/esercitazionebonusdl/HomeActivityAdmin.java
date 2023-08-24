package com.example.esercitazionebonusdl;

import static com.example.esercitazionebonusdl.LoginActivity.USER_EXTRA;
import static com.example.esercitazionebonusdl.User.users;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.Serializable;

public class HomeActivityAdmin extends AppCompatActivity {

    private TextView homeCity, homeBirthDate, homeUsername, homePassword;
    private Button logoutButton;
    private LinearLayout goToManageUsers, goToChangePassword;
    private User user;
    private ImageView adminProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        homeCity = findViewById(R.id.homeCity);
        homeBirthDate = findViewById(R.id.homeBirthDate);
        homeUsername = findViewById(R.id.homeUsername);
        homePassword = findViewById(R.id.homePassword);
        logoutButton = findViewById(R.id.logoutButton);
        goToChangePassword = findViewById(R.id.goToChangePassword);
        goToManageUsers = findViewById(R.id.goToManageUsers);
        adminProfilePic = findViewById(R.id.adminProfileImage);

        /* Prendo tutti gli intent passati a questa activity*/
        Intent intentGetter = getIntent();

        Serializable obj = intentGetter.getSerializableExtra(LoginActivity.USER_EXTRA);

        /* Controllo che l'oggetto passato sia un User. Se non lo è lo istanzio*/
        if(obj instanceof User)
            user = (User) obj;
        else
            user = new User();

        updateProfile();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* astrazione che predice l'intenzione (activity attuale , activity di destinazione)*/
                Intent goToLogin = new Intent(HomeActivityAdmin.this, LoginActivity.class);

                startActivity(goToLogin);
                HomeActivityAdmin.this.finish();
            }
        });

        goToChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // se l'utente è "admin" non può cambiare la password, altrimenti si
                if(user.getUsername().equals("admin")){

                    // messaggio pop up di divieto di accesso
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivityAdmin.this);

                    // se impostato su true, si può chiudere il dialog cliccando in un punto
                    // qualsiasi dello schermo
                    builder.setCancelable(false);

                    builder.setTitle("Errore!");
                    builder.setMessage("L'utente amministratore \"admin\" non può cambiare la password!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}
                    });

                    // Cambio colore di background del dialog
                    AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        }
                    });
                    dialog.show();
                } else {

                    // astrazione che predice l'intenzione (activity attuale , activity di destinazione)
                    Intent goToChangePasswordActivity = new Intent(HomeActivityAdmin.this, AdminPasswordChangerActivity.class);

                    goToChangePasswordActivity.putExtra(USER_EXTRA, user);
                    startActivity(goToChangePasswordActivity);
                    HomeActivityAdmin.this.finish();

                }
            }
        });

        // avvio pagina gestione utente per gli admin
        goToManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // astrazione che predice l'intenzione (activity attuale , activity di destinazione)
                Intent goToManageUsers = new Intent(HomeActivityAdmin.this, ManageUsersActivity.class);

                goToManageUsers.putExtra(USER_EXTRA, user);
                startActivity(goToManageUsers);
                HomeActivityAdmin.this.finish();
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
                        adminProfilePic.setImageResource(R.drawable.pic1);
                        break;
                    case "@drawable/pic2":
                        adminProfilePic.setImageResource(R.drawable.pic2);
                        break;
                    case "@drawable/pic3":
                        adminProfilePic.setImageResource(R.drawable.pic3);
                        break;
                    case "@drawable/pic4":
                        adminProfilePic.setImageResource(R.drawable.pic4);
                        break;
                    case "@drawable/pic5":
                        adminProfilePic.setImageResource(R.drawable.pic5);
                        break;
                    case "@drawable/pic6":
                        adminProfilePic.setImageResource(R.drawable.pic6);
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