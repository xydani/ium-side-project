package com.example.esercitazionebonusdl;

import static com.example.esercitazionebonusdl.User.users;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<User> usersList;
    public recyclerAdapter(ArrayList<User> usersList){
        this.usersList = usersList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView text;
        private SwitchCompat adminSwitch;

        public MyViewHolder(final View view){
            super(view);
            image = view.findViewById(R.id.userProfilePic);
            text = view.findViewById(R.id.userName);
            adminSwitch = view.findViewById(R.id.adminSwitch);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {

        String image = usersList.get(position).getImgPath();

        // Imposta la corretta immagine del profilo
        switch (usersList.get(position).getImgPath()) {
            case "@drawable/pic1":
                holder.image.setImageResource(R.drawable.pic1);
                break;
            case "@drawable/pic2":
                holder.image.setImageResource(R.drawable.pic2);
                break;
            case "@drawable/pic3":
                holder.image.setImageResource(R.drawable.pic3);
                break;
            case "@drawable/pic4":
                holder.image.setImageResource(R.drawable.pic4);
                break;
            case "@drawable/pic5":
                holder.image.setImageResource(R.drawable.pic5);
                break;
            case "@drawable/pic6":
                holder.image.setImageResource(R.drawable.pic6);
                break;
            default:
                holder.image.setImageResource(R.drawable.adminiconv3);
        }

        String username = usersList.get(position).getUsername();
        holder.text.setText(username);

        String isAdmin = usersList.get(position).getIsAdmin();
        if(isAdmin.equals("true"))
            holder.adminSwitch.setChecked(true);
        else
            holder.adminSwitch.setChecked(false);

        if(username.equals("admin"))
            holder.adminSwitch.setVisibility(View.GONE);

        // Controlla la posizione dello switch e in base ad esso assegna o meno i
        // privilegi di amministrazione
        holder.adminSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    for(int i = 0; i < users.size(); i++){

                        if(users.get(i).getUsername().equals(username))
                            users.get(i).setIsAdmin("true");
                    }
                } else {
                    for (int i = 0; i < users.size(); i++) {

                        if (users.get(i).getUsername().equals(username))
                            users.get(i).setIsAdmin("false");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
