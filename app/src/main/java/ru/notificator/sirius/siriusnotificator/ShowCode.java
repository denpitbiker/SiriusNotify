package ru.notificator.sirius.siriusnotificator;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.*;


import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;

import static ru.notificator.sirius.siriusnotificator.MainActivity.save;

public class ShowCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);
        SharedPreferences saver = getSharedPreferences(save,MODE_PRIVATE);
        String text =saver.getString("surname","")+" "+saver.getString("name","")+" "+android.provider.Settings.Secure.getString(getContentResolver(),"bluetooth_address");
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 800, 800, hints);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ((ImageView) findViewById(R.id.qrcode)).setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        findViewById(R.id.back_showc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
