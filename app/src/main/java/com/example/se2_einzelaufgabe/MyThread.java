package com.example.se2_einzelaufgabe;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyThread extends Thread{

    private final EditText text_input;
    private final TextView text_output;
    private final int method;

    public MyThread(EditText text_input, TextView text_output, int method) {
        this.text_input = text_input;
        this.text_output = text_output;
        this.method = method;
    }

    @Override
    public void run(){
        switch (method){
            case 1:
                send();
                break;
            case 2:
                calc();
                break;
            default:
                Log.d("Wrong_Method", "You gave the wrong method!!!");
                break;
        }
    }

    private void send(){
        try {
            String input = text_input.getText().toString();
            StringBuilder builder = new StringBuilder();
            builder.append(input);
            builder.append("\n\r");
            input = builder.toString();

            Socket socket = new Socket("se2-submission.aau.at", 20080);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(input);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String messageRecieved = in.readLine();
            text_output.setText("Server hat geantwortet:\n"+messageRecieved);


            in.close();
            out.close();
            socket.close();
        }catch (Exception e){
            Log.d("Shit hit the fan", e.toString());
        }
    }

    private void calc(){
        // Matrikelnummer 12212707%7=3
        String temp = text_input.getText().toString();
        char[] input = temp.toCharArray();
        //

    }
}
