package com.example.freephoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FreeList extends AppCompatActivity {
    List<String> list;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_list);
        try {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection("userlist").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
                        }
                        //list.remove("sharath");
                    } else {
                        Toast.makeText(FreeList.this, "You don't have any friends in list ...", Toast.LENGTH_SHORT).show();
                    }
                    for (String userName : list) {
                        addFriendToList(userName);
                     }
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addFriendToList(String userName){
             try {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.friend_list, null);
                Button friendBtn = rowView.findViewById(R.id.friendbtn);
                LinearLayout friendListView=findViewById(R.id.freelist);
                friendListView.addView(rowView, friendListView.getChildCount());
                friendBtn.setText(userName);
                 rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // showAlertDialogForAddTravelDetail("User Details",email.getText().toString());
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}