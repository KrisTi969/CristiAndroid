package com.example.crys_.makeandroidgreatagain;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import database.myDbAdapter;

public class MainActivity extends AppCompatActivity {
    EditText Name, Pass, updateold, updatenew, delete;
    myDbAdapter adapter;
    Button view_all_button;
    Button server_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("CORNFLIX")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentText("Check some statistics !")
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);


        Intent resultIntent = new Intent(this, BarChartActivity.class);
// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

/////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText) findViewById(R.id.editName);
        Pass = (EditText) findViewById(R.id.editPass);
        updateold = (EditText) findViewById(R.id.editText3);
        updatenew = (EditText) findViewById(R.id.editText5);
        delete = (EditText) findViewById(R.id.editText6);

        view_all_button = (Button) findViewById(R.id.button5);
        view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewUsers.class));
            }
        });

        server_button = (Button) findViewById(R.id.buttonServer);
        server_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewRecommandations.class));

            }
        });

        adapter = new myDbAdapter(this);
    }

    public void addUser(View view)
    {
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        if(t1.isEmpty() || t2.isEmpty()) {
            ToastMessage.message(getApplicationContext(),"Introduceti atat filmul, cat si data !");

        }
        else {
            long id = adapter.insertData(t1,t2);
            if(id<=0) {
                ToastMessage.message(getApplicationContext(), "Inserarea nu s-a putut face");
                Name.setText("");
                Pass.setText("");
            }
            else
            {
                ToastMessage.message(getApplicationContext(),"Inserare cu succes");
                Name.setText("");
                Pass.setText("");
            }
        }
    }

    public void viewdata(View view) {
        String data = adapter.getData();
        ToastMessage.message(this,data);
    }

    public void update(View view)
    {
        String u1 = updateold.getText().toString();
        String u2 = updatenew.getText().toString();
        if(u1.isEmpty() || u2.isEmpty())
        {
            ToastMessage.message(getApplicationContext(),"Enter Data");
        }
        else
        {
            int a= adapter.updateName( u1, u2);
            if(a<=0)
            {
                ToastMessage.message(getApplicationContext(),"Unsuccessful");
                updateold.setText("");
                updatenew.setText("");
            } else {
                ToastMessage.message(getApplicationContext(),"Updated");
                updateold.setText("");
                updatenew.setText("");
            }
        }
    }

    public void delete( View view)
    {
        String uname = delete.getText().toString();
        if(uname.isEmpty())
        {
            ToastMessage.message(getApplicationContext(),"Enter Data");
        }
        else{
            int a= adapter.delete(uname);
            if(a<=0)
            {
                ToastMessage.message(getApplicationContext(),"Unsuccessful");
                delete.setText("");
            }
            else
            {
                ToastMessage.message(this, "DELETED");
                delete.setText("");
            }
        }
    }



}
