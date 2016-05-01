package com.xiangyixie.picshouse.service;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class NotificationInstanceIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, NotificationRegistrationService.class);
        startService(intent);
    }
}
