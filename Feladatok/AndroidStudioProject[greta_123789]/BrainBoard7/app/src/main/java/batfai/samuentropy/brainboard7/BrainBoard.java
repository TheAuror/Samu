package batfai.samuentropy.brainboard7;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by artibarti on 2016.11.06..
 */

public class BrainBoard extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
