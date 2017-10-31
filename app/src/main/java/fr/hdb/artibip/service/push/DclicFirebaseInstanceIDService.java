package fr.hdb.artibip.service.push;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.androidannotations.annotations.EService;


@EService
public class DclicFirebaseInstanceIDService extends FirebaseInstanceIdService{

    private static final String TAG = "InstanceIDService";

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
    }

}
