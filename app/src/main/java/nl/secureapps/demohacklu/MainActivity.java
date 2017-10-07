package nl.secureapps.demohacklu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    public static final String OUTPUT_EVENT = "output_event";
    public static final String OUTPUT_DATA = "output";
    private String output;
    private EditText editTextOutput;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            output = intent.getStringExtra(OUTPUT_DATA);
            if (editTextOutput != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder currentOutput = new StringBuilder(String.valueOf(editTextOutput.getText()));
                        currentOutput.append(output);
                        currentOutput.append("\n");
                        editTextOutput.setText(currentOutput.toString());
                        editTextOutput.setSelection(currentOutput.length());
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("frida");
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(OUTPUT_EVENT));
    }

    @Override
    public void onResume() {
        super.onResume();
        editTextOutput = (EditText) findViewById(R.id.editTextOutput);
        editTextOutput.setKeyListener(null);

        Button buttonIo = (Button) findViewById(R.id.button_io);
        Button buttonHttps = (Button) findViewById(R.id.button_https);
        Button buttonHttpsPin = (Button) findViewById(R.id.button_https_pin);
        Button buttonCrypt = (Button) findViewById(R.id.button_crypt);
        Button buttonKey = (Button) findViewById(R.id.button_key);

        buttonIo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSelectedOption(R.id.action_io);
            }
        });
        buttonHttps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSelectedOption(R.id.action_https);
            }
        });
        buttonHttpsPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSelectedOption(R.id.action_https_pin);
            }
        });
        buttonCrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSelectedOption(R.id.action_crypt);
            }
        });
        buttonKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSelectedOption(R.id.action_key);
            }
        });
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        handleSelectedOption(id);
        return super.onOptionsItemSelected(item);
    }

    private void handleSelectedOption(int id) {
        switch (id) {
            case R.id.action_io:
                IoDemo ioDemo = new IoDemo();
                ioDemo.startIoDemo();
                break;
            case R.id.action_https:
                HttpsDemo httpsDemo = new HttpsDemo();
                httpsDemo.startHttpsDemo();
                break;
            case R.id.action_https_pin:
                HttpsDemo httpsPinDemo = new HttpsDemo();
                httpsPinDemo.startHttpsPinDemo();
                break;
            case R.id.action_key:
                KeyDemo keyDemo = new KeyDemo();
                keyDemo.startKeyDemo();
                break;
            case R.id.action_crypt:
                CryptoDemo cryptoDemo = new CryptoDemo();
                cryptoDemo.startCryptoDemo();
                break;
        }
    }

    public static void processOutput(String output) {
        Intent intent = new Intent(OUTPUT_EVENT);
        intent.putExtra(OUTPUT_DATA, output);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
