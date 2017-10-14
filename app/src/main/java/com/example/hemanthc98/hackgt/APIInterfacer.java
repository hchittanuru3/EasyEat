package com.example.hemanthc98.hackgt;

/**
 * Created by Matt on 10/14/2017.
 */

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Will be used as a facade to call HTTP Requests/Posts from the Model;
 * Layer of abstraction from controller
 */
public class APIInterfacer {

    final static String baseURL = "/";

    public static void updatePreferences(String username, String bStart, String bEnd, String lStart,
                                         String lEnd, String dStart, String dEnd) {
        String json = String.format("{'preferences': {'breakfast': { 'startTime': %s, 'endTime': %s}, 'lunch' : {'startTime': %s, 'endTime': %s}," +
                "'dinner' : {'startTime': %s, 'endTime': %s}}}", bStart, bEnd, lStart, lEnd, dStart, dEnd);
        try {
            JSONObject preferenceJSON = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //HTTP.makePost(username, preferencesJSON)

    }

    public static void addScheduleActivity(String username, String date, String activityName, String startTime,
                                           String endTime, String location) {
        String json = String.format("{'name': %s, 'startTime': %s, 'endTime': %s, 'location': %s}",
                activityName, startTime, endTime, location);
        try {
            JSONObject activityJSON = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //HTTP.makePost(username, date, activityJSON)
    }

    public static void addUser(String username, String password) {
        String json = String.format("{'name': %s, 'password': %s, 'schedules': []}", username, password);
        try {
            JSONObject userJSON = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //HTTP.makePost(userJSON)

    }

    //GET request to /:username/:date
    // Re
    public static JSONObject getMealIntervals(String username, String date) {}

    public static boolean authenticateUser(String username, String password) {
        String urlString = baseURL + "username=" + username + "/" + "password=" + password;
        try {
            JSONObject json = new GetMethod().execute(baseURL).get();
            String res = json.getString("message");
            if (res.equals("Authenticated")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class GetMethod extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                String urlString = strings[0];
                HttpURLConnection urlConnection = null;
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                String jsonString = sb.toString();
                System.out.println("JSON: " + jsonString);

                return new JSONObject(jsonString)
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
