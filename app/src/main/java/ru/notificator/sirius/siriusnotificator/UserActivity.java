package ru.notificator.sirius.siriusnotificator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class UserActivity extends AppCompatActivity {
    public static final String save = "save";

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private View sho;

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    public void loadBring() {
        SharedPreferences sp = getSharedPreferences(save, Context.MODE_PRIVATE);
        DateFormat format = new SimpleDateFormat("dd:MM:yyyy kk:mm:ss", Locale.ENGLISH);
        Date cur = Calendar.getInstance().getTime();
        String txt = sp.getString("time", format.format(cur));
        Date date = null;
        try {
            date = format.parse(txt);
        } catch (ParseException e) {
            Log.e("Parse", e.getMessage());
        }
        int second = (int) ((date.getTime() - cur.getTime()) / 1000);
        int hour = second / 3600;
        second %= 3600;
        int minute = second / 60;
        second %= 60;
        ((TextView) findViewById(R.id.timeTo)).setText("" +
                hour / 10 + hour % 10 + ":" +
                minute / 10 + minute % 10 + ":" +
                second / 10 + second % 10);
        ((TextView) findViewById(R.id.placeTo)).setText(sp.getString("place", "Никуда..."));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        SharedPreferences sp = getSharedPreferences(save, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("time", "22:01:2019 17:30:30");
        editor.putString("place", "Столовую");
        editor.apply();
        loadBring();

        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 1000, alarmIntent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_show_code:
                showc();
        }
        return false;
    }
    public void showc(){
        startActivity(new Intent(UserActivity.this,ShowCode.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);

        return true;
    }
}
