package info.sayederfanarefin.qrbarcode.ui;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import info.sayederfanarefin.qrbarcode.MainActivity;
import info.sayederfanarefin.qrbarcode.R;

public class SplashScreen extends AwesomeSplash
{

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, 1000);
//    }

    @Override
    public void initSplash(ConfigSplash configSplash) {

			/* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(800); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.logoneworange); //or any other drawable
        configSplash.setAnimLogoSplashDuration(800); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInDown); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)



        //Customize Title
        configSplash.setTitleSplash(this.getResources().getString(R.string.app_name));
        configSplash.setTitleTextColor(R.color.profilePictureBorder);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(800);
        configSplash.setAnimTitleTechnique(Techniques.FadeInUp);
       // configSplash.setTitleFont("fonts/Montserrat-Light.ttf"); //provide string to your font located in assets/fonts/

    }

    @Override
    public void animationsFinished() {

        //transit to another activity here
        //or do whatever you want
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, 500);

        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
