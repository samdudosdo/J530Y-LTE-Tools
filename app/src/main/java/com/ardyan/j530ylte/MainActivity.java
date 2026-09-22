package com.ardyan.j530ylte;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    private static final String TESTING_ACTIVITY =
            "com.android.settings/.Settings$TestingSettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView title = new TextView(this);
        title.setText("J530Y LTE Tools");
        title.setTextSize(26);
        title.setPadding(0, 0, 0, 24);

        TextView device = new TextView(this);
        device.setText(
                "Samsung Galaxy J5 Pro\n" +
                "SM-J530Y â€¢ Android 9\n\n" +
                "Root access is required to open Samsung's\n" +
                "Phone Information / Testing menu."
        );
        device.setTextSize(16);
        device.setPadding(0, 0, 0, 32);

        Button phoneInfo = new Button(this);
        phoneInfo.setText("OPEN PHONE INFORMATION");

        phoneInfo.setOnClickListener(v -> openTestingMenu());

        Button exit = new Button(this);
        exit.setText("EXIT");

        exit.setOnClickListener(v -> finish());

        layout.addView(title);
        layout.addView(device);
        layout.addView(phoneInfo);
        layout.addView(exit);

        setContentView(layout);
    }

 ```java
private void openTestingMenu() {

    try {

        String command =
                "am start -n 'com.android.settings/.Settings$TestingSettingsActivity'";

        Process process = Runtime.getRuntime().exec(
                new String[]{
                        "su",
                        "-c",
                        command
                }
        );

        BufferedReader stdout = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        BufferedReader stderr = new BufferedReader(
                new InputStreamReader(process.getErrorStream())
        );

        StringBuilder output = new StringBuilder();
        String line;

        while ((line = stdout.readLine()) != null) {
            output.append(line).append("\n");
        }

        while ((line = stderr.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();

        if (exitCode == 0) {

            Toast.makeText(
                    this,
                    "Phone Information opened",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Failed:\n" + output.toString(),
                    Toast.LENGTH_LONG
            ).show();
        }

    } catch (Exception e) {

        Toast.makeText(
                this,
                "Error: " + e.getMessage(),
                Toast.LENGTH_LONG
        ).show();
    }
}
