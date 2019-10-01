package com.example.sber_app1;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private EditText sourceEditText;
    private TextView resultTextView;
    private ImageButton copyButton;
    private ImageButton copyButton2;
    private CompoundButton check_caps;
    private CompoundButton check_num;
    private CompoundButton check_sym;
    private Button generateButton;
    private TextView generatedTextView;
    private ImageView password_bar;

    private String[] latin;
    private String[] russian;
    private String[] lower;
    private String[] upper;
    private String[] digit;
    private String[] symbol;

    private PasswordHelper helper;
    private PasswordGen generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEditText = findViewById(R.id.edit_source);
        resultTextView = findViewById(R.id.text_result);
        copyButton = findViewById(R.id.copy_password);
        copyButton2 = findViewById(R.id.copy_generated);
        password_bar = findViewById(R.id.password_bar);

        generateButton = findViewById(R.id.button_generatepassword);

        check_caps = findViewById(R.id.check_caps);
        check_num = findViewById(R.id.check_num);
        check_sym = findViewById(R.id.check_sym);

        generatedTextView = findViewById(R.id.text_generated);

        latin = getResources().getStringArray(R.array.latin);
        russian = getResources().getStringArray(R.array.russian);
        lower = getResources().getStringArray(R.array.lower);
        upper = getResources().getStringArray(R.array.upper);
        digit = getResources().getStringArray(R.array.digit);
        symbol = getResources().getStringArray(R.array.symbols);

        helper = new PasswordHelper(russian, latin);
        generator = new PasswordGen(lower);

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (manager != null) {
                    manager.setPrimaryClip(
                            ClipData.newPlainText(
                                    getString(R.string.clipboard_title), resultTextView.getText().toString()
                            )
                    );

                    Toast.makeText(MainActivity.this, R.string.toast_copied, Toast.LENGTH_LONG).show();
                }
            }
        });

        copyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (manager != null) {
                    manager.setPrimaryClip(
                            ClipData.newPlainText(
                                    getString(R.string.clipboard_title), generatedTextView.getText().toString()
                            )
                    );

                    Toast.makeText(MainActivity.this, R.string.toast_copied, Toast.LENGTH_LONG).show();
                }
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean caps = false;
                boolean num = false;
                boolean sym = false;

                if (check_caps.isChecked()) {caps = true;}
                if (check_num.isChecked()) {num = true;}
                if (check_sym.isChecked()) {sym = true;}

                String password = "";

                boolean[] check = {caps, num, sym};

                switch (Arrays.toString(check)) {
                    case "[false, false, false]":
                        password = generator.generate(lower);
                        break;
                    case "[true, false, false]":
                        String[] low_up = generator.merge(lower, upper);
                        password = generator.generate(generator.randomize(low_up));
                        break;
                    case "[false, true, false]":
                        String[] low_num = generator.merge(lower, digit);
                        password = generator.generate(generator.randomize(low_num));
                        break;
                    case "[false, false, true]":
                        String[] low_sym = generator.merge(lower, symbol);
                        password = generator.generate(generator.randomize(low_sym));
                        break;
                    case "[true, true, false]":
                        String[] low_upper = generator.merge(lower, upper);
                        String[] low_up_num = generator.merge(low_upper, digit);
                        password = generator.generate(generator.randomize(low_up_num));
                        break;
                    case "[false, true, true]":
                        String[] low_number = generator.merge(lower, digit);
                        String[] low_num_sym = generator.merge(low_number, symbol);
                        password = generator.generate(generator.randomize(low_num_sym));
                        break;
                    case "[true, false, true]":
                        String[] low_upp = generator.merge(lower, upper);
                        String[] low_up_sym = generator.merge(low_upp, symbol);
                        password = generator.generate(generator.randomize(low_up_sym));
                        break;
                    case "[true, true, true]":
                        String[] lower_up = generator.merge(lower, upper);
                        String[] lower_up_num = generator.merge(lower_up, digit);
                        String[] low_up_num_sym = generator.merge(lower_up_num, symbol);
                        password = generator.generate(generator.randomize(low_up_num_sym));
                        break;
                }
                generatedTextView.setText(password);
            }
        });

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultTextView.setText(helper.convert(s));
                copyButton.setEnabled(s.length() > 0);
                if (resultTextView.getText().length()<5){password_bar.setImageLevel(0);}
                else if (resultTextView.getText().length()<8) {password_bar.setImageLevel(1);}
                else {password_bar.setImageLevel(2);}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
