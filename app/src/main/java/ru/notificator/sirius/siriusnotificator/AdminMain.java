package ru.notificator.sirius.siriusnotificator;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import ru.notificator.sirius.siriusnotificator.Recyclik.Adapter_recuc;
import ru.notificator.sirius.siriusnotificator.Recyclik.Boi;

public class AdminMain extends AppCompatActivity {
    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.admin_asks:
                    setAsksContent();
                    return true;
                case R.id.admin_brings:
                    setBringContent();
                    return true;
                case R.id.admin_children:
                    setMembersContent();
                    return true;
            }
            return false;
        }
    };
    private Adapter_recuc mAdapter;
    private View boi_but;
    private View sbor_create_but;

    public static List<Boi> boiz = new ArrayList<>();
    private SharedPreferences saver;

    private RecyclerView prok_uch;
    public static File file;

    public void setMembersContent() {
        final Activity at = this;
        if (sbor_create_but.getVisibility() == View.VISIBLE) {
            sbor_create_but.setVisibility(View.GONE);
        }
        if (boi_but.getVisibility() != View.VISIBLE) {
            boi_but.setVisibility(View.VISIBLE);
            prok_uch.setVisibility(View.VISIBLE);
        }
        boi_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(at);
                integrator.initiateScan();
            }
        });
        mTextMessage.setText(R.string.title_childrens);
        LinearLayoutManager Uch_ll = new LinearLayoutManager(this);
        prok_uch.setLayoutManager(Uch_ll);
        prok_uch.setItemAnimator(new DefaultItemAnimator());
        boiz = Boiz_saver.get_boiz(file);
        mAdapter = new Adapter_recuc(boiz);
        prok_uch.setAdapter(mAdapter);
    }

    public void setBringContent() {
        if (boi_but.getVisibility() == View.VISIBLE) {
            boi_but.setVisibility(View.GONE);
            prok_uch.setVisibility(View.GONE);
        }
        if (sbor_create_but.getVisibility() == View.GONE) {
            sbor_create_but.setVisibility(View.VISIBLE);
        }
        sbor_create_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }

            void openDialog() {
                final Date dateAndTime = new Date();
                final int[] curHour = new int[1];
                final int[] curMinute = new int[1];
                final int pois = -180;
                final TimePickerDialog timePickerDialog = new TimePickerDialog(AdminMain.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                curHour[0] = hourOfDay;
                                curMinute[0] = minute;
                                if (pois != dateAndTime.getTimezoneOffset()) {
                                    curHour[0] += (dateAndTime.getTimezoneOffset() - pois) / 60;
                                }
                                if (curHour[0] < 0) {
                                    curHour[0] = 24 + curHour[0];
                                } else {
                                    curHour[0] %= 24;
                                }
                                complete(curHour[0], curMinute[0]);
                            }
                        }, dateAndTime.getHours(), dateAndTime.getMinutes(), true);
                timePickerDialog.show();
            }
        });
    }

    void complete(int hour, int minute) {
        try {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpsURLConnection huc = (HttpsURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.addRequestProperty("Content-type", "application/json");
            huc.addRequestProperty("Authorization", "key=AAAAzAFCJzQ:APA91bFk4WHicdVlUkTECIbPFPOeelDeHIKvUa9MUjnstDmLi8v3_CjoGLEvhIUCfyyTliz7JHAHDIIu4ziHvmL2LsRcKM9hYYlIUXdfwqwtH59gMymhTv1TCq-Xl0yy6nj3eEqG3E_h");
            PrintWriter out = new PrintWriter(huc.getOutputStream());
            out.print(String.format("{\n" +
                    "  \"registration_ids\":[%s],\n" +
                    "  \"notification\": {\n" +
                    "      \"title\":\"Внимание!!! сбор\",\n" +
                    "      \"body\":\"Вы должны придти на сбор, который состоится в %d:%d\"\n" +
                    "  }\n" +
                    "  data: {\n" +
                    "    hour: '%d',\n" +
                    "    minute: '%d'\n" +
                    "  }\n" +
                    "}", getAllTokens(), hour, minute, hour, minute));
            out.close();
            Scanner in = new Scanner(huc.getInputStream());
            Toast.makeText(AdminMain.this, in.nextLine(), Toast.LENGTH_LONG).show();
            in.close();
        } catch (Exception e) {
            Toast.makeText(AdminMain.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public String getAllTokens() throws Exception {
        String res = "";
        for (Boi mac : boiz) {
            res += ",\"" + new GsonBuilder().create().fromJson(ServerSN.getInfo(mac.getMac()), Info.class).token + "\"";
        }
        return res.substring(1);
    }


    public void setAsksContent() {

        if (boi_but.getVisibility() == View.VISIBLE) {
            boi_but.setVisibility(View.GONE);
            prok_uch.setVisibility(View.GONE);
        }
        if (sbor_create_but.getVisibility() != View.GONE) {
            sbor_create_but.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        mTextMessage = findViewById(R.id.admin_title);
        boi_but = findViewById(R.id.add_boi_button);
        prok_uch = findViewById(R.id.Recycler_View_uch);
        sbor_create_but = findViewById(R.id.add_sbor_but);
        mTextMessage.setText(R.string.title_bring);
        mTextMessage.setText(R.string.title_asks);
        file = new File(getFilesDir(), "save.txt");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                setMembersContent();
                final EditText ed = new EditText(this);
                ed.setText(result.getContents());
                String[] gi = result.getContents().split(" ");
                Boi boi_add = new Boi(gi[0], gi[1], gi[2]);
                boiz.add(boi_add);
                Boiz_saver.save_boiz(boiz, file);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}