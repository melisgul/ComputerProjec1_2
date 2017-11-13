package com.example.hp.socketjavaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class UserActivity extends AppCompatActivity {
    EditText username;
    Button enterButton;
    private static Socket s;
    private static PrintWriter printWriter;
    private static String ip = "192.168.1.105";
    private static String name = "";
    private static InetAddress hostIP;
    private static String hostIPString;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        username = (EditText) findViewById(R.id.userText);

        enterButton = (Button) findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(username.getText().toString());
            }
        });
    }

    private void register(String username){
        name = username;
        UserActivity.myTask mt = new UserActivity.myTask();
        mt.execute();
        Intent intent = new Intent(UserActivity.this,MainActivity.class);
        startActivity(intent);
    }

    class myTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                s = new Socket(ip,12002);
                hostIP = InetAddress.getLocalHost();
                hostIPString = hostIP.getHostAddress() ;
                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.write(name);
                printWriter.flush();
                printWriter.close();
                s.close();


            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }


}
