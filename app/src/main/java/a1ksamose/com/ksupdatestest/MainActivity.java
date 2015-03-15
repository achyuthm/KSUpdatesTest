package a1ksamose.com.ksupdatestest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity
{

    FirstStart firstStart;
    boolean firstLaunch;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstStart = new FirstStart(this);
        firstLaunch = firstStart.isFirstLaunch();
        if(firstLaunch)
        {
            calendar = Calendar.getInstance();
            Intent notificationFetchIntent = new Intent(MainActivity.this, NotificationFetch.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1010 , notificationFetchIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 120000, pendingIntent);
            firstStart.setFirstLaunchFalse();
            Toast.makeText(this, "First Launch Set", Toast.LENGTH_SHORT).show();
        }
    }


}
