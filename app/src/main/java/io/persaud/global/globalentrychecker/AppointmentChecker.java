package io.persaud.global.globalentrychecker;

import com.google.gson.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;

public class AppointmentChecker {

    private static String GOES_URL = "https://ttp.cbp.dhs.gov/schedulerapi/slots?orderBy=soonest&limit=3&locationId=LOCATIONID&minimum=1";
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        String url = createURL("5029");
        System.out.println(getDates(url));
    }

    public static String getDates(String newURL) throws IOException
    {

        String date = "appointments:";
        URL url = new URL(newURL);

        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

        if (root instanceof JsonObject) {
            System.out.println("JsonObject");
            JsonObject jobject = root.getAsJsonObject();
            date = jobject.get("startTimestamp").getAsString();
        } else if (root instanceof JsonArray) {
            JsonArray jarray = root.getAsJsonArray();
            JsonObject temp = jarray.get(0).getAsJsonObject();
            date = temp.get("startTimestamp").toString();
        }

        return date;

    }

    public static String createURL(String locationNumber)
    {
        String newURL = GOES_URL.replaceAll("LOCATIONID", locationNumber);
        System.out.println(newURL);
        return newURL;
    }

}
