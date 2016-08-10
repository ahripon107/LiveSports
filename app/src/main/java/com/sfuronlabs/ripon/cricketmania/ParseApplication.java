package com.sfuronlabs.ripon.cricketmania;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Ripon on 10/29/15.
 */
public class ParseApplication extends Application {

    public void onCreate()
    {
        super.onCreate();

        Parse.initialize(this,"qjuLn9mQKkpIR1v9TrNBvWF16KDSh1vSS3ocjTcS","Mqcd9rknGJ9PxhWN79xFb5vvJkNDb62t8NpzPLe5");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        //PushService.setDefaultPushCallback(this,MainActivity.class);
    }
}
