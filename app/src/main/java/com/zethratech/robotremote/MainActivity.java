package com.zethratech.robotremote;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    TcpClient client;
    EditText hostname;
    Button upButton;
    Button downButton;
    Button leftButton;
    Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new TcpClient();

        hostname = (EditText) findViewById(R.id.hostname_text);
        upButton = (Button) findViewById(R.id.up_button);
        downButton = (Button) findViewById(R.id.down_button);
        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);

        upButton.setOnTouchListener(touchListener);
        downButton.setOnTouchListener(touchListener);
        leftButton.setOnTouchListener(touchListener);
        rightButton.setOnTouchListener(touchListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connect:
                client.connect(getApplicationContext(), hostname.getText().toString(), 8888);
        }
    }
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(client.connected) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    switch (view.getId()) {
                        case R.id.up_button:
                            client.send("f");
                            Log.i(TAG, "f");
                            return true;
                        case R.id.down_button:
                            client.send("d");
                            Log.i(TAG, "d");
                            return true;
                        case R.id.left_button:
                            client.send("l");
                            Log.i(TAG, "l");
                            return true;
                        case R.id.right_button:
                            client.send("r");
                            Log.i(TAG, "r");
                            return true;
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    client.send("s");
                    Log.i(TAG, "s");
                    return true;
                }
            } else {
                Log.i(TAG, "Not Connected");
            }
            return false;
        }
    };

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}