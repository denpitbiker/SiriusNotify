package ru.notificator.sirius.siriusnotificator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

import static java.lang.String.valueOf;
import static ru.notificator.sirius.siriusnotificator.MainActivity.save;


public class AdminEnter extends AppCompatActivity {
    EditText tx;
    EditText name;
    EditText surname;
    public static final String ADMIN_KEY = "admin";
    TextView tex;

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
        setContentView(R.layout.activity_admin_enter);
        tx = findViewById(R.id.admin_key);
        name = findViewById(R.id.name_client);
        surname = findViewById(R.id.surname_client);
        tex = findViewById(R.id.errr);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.admin_key_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str= check();
                if (String.valueOf(tx.getText()).equals(ADMIN_KEY)) {
                    if (str!=null){
                        SharedPreferences saver = getSharedPreferences(save, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = saver.edit();
                        editor.putBoolean("tutor",true);
                        editor.putBoolean("first_enter",false);
                        editor. putString("name",String.valueOf(name.getText()));
                        editor. putString("surname",String.valueOf(surname.getText()));
                        editor.putString("Mac",ShowCode.getBluetoothMacAddress());
                        editor.apply();
                        startActivity(new Intent(AdminEnter.this, AdminMain.class));
                        finish();
                    }
                   else {
                        tex.setTextColor(Color.RED);
                        tex.setText("Неверно введена фамилия или имя\n(Разрешается писать только в русской раскладке)");
                    }
                }
                else{
                    if (str==null){
                        tex.setTextColor(Color.RED);
                        tex.setText("Неверный пароль и фамилия/имя");
                    }
                    else {
                        tex.setTextColor(Color.RED);
                        tex.setText("Неверный пароль");
                    }
                }
            }
        });
    }
}
