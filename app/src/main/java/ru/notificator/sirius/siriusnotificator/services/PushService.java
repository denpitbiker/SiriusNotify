package ru.notificator.sirius.siriusnotificator.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.GsonBuilder;

import ru.notificator.sirius.siriusnotificator.Info;
import ru.notificator.sirius.siriusnotificator.ServerSN;
import ru.notificator.sirius.siriusnotificator.ShowCode;

import static android.support.constraint.Constraints.TAG;

public class PushService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        SharedPreferences sp = getSharedPreferences("save",Context.MODE_PRIVATE);
        sp.edit().putString("token",s).apply();
        Log.d(TAG, "Refreshed token: " + s);
        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String s) {
        SharedPreferences sp = getSharedPreferences("save",Context.MODE_PRIVATE);

        String stringi =ShowCode.getBluetoothMacAddress();
        try {
            Info inf =new GsonBuilder().create().fromJson(ServerSN.getInfo(stringi), Info.class);
            ServerSN.putInfo(sp.getString("Mac",""),s,sp.getString("name",inf.getName()),sp.getString("surname",inf.getSurname()));
        } catch (Exception e) {}
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
               // scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
