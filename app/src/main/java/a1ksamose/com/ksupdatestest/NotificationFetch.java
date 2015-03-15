package a1ksamose.com.ksupdatestest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Achyuth on 15-Mar-15.
 */
public class NotificationFetch extends BroadcastReceiver
{
    public Context context;
    public SharedPreferences storedAnnouncements;
    public SharedPreferences.Editor editor;
    JSONArray jsonArray;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        if(isNetworkAvailable())
        {
            String countUrl = "http://ksupdates.herokuapp.com/api/count";
            HttpRequestTask httpCountRequestTask = new HttpRequestTask();
            httpCountRequestTask.execute(countUrl);
        }

        storedAnnouncements = context.getSharedPreferences("ANNOUNCEMENT_COUNT", Activity.MODE_PRIVATE);
        editor=storedAnnouncements.edit();


//        int totalAnnouncements = Integer.parseInt(HttpRequestTask.getCurrentCount());
//        int curStoredAnnouncements = storedAnnouncements.getInt("STORED_ANNOUNCEMENTS",0);
//        int remAnnouncements = totalAnnouncements-curStoredAnnouncements;
//        editor.putInt("STORED_ANNOUNCEMENTS",curStoredAnnouncements+remAnnouncements).commit();
        String announcementUrl = "http://ksupdates.herokuapp.com/api/announcements/4";
        HttpAnnouncementRequest announcementRequest = new HttpAnnouncementRequest();
        announcementRequest.execute(announcementUrl);


        jsonArray = HttpAnnouncementRequest.getJSONArray();

        if(jsonArray!=null)
        {
            try
            {
                for (int i=0; i<jsonArray.length(); i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Log.d("CLUSTER",object.getString("cluster"));
                    Log.d("ANNOUNCEMENT",object.getString("announcement"));
                    Log.d("TIME",object.getString("time"));
                }
            }
            catch (Exception e){e.printStackTrace();}
        }
        else
        {
            Log.d("EMPTY","Empty JSON ARRAY");
        }

    }

    public boolean isNetworkAvailable()
    {
        boolean isConnected = false;
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo!=null)
        {
            isConnected = true;
        }
        else
        {
            isConnected = false;
        }
        return isConnected;
    }

}

class HttpRequestTask extends AsyncTask<String, Void, String>
{
    static String countResult="";

    @Override
    protected String doInBackground(String... url)
    {
        String count="";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGetRequest = new HttpGet(url[0]);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try
        {
            count = httpClient.execute(httpGetRequest, responseHandler);
        }catch (Exception e)
        {
            e.getStackTrace();
        }

        httpClient.getConnectionManager().shutdown();

        return count;
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(result);
            countResult = jsonObject.getString("count");
            Log.d("COUNT_VALUE",countResult);
        }
        catch (Exception e){e.printStackTrace();}
    }

    public static String getCurrentCount()
    {
        return countResult;
    }

}

class HttpAnnouncementRequest extends AsyncTask<String, Void, String>
{
    static JSONArray announcementsJsonArray;

    @Override
    protected String doInBackground(String... url) {

        String announcementDetails="";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGetRequest = new HttpGet(url[0]);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try
        {
            announcementDetails = httpClient.execute(httpGetRequest, responseHandler);
        }
        catch (Exception e){e.printStackTrace();}

        return announcementDetails;
    }

    @Override
    protected void onPostExecute(String result)
    {
        try
        {
            JSONObject object = new JSONObject(result);
            announcementsJsonArray = object.getJSONArray("details");
        }catch (Exception e){e.printStackTrace();}
    }


    public static JSONArray getJSONArray()
    {
        return announcementsJsonArray;
    }
}