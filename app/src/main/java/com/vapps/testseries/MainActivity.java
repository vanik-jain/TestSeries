package com.vapps.testseries;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 123;


    private  FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();


    private  DatabaseReference mDatabase =firebaseDatabase.getReference();


    int score=0;

    int total=0;



    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button go;




    TextView questionTextView;


    TextView timerTextView;


    TextView messageTextView2;


    GridLayout gridLayout;







    public void appStart(View view)
    {



        questionTextView.setVisibility(View.VISIBLE);

        gridLayout.setVisibility(View.VISIBLE);

        timerTextView.setVisibility(View.VISIBLE);



        go.setVisibility(View.INVISIBLE);

        total=0;

        score=0;

        timerMethod();

        questionCreator();


    }



    public void questionCreator()

    {



        mDatabase.child("quiz/maths/q"+(total+1)+"/question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String question = dataSnapshot.getValue(String.class);
                questionTextView.setText(question);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("quiz/maths/q"+(total+1)+"/options/0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String answer = dataSnapshot.getValue(String.class);
                button0.setText(answer);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mDatabase.child("quiz/maths/q"+(total+1)+"/options/1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String answer = dataSnapshot.getValue(String.class);
                button1.setText(answer);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mDatabase.child("quiz/maths/q"+(total+1)+"/options/2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String answer = dataSnapshot.getValue(String.class);
                button2.setText(answer);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mDatabase.child("quiz/maths/q"+(total+1)+"/options/3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String answer = dataSnapshot.getValue(String.class);
                button3.setText(answer);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        ++total;


    }




    public void answerClicked(View view)
    {


        String Tag = view.getTag().toString();
        String userAnswer = "";

        if(Tag.equals("0"))
        {
            userAnswer = button0.getText().toString();

        }

        else if(Tag.equals("1"))
        {
            userAnswer = button1.getText().toString();

        }

        else if(Tag.equals("2"))
        {
            userAnswer = button2.getText().toString();

        }

        else if (Tag.equals("3"))

        {
            userAnswer = button3.getText().toString();

        }

        Log.i("META",userAnswer);




        final String userAnswerFinal = userAnswer;


        Log.i("META7", Integer.toString(total));

        mDatabase.child("quiz/maths/q"+total+"/answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String correctAnswer = dataSnapshot.getValue(String.class);
                Log.i("META%",correctAnswer);

                Log.i("META6",userAnswerFinal);

                if(userAnswerFinal.equals (correctAnswer))

                {
                    ++score;

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        questionCreator();


    }





    public void timerMethod()
    {

        new CountDownTimer(30000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {

                timerTextView.setText("0"+":"+String.format("%02d",((int)millisUntilFinished)/1000));
            }

            @Override
            public void onFinish() {
                messageTextView2.setText("Your Score:\n"+"   "+ Integer.toString(score)+"/"+Integer.toString(total));




                questionTextView.setVisibility(View.INVISIBLE);

                gridLayout.setVisibility(View.INVISIBLE);

                timerTextView.setVisibility(View.INVISIBLE);






                messageTextView2.setVisibility(View.VISIBLE);


                new CountDownTimer(2000,1000)
                {

                    @Override
                    public void onTick(long millisUntilFinished)
                    {

                    }

                    @Override
                    public void onFinish() {

                        go.setText("RETEST!");

                        go.setTextSize(40f);

                        go.setPadding(30,30,30,30);

                        go.setVisibility(View.VISIBLE);

                        messageTextView2.setVisibility(View.INVISIBLE);




                    }
                }.start();

            }



        }.start();


    }











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

if(user == null) {
    List<AuthUI.IdpConfig> providers = Arrays.asList(

            new AuthUI.IdpConfig.PhoneBuilder().build()

    );


    startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN);


}
        button0=(Button)findViewById(R.id.button0);

        button1=(Button)findViewById(R.id.button1);

        button2=(Button)findViewById(R.id.button2);

        button3=(Button)findViewById(R.id.button3);

        go=(Button)findViewById(R.id.appStart);




        questionTextView=(TextView)findViewById(R.id.questionTextView);

        messageTextView2 = findViewById(R.id.messageTextView2);




        timerTextView=(TextView)findViewById(R.id.timerTextView);


        gridLayout = (GridLayout)findViewById(R.id.grid);





    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
                // Successfully signed in




                // ...
            } else {


            }
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.afterlogin,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.SignOut)
        {



            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {


                            Toast.makeText(getApplicationContext(),"SigningOut!",Toast.LENGTH_LONG).show();

                        }
                    });


        }



        return super.onOptionsItemSelected(item);
    }











}
