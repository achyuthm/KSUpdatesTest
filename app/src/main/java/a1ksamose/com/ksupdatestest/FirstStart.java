package a1ksamose.com.ksupdatestest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Achyuth on 15-Mar-15.
 */
public class FirstStart
{
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public FirstStart(Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences("FIRST_START_DETECT", Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean isFirstLaunch()
    {
        return preferences.getBoolean("IS_FIRST_START", true);
    }

    public void setFirstLaunchFalse()
    {
        editor.putBoolean("IS_FIRST_START",false);
    }
}
