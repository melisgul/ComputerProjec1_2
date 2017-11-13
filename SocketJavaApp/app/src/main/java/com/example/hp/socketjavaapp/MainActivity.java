package com.example.hp.socketjavaapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements Serializable{
    private static final long serialVersionUID = 7526472295622776147L;
    EditText usernameMessage;
    Button sendButton;
    private static Socket s;
    private static PrintWriter printWriter;
    static TreeMap<Character, String> pair = new TreeMap<>();
    static PriorityQueue<HuffmanObject> nodeQueue = new PriorityQueue<>(1000,
            new Comparator<HuffmanObject>(){
        public int compare(HuffmanObject a, HuffmanObject b){
            if (a.frequency > b.frequency) return 1;
            else return -1;
        }
    });
    private static ObjectOutputStream os;
    static String input = "";
    static String encoded = "";
    static int asciiVariables[] = new int[128];
    String message = "";
    private static String ip = "192.168.1.105";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameMessage = (EditText) findViewById(R.id.sendName);
    }

    public void sendText(View v){
        message = usernameMessage.getText().toString();
        huffmanEncode();
        myTask mt = new myTask();
        mt.execute();
        Toast.makeText(getApplicationContext(),"Encoded Data sent",Toast.LENGTH_LONG).show();
    }



    private void huffmanEncode() {
        input = usernameMessage.getText().toString();
        asciiVariables = new int[128];
        nodeQueue.clear();
        pair.clear();
        encoded = "";
        rateCalculation(nodeQueue);
        binaryCalculation(nodeQueue.peek(), "");
        encodeText();

    }

    private void encodeText() {
        encoded = "";
        for (int i = 0; i < input.length(); i++)
            encoded += pair.get(input.charAt(i));
    }


    private void rateCalculation(PriorityQueue<HuffmanObject> vector) {
        for (int i = 0; i < input.length(); i++)
            asciiVariables[input.charAt(i)]++;

        for (int i = 0; i < asciiVariables.length; i++)
            if (asciiVariables[i] > 0) {
                vector.add(new HuffmanObject(asciiVariables[i] / (input.length() * 1.0), ((char) i) + ""));
            }

        //creating new node with two lower frequency nodes and build whole tree.

        while (vector.size() >= 2){
            HuffmanObject leftObject = vector.poll();
            HuffmanObject rightObject = vector.poll();
            vector.add(new HuffmanObject(leftObject, rightObject));
        }
    }

    private void binaryCalculation(HuffmanObject object, String str) {
        if (object != null) {
            if (object.left != null)
                binaryCalculation(object.left, str + "1");

            if (object.right != null)
                binaryCalculation(object.right, str + "0");

            if ((object.left == null) && (object.right == null))
                pair.put(object.character.charAt(0), str);
        }
    }





    class myTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                s = new Socket(ip,12002);
                System.out.println(nodeQueue.peek());

                System.out.println(encoded);
                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.write(encoded);
                printWriter.flush();
                printWriter.close();
              /*  os = new ObjectOutputStream(s.getOutputStream());
                os.writeObject(nodeQueue.peek());
                os.flush();*/

               // os.close();
                s.close();


            }catch (IOException e){
                e.printStackTrace();
            }



            return null;
        }
    }

}


