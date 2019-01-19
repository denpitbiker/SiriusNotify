package ru.notificator.sirius.siriusnotificator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Tutor_or_child extends AppCompatActivity {

    private View button_child;
    private View button_tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_or_child);
        button_child = findViewById(R.id.rebenok_reg_intro);
        button_tutor = findViewById(R.id.kurator_reg_intro);
        button_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tutor_or_child.this,AdminEnter.class));
            }
        });
        button_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tutor_or_child.this,Registration.class));
            }
        });
    }
}
