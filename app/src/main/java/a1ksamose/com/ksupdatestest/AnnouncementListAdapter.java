package a1ksamose.com.ksupdatestest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

/**
 * Created by Achyuth on 15-Mar-15.
 */
public class AnnouncementListAdapter extends BaseAdapter
{
    private Context context;

    public AnnouncementListAdapter(Context context)
    {
        this.context = context;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
