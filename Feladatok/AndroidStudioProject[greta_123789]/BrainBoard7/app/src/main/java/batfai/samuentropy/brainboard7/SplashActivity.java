package batfai.samuentropy.brainboard7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

class Splash extends Thread {

    SplashActivity act;
    android.graphics.drawable.AnimationDrawable anim;

    public Splash(android.graphics.drawable.AnimationDrawable anim, SplashActivity act) {
        this.anim = anim;
        this.act = act;
    }

    public void st() {
        android.content.Intent intent = new android.content.Intent();
        intent.setClass(act, MainMenuActivity.class);
        act.startActivity(intent);
        anim.stop();
    }

    @Override
    public void run() {
        anim.start();
    }
}

public class SplashActivity extends android.app.Activity
{
    private Splash splash;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        android.widget.ImageView iv = (android.widget.ImageView) findViewById(R.id.splashanimation);

        iv.setBackgroundResource(R.drawable.splash_animation);

        final android.graphics.drawable.AnimationDrawable anim = (android.graphics.drawable.AnimationDrawable) iv.getBackground();

        splash = new Splash(anim, this);
        runOnUiThread(splash);
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent evt) {
        if (evt.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            splash.st();
        }
        return true;
    }

}
