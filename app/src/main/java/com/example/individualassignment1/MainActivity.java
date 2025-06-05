package com.example.individualassignment1;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.*;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText amountInput, rateInput, monthsInput;
    TextView resultView;
    Button calcButton, aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        amountInput = findViewById(R.id.amountInput);
        rateInput = findViewById(R.id.rateInput);
        monthsInput = findViewById(R.id.monthsInput);
        resultView = findViewById(R.id.resultView);
        calcButton = findViewById(R.id.calcButton);
        aboutBtn = findViewById(R.id.aboutBtn);

        // Set input filters
        amountInput.setFilters(new InputFilter[]{new InputFilterMinMax("0.01", "10000000")});
        rateInput.setFilters(new InputFilter[]{new InputFilterMinMax("0.01", "100")});
        monthsInput.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});

        // Calculate button
        calcButton.setOnClickListener(v -> calculateDividend());

        // About button - launches AboutActivity
        aboutBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Opening About Page...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void calculateDividend() {
        if (amountInput.getText().toString().isEmpty() ||
                rateInput.getText().toString().isEmpty() ||
                monthsInput.getText().toString().isEmpty()) {
            resultView.setText("Please fill in all fields");
            return;
        }

        try {
            float amount = Float.parseFloat(amountInput.getText().toString());
            float rate = Float.parseFloat(rateInput.getText().toString());
            int months = Integer.parseInt(monthsInput.getText().toString());

            if (amount <= 0 || rate <= 0) {
                resultView.setText("Amount and rate must be positive");
                return;
            }

            if (months < 1 || months > 12) {
                resultView.setText("Months must be between 1-12");
                return;
            }

            float monthlyRate = rate / 100 / 12;
            float totalDividend = monthlyRate * amount * months;

            DecimalFormat df = new DecimalFormat("0.00");
            String result = "Total Dividend: RM" + df.format(totalDividend) +
                    "\nMonthly: RM" + df.format(monthlyRate * amount);
            resultView.setText(result);

        } catch (NumberFormatException e) {
            resultView.setText("Invalid input format");
        }
    }

    private class InputFilterMinMax implements InputFilter {
        private float min, max;

        InputFilterMinMax(String min, String max) {
            this.min = Float.parseFloat(min);
            this.max = Float.parseFloat(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                String newVal = dest.toString().substring(0, dstart) + source + dest.toString().substring(dend);
                if (!newVal.isEmpty()) {
                    float input = Float.parseFloat(newVal);
                    if (isInRange(min, max, input)) return null;
                }
            } catch (NumberFormatException ignored) {}
            return "";
        }

        private boolean isInRange(float min, float max, float input) {
            return input >= min && input <= max;
        }
    }
}
