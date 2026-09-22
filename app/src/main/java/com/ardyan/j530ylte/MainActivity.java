```java
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

        // =========================
        // MAIN LAYOUT
        // =========================

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        // =========================
        // TITLE
        // =========================

        TextView title = new TextView(this);
        title.setText("J530Y LTE Tools");
        title.setTextSize(26);
        title.setPadding(0, 0, 0, 24);

        // =========================
        // DEVICE INFO
        // =========================

        TextView device = new TextView(this);

        device.setText(
                "Samsung Galaxy J5 Pro\n" +
                "SM-J530Y • Android 9\n\n" +
                "Root access is required to open Samsung's\n" +
                "Phone Information / Testing menu."
        );

        device.setTextSize(16);
        device.setPadding(0, 0, 0, 32);

        // =========================
        // PHONE INFORMATION BUTTON
        // =========================

        Button phoneInfo = new Button(this);
        phoneInfo.setText("OPEN PHONE INFORMATION");

        phoneInfo.setOnClickListener(v -> openTestingMenu());

        // =========================
        // EXIT BUTTON
        // =========================

        Button exit = new Button(this);
        exit.setText("EXIT");

        exit.setOnClickListener(v -> finish());

        // =========================
        // ADD VIEWS
        // =========================

        layout.addView(title);
        layout.addView(device);
        layout.addView(phoneInfo);
        layout.addView(exit);

        // =========================
        // SET CONTENT
        // =========================

        setContentView(layout);
    }

    // =========================================================
    // OPEN SAMSUNG TESTING / PHONE INFORMATION
    // =========================================================

    private void openTestingMenu() {

        try {

            /*
             * IMPORTANT:
             *
             * The $ character must be protected from the shell.
             *
             * This is equivalent to:
             *
             * am start -n 'com.android.settings/.Settings$TestingSettingsActivity'
             *
             * The single quotes prevent the shell from interpreting
             * $TestingSettingsActivity as an environment variable.
             */

            String command =
                    "am start -n 'com.android.settings/.Settings$TestingSettingsActivity'";

            // =========================
            // EXECUTE THROUGH SU
            // =========================

            Process process = Runtime.getRuntime().exec(
                    new String[]{
                            "su",
                            "-c",
                            command
                    }
            );

            // =========================
            // READ STANDARD OUTPUT
            // =========================

            BufferedReader stdout = new BufferedReader(
                    new InputStreamReader(
                            process.getInputStream()
                    )
            );

            // =========================
            // READ ERROR OUTPUT
            // =========================

            BufferedReader stderr = new BufferedReader(
                    new InputStreamReader(
                            process.getErrorStream()
                    )
            );

            StringBuilder output = new StringBuilder();

            String line;

            // Read stdout
            while ((line = stdout.readLine()) != null) {

                output.append(line)
                        .append("\n");
            }

            // Read stderr
            while ((line = stderr.readLine()) != null) {

                output.append(line)
                        .append("\n");
            }

            // =========================
            // WAIT FOR PROCESS
            // =========================

            int exitCode = process.waitFor();

            // =========================
            // RESULT
            // =========================

            if (exitCode == 0) {

                Toast.makeText(
                        this,
                        "Phone Information opened",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                String error = output.toString().trim();

                if (error.length() == 0) {
                    error = "Unknown error. Exit code: " + exitCode;
                }

                Toast.makeText(
                        this,
                        "Failed:\n" + error,
                        Toast.LENGTH_LONG
                ).show();
            }

            // =========================
            // CLOSE READERS
            // =========================

            stdout.close();
            stderr.close();

        } catch (Exception e) {

            // =========================
            // JAVA ERROR
            // =========================

            Toast.makeText(
                    this,
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
```

**Bagian paling penting** adalah ini:

```java
String command =
        "am start -n 'com.android.settings/.Settings$TestingSettingsActivity'";
```

Bukan:

```java
"am start -n " + TESTING_ACTIVITY
```

Dengan demikian command yang diberikan ke `su -c` memiliki format yang sama secara efektif dengan command ADB Anda yang berhasil.

Jika setelah APK di-install masih gagal, **jangan ubah-ubah dulu kodenya**. Tekan `OPEN PHONE INFORMATION` dan kirimkan teks Toast error yang muncul. Dari error `am` tersebut kita bisa menentukan apakah masalahnya ada di `su`, shell escaping, permission, atau `Settings$TestingSettingsActivity`.
