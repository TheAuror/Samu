package batfai.samuentropy.brainboard7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends android.app.Activity
{

    private EditText username;
    private ImageView btnLogin;
    private Firebase firebase;
    private ArrayList<String> users;
    private String baseUrl = "https://brainboard-3fea4.firebaseio.com/Users";
    private String currUser;

    private String blockCharacterSet = "~#^|$%&*!.[]#$";

	private InputFilter filter = new InputFilter()
	{
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {

            if (source != null && blockCharacterSet.contains(("" + source)))
            {
                return "";
            }
            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebase = new Firebase(baseUrl);
        username = (EditText) findViewById(R.id.uname);
        btnLogin = (ImageView) findViewById(R.id.login);
        users = new ArrayList<String>();
        username.setFilters(new InputFilter[] { filter });

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(users.isEmpty()) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        String post = postSnapshot.getKey();
                        users.add(post);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });
    }


    public int login()
    {
        String uname = ((EditText) findViewById(R.id.uname) ).getText().toString();

        if(uname.equals(""))
        {
            android.widget.Toast.makeText(this, "username is empty", android.widget.Toast.LENGTH_SHORT).show();
            return -1;
        }

        android.widget.Toast.makeText(this, "Welcome " + uname, android.widget.Toast.LENGTH_SHORT).show();
        currUser = uname;
        Intent data = new Intent();
        data.putExtra("username", uname);
        setResult(RESULT_OK, data);
        finish();

        return 0;
    }
}
