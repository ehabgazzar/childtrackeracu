package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Completeinfo extends AppCompatActivity {
    EditText name,mob,email;
    Button b1;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    RadioGroup radioGenderGroup;
    RadioButton radioGenderButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completeinfo);
        name= (EditText) findViewById(R.id.name_sign_up);
        mob = (EditText) findViewById(R.id.mob_sign_up);
        radioGenderGroup = (RadioGroup) findViewById(R.id.gender_sign_up);

        b1= (Button) findViewById(R.id.button);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dosignup();
            }
        });
    }


    private void createUser(String name, String mob,String gender) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth

            userId = mFirebaseDatabase.push().getKey();


        User user = new User(name,mob,gender);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    void dosignup()
    {
        int selectedId=radioGenderGroup.getCheckedRadioButtonId();
        radioGenderButton=(RadioButton)findViewById(selectedId);

        createUser(name.getText().toString(),mob.getText().toString(),radioGenderButton.getText().toString());
        startActivity(new Intent(Completeinfo.this, MainActivity.class));
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e("TAG", "User data is null!");
                    return;
                }

                Log.e("TAG", "User data is changed!" + user.name + ", " + user.email);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read user", error.toException());
            }
        });
    }
}
