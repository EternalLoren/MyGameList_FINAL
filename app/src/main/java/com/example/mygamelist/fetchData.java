package com.example.mygamelist;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data = "";
    public static ArrayList<String> arrText = new ArrayList<>(100);
    public static ArrayList<String> arrPic = new ArrayList<>(100);
    public static ArrayList<String> arrName = new ArrayList<>(100);
    public static boolean done = false;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://raw.githubusercontent.com/EternalLoren/MyGameList/master/gamelist.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i = 1; i < JA.length(); i++) {
                JSONObject JO = (JSONObject) JA.get(i);
                arrPic.add("https://raw.githubusercontent.com/EternalLoren/MyGameList/master/" + JO.get("graphic"));
                arrName.add("" + JO.get("name"));
                try {
                    arrText.add("" + JO.get("helptext"));
                }
                catch (JSONException e) {
                    arrText.add("No Helptext");
                }
            }
            done = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
