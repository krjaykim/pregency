package com.example.pregency.dayonddosil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pregency.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Chatmain extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "Chatmain";

    private RecyclerView mRecyclerView;
    public  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    static String nick1;
//    private String nick = "nick1"; // ID 전달 받아서 넣기

    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference myRef;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String findMachineName( String uid ) {
        CollectionReference colRef = db.collection("machines").document("iot1").collection(uid);
        if( colRef != null ) {
            //uid 콜렉션이 존재함.
            return "iot1";
        }

        colRef = db.collection("machines").document("iot2").collection(uid);
        if( colRef != null ) {
            //uid 콜렉션이 존재함.
            return "iot2";
        }

        return "";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);

        firebaseAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = dateFormat.format(date);
        Button_send = findViewById(R.id.Button_send);
        EditText_chat = findViewById(R.id.EditText_chat);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        String machineName = findMachineName( uid );

        db.collection("machines").document( machineName ).collection(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                nick1 = (String) document.getData().get("nick");
                                Log.d(TAG, "where"+nick1);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, Chatmain.this, nick1);

        mRecyclerView.setAdapter(mAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = EditText_chat.getText().toString(); //msg

                if(msg != null) {
                    ChatData chat = new ChatData();
                    chat.setNickname(getTime+"\n"+nick1+"\n");
                    chat.setMsg(msg);
                    myRef.push().setValue(chat);
                    EditText_chat.setText(null);
                    mRecyclerView.scrollToPosition(chatList.size()-1);
                }

            }
        });
        //caution!!!

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                ChatData chat = dataSnapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}