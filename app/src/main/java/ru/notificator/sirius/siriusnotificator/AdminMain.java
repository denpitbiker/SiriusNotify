package ru.notificator.sirius.siriusnotificator;

import android.app.Activity;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.notificator.sirius.siriusnotificator.Recyclik.Adapter_recuc;
import ru.notificator.sirius.siriusnotificator.Recyclik.Boi;

import static ru.notificator.sirius.siriusnotificator.MainActivity.save;

public class AdminMain extends AppCompatActivity {
    private TextView mTextMessage;


    private LinearLayout mainContent;


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

    public static List<Boi> boiz = new ArrayList<>();
    private SharedPreferences saver;

    public void setMembersContent() {

        final Activity at = this;
        boi_but = findViewById(R.id.add_boi_button);
        boi_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(at);
                integrator.initiateScan();
            }
        });
        boiz.add(new Boi("Gamaz","Neft","fdsfsdf"));
        Boiz_saver.save_boiz(boiz,getFilesDir());
        mTextMessage.setText(R.string.title_childrens);
        RecyclerView prok_uch = findViewById(R.id.Recycler_View_uch);
        LinearLayoutManager Uch_ll = new LinearLayoutManager(this);
        prok_uch.setLayoutManager(Uch_ll);
        prok_uch.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new Adapter_recuc(Boiz_saver.get_boiz(getFilesDir()));
        prok_uch.setAdapter(mAdapter);
    }

    public void setBringContent() {
        mTextMessage.setText(R.string.title_bring);
        mainContent.removeAllViews();
    }

    public void setAsksContent() {
        mTextMessage.setText(R.string.title_asks);
        mainContent.removeAllViews();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        mainContent = findViewById(R.id.admin_main_content);
        mTextMessage = findViewById(R.id.admin_title);

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
                final int ind = boiz.size();
                final EditText ed = new EditText(this);
                ed.setText(result.getContents());
                String[] gi = result.getContents().split(" ");
                Boi boi_add = new Boi(gi[0], gi[1], gi[2]);
                boiz.add(boi_add);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}