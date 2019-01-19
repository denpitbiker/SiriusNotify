package ru.notificator.sirius.siriusnotificator;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.*;

import ru.notificator.sirius.siriusnotificator.Recyclik.Boi;
import ru.notificator.sirius.siriusnotificator.vievmodel.TextVievModel;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    public static final String save = "save";
    public static final Boolean child = false;
    public static final Boolean tutor = false;
    public static final Boolean firstl = true;
    SharedPreferences saver;
    private TextVievModel mVievModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saver = getSharedPreferences(save, Context.MODE_PRIVATE);
        mVievModel = ViewModelProviders.of(this).get(TextVievModel.class);
        mVievModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this,"lol",Toast.LENGTH_LONG).show();
            }
        });
        if (saver.getBoolean("first_enter", true)) {
            SharedPreferences.Editor editor = saver.edit();
            editor.putBoolean("child", child);
            editor.putBoolean("tutor", tutor);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, Tutor_or_child.class);
            startActivity(intent);
            finish();
        } else if (saver.getBoolean("tutor", false)) {
                Intent intent = new Intent(MainActivity.this, AdminMain.class);
                startActivity(intent);
                finish();
            }
        //else{
          //  Intent intent = new Intent(MainActivity.this, ShowCode.class);
          //  startActivity(intent);
          //  finish();
      //  }

    }
}
