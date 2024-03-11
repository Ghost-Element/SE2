package com.example.se2_einzelaufgabe;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

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

    private void calc() {
        // Matrikelnummer 12212707%7=3
        String temp = text_input.getText().toString();
        if (temp.isEmpty()) {
            text_output.setText("Die Eingabe ist leer!");
        } else {
            char[] input = temp.toCharArray();
            int counter = 0;
            char[] digits = new char[10];
            for (int i = 0; i < 10; i++) {
                if (i < 5) {
                    digits[i] = (char) ((i * 2) + 48);
                } else {
                    digits[i] = (char) (((i - 5) * 2 + 1) + 48);
                }
            }
            for (int i = 0; i < 10; i++) {
                counter = check_next_digit(counter, input, digits[i]);
                if (counter == -1) {
                    break;
                }
            }
            StringBuilder builder = new StringBuilder();
            for(char digit : input){
                builder.append(digit);
            }
            text_output.setText("After the calculation your Matrikelnr looks like this: " + builder.toString());
        }
    }

    private int check_next_digit(int counter, char[] input, char digit){
        if(counter==input.length){
            return -1;
        }
        if(input[counter]==digit){
            counter++;
        }
        for(int i=counter;i<input.length;i++){
            if(input[i]==digit){
                // swap
                char temp = input[counter];
                input[counter]=input[i];
                input[i]=temp;
                counter++;
            }
        }
        return counter;
    }
}
