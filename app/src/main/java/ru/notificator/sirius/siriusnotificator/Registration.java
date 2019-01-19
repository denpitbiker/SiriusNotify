package ru.notificator.sirius.siriusnotificator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static java.lang.String.valueOf;
import static ru.notificator.sirius.siriusnotificator.MainActivity.save;

public class Registration extends AppCompatActivity {
    EditText name, surname;

    String check() {
        String name = valueOf(this.name.getText());
        String family = valueOf(this.surname.getText());
        if (Pattern.matches("([а-яА-Я]{3,30}\\s*)+ ([а-яА-Я]{3,30}\\s*)+", name + " " + family)) {
            return name + " " + family;
        }
        return null;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name_client);
        surname = findViewById(R.id.family_client);

        Network.helloapi.gethellomsg()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Hellomsg>() {
                    @Override
                    public void accept(Hellomsg hellomsg) throws Exception {
                        ((TextView) findViewById(R.id.tex)).setText(hellomsg.message);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("error",throwable.getMessage(),throwable);
                    }
                });
        findViewById(R.id.enter_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = check();
                if (str == null) {
                    ((TextView) findViewById(R.id.error)).setText("Неверно введена фамилия или имя\n(Разрешается писать только в русской раскладке)");
                } else {
                    SharedPreferences saver = getSharedPreferences(save, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = saver.edit();
                    editor.putBoolean("child",true);
                    editor.putBoolean("first_enter",false);
                    editor. putString("name",String.valueOf(name.getText()));
                    editor. putString("surname",String.valueOf(surname.getText()));
                    editor.apply();
                    startActivity(new Intent(Registration.this, ShowCode.class));
                }
            }
        });
        findViewById(R.id.back_child_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
