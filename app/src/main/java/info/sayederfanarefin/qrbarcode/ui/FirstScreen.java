package info.sayederfanarefin.qrbarcode.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import info.sayederfanarefin.qrbarcode.authentication.CreateProfile;
import info.sayederfanarefin.qrbarcode.authentication.LoginPhone;
import info.sayederfanarefin.R;

public class FirstScreen extends AppCompatActivity {

    Button sign_in, sign_up;
    ImageView twitter,facebook,github,google;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        sign_in = (Button) findViewById(R.id.button_login_2);
        sign_up = (Button) findViewById(R.id.button_sign_up_2);

        twitter = (ImageView) findViewById(R.id.twitter_sign_in);
        facebook = (ImageView) findViewById(R.id.fb_sign_in);
        github = (ImageView) findViewById(R.id.github_sign_in);
        google = (ImageView) findViewById(R.id.google_sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstScreen.this, LoginPhone.class);
                startActivity(intent);
                finish();
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstScreen.this, CreateProfile.class);
                startActivity(intent);
                finish();
            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



}
