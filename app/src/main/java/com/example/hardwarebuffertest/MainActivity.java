package com.example.hardwarebuffertest;

import android.os.Bundle;

import android.hardware.HardwareBuffer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HardwareBufferTest";

    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mButton = findViewById(R.id.button);
        mTextView = findViewById(R.id.textView);

        mButton.setOnClickListener(v -> CreateHwBuffer());
    }

    private void CreateHwBuffer() {
        HardwareBuffer hwBuffer;
        try {
            // should succeed
            hwBuffer = HardwareBuffer.create(512, 512, HardwareBuffer.RGBA_8888, 1,
                    HardwareBuffer.USAGE_GPU_COLOR_OUTPUT
                            | HardwareBuffer.USAGE_GPU_SAMPLED_IMAGE
                            | HardwareBuffer.USAGE_COMPOSER_OVERLAY
                            | HardwareBuffer.USAGE_PROTECTED_CONTENT);
        } catch (IllegalArgumentException iae) {
            String st = Log.getStackTraceString(iae);
            mTextView.setText(st);
            Log.e(TAG, st);
            return;
        }

        mTextView.setText(R.string.string_ok);
        Log.i(TAG, "HardwareBuffer created! All okay.");
    }
}
