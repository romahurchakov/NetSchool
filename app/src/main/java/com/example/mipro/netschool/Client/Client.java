package com.example.mipro.netschool.Client;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class Client extends AsyncTask<String, Void, String> {

    String dstAddress;
    int dstPort;
    String response = "";

    public Client(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
    }

    @Override
    protected String doInBackground(String... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream= socket.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            int read;

           /* try{
                String ipAddress = "77.73.26.195";
                InetAddress inet = InetAddress.getByName(ipAddress);

                System.out.println("Sending Ping Request to " + ipAddress);
                System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
            } catch (Exception e){
                e.printStackTrace();
            }*/
/*
            if (arg0[0].equals("set")) {
                JSONObject message = new JSONObject();
                message.put("action", "sign_in");
                message.put("login","Kek");
                message.put("passkey", "asdasdasd");
                message.put("id", "3");
                out.write(message.toString());

                System.out.println(inputStream.read());
                System.out.println("kek");
            }*/

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        /*} catch (JSONException e) {
            e.printStackTrace();*/
        } finally {
            if (socket != null) {
                try {
                    System.out.println("close");
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }


    @Override
    protected void onPostExecute(String result) {
       //
        super.onPostExecute(result);
    }
}
