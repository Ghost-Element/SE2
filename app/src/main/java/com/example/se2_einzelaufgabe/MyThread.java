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

    public MyThread(EditText text_input, TextView text_output) {
        this.text_input = text_input;
        this.text_output = text_output;
    }

    @Override
    public void run(){
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
}
