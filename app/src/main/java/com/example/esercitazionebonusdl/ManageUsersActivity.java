package com.example.esercitazionebonusdl;

import static com.example.esercitazionebonusdl.LoginActivity.USER_EXTRA;
import static com.example.esercitazionebonusdl.User.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import java.io.Serializable;
import java.util.ArrayList;

public class ManageUsersActivity extends AppCompatActivity {

    private User user;
    private Button goBackHomeButton;
    private RecyclerView recyclerView;
    private TextInputEditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        goBackHomeButton = findViewById(R.id.goBackHomeButton);
        recyclerView = findViewById(R.id.recyclerView);
        searchBar = findViewById(R.id.searchBar);

        // Prendo tutti gli intent passati a questa activity
        Intent intentGetter = getIntent();

        Serializable obj = intentGetter.getSerializableExtra(USER_EXTRA);

        // Controllo che l'oggetto passato sia un User. Se non lo è lo istanzio
        if(obj instanceof User)
            user = (User) obj;
        else
            user = new User();

        // Inizialmente mostro tutti gli utenti registrati,
        // Tranne l'utente che è attualmente loggato
        setAdapter(removeLoggedUser(users));

        // Modifico layout in base a ricerca utente
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ArrayList<User> newList = removeLoggedUser(users);
                ArrayList<User> correctList = new ArrayList<>();

                for(int c = 0; c < newList.size(); c++) {

                    if(newList.get(c).getUsername().startsWith(searchBar.getText().toString()))
                        correctList.add(newList.get(c));
                }

                // Mostro solo gli utenti che combaciano con i criteri di ricerca
                setAdapter(correctList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Torna alla home dell'utente
        goBackHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // astrazione che predice l'intenzione (activity attuale , activity di destinazione)
                Intent returnToHomePage = new Intent(ManageUsersActivity.this, HomeActivityAdmin.class);

                returnToHomePage.putExtra(USER_EXTRA, user);

                startActivity(returnToHomePage);
                ManageUsersActivity.this.finish();
            }
        });
    }

    // Metodo che elimina dalla visualizzazione l'utente loggato
    protected ArrayList<User> removeLoggedUser(ArrayList<User> usersList){

        ArrayList<User> newList = new ArrayList<>();

        for (int i = 0; i < usersList.size(); i++){

            if(!(usersList.get(i).getUsername().equals(user.getUsername())))
                newList.add(usersList.get(i));
        }

        return newList;
    }

    private void setAdapter(ArrayList<User> usersList) {

        recyclerAdapter adapter = new recyclerAdapter(usersList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {}
}