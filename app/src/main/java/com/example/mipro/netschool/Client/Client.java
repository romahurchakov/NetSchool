package com.example.mipro.netschool.Client;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;



public class Client extends AsyncTask<String, Void, String> {

    String dstAddress;
    int dstPort;
    String response = "";
    private static final String URLserv = "https://www.netschool.app:8000/get_school_list";

    public Client(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... arg0) {
        if (arg0[0].equals("set")) {
            try {
                URL obj = null;
                obj = new URL(URLserv);
                HttpURLConnection con = null;
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = 0;
                //responseCode = con.getResponseCode();
                BufferedReader in = null;
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while (in != null && (inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());

                Gson gson = new Gson();
                Schools schools = gson.fromJson(response.toString(), Schools.class);

                /* System.out.println(schools.schools.get(0).name);
                System.out.println(schools.schools.get(0).id);
                System.out.println(schools.schools.get(0).website);
                System.out.println(schools.schools.get(0).shortcut);*/

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }


}

class Schools {
    List<School> schools;

    class School {
        public int id;
        public String name;
        public String website;
        public String shortcut;
    }

}

