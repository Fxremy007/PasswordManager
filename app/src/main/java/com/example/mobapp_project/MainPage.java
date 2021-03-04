package com.example.mobapp_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GetUser";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    ListView listView;

    private List<String> websitesList = new ArrayList<>();

    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        this.getUser();
        FirebaseUser user = mAuth.getCurrentUser();
        idUser = user.getUid();
        
        listView = (ListView)findViewById(R.id.listView);

        CollectionReference websitesCollection = db.collection("Users").document("Website").collection(idUser);
        websitesCollection.orderBy("Name");


        /*websitesCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //QueryDocumentSnapshot document = null;
                            //while (document.exists()) {
                                Log.d(TAG, "Test passage");
                            String name = "";
                            ArrayList<String> arrayList2 = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Log.d(TAG, String.valueOf(document.get("Name")));
                                    name = String.valueOf(document.get("Name"));
                                    TextView test = findViewById(R.id.textView2);
                                    test.setText(name);
                                }
                            arrayList2.add(name);
                            TextView test = findViewById(R.id.textView2);
                            test.setText((CharSequence) arrayList2);
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        //listView.setAdapter(arrayAdapter);
        */

    websitesCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            websitesList.clear();

            for(DocumentSnapshot snapshot : value) {
                websitesList.add(snapshot.getString("Name"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, websitesList);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    });

    //arrayList.add("teeest après blabla");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocumentReference websiteDocument = db.collection("Users").document("Website").collection(idUser).document(listView.getItemAtPosition(position).toString());
                websiteDocument
                        //.whereEqualTo("Name", listView.getItemAtPosition(position).toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Toast.makeText(MainPage.this, listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }

    private void getUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "User connected");
            // Name, email address
            String name = user.getDisplayName();

            TextView nameUser = findViewById(R.id.nameUser);
            nameUser.setText(name);
        } else {
            Log.d(TAG, "User not connected");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addWebsite:
                Intent i = new Intent(this, CreateWebsite.class);
                startActivity(i);
                break;
            case R.id.btn_logout:
                mAuth.signOut();
                Intent i_MainActivity = new Intent(this, MainActivity.class);
                startActivity(i_MainActivity);
                break;
        }
    }
}