package batfai.samuentropy.brainboard7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private Firebase rootRef;
    private EditText editText;
    private EditText keyVal;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase.setAndroidContext(this);

        rootRef = new Firebase("https://brainboard-3fea4.firebaseio.com/");

        button = (Button)findViewById(R.id.btn);
        editText = (EditText)findViewById(R.id.editText);
        keyVal = (EditText)findViewById(R.id.editText2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = editText.getText().toString();
                String keyValue = keyVal.getText().toString();

                for(int i = 0; i<4; i++)
                {
                    rootRef.push();
                    Firebase childRef = rootRef.child(keyValue);
                    childRef.setValue(value);
                }

                /*mRefChild.setValue("artikaaaa");
                mRefChild = mRef.child("Születési év");
                mRefChild.setValue(1);*/
            }
        }) ;
    }
}
