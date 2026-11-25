package com.example.caloriecalculator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {

    private EditText etAge;
    private EditText etWeight;
    private Spinner spinnerActivity;
    private Button btnCalculate;
    private Button btnClear;
    private TextView tvError;

    private LinearLayout layoutResult;
    private TextView tvCalories;
    private TextView tvCategoryBadge;
    private TextView tvBreakdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind UI components
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        spinnerActivity = findViewById(R.id.spinnerActivity);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        tvError = findViewById(R.id.tvError);

        layoutResult = findViewById(R.id.layoutResult);
        tvCalories = findViewById(R.id.tvCalories);
        tvCategoryBadge = findViewById(R.id.tvCategoryBadge);
        tvBreakdown = findViewById(R.id.tvBreakdown);

        // Calculate Calories Click Listener
        btnCalculate.setOnClickListener(v -> calculateCalories());

        // Clear Click Listener
        btnClear.setOnClickListener(v -> clearFields());
    }

    private void calculateCalories() {
        hideKeyboard();
        tvError.setVisibility(View.GONE);

        String ageStr = etAge.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        // 1. Validate inputs are not empty
        if (ageStr.isEmpty() || weightStr.isEmpty()) {
            showError(getString(R.string.error_empty));
            return;
        }

        int age;
        double weight;

        // 2. Parse and validate Age
        try {
            age = Integer.parseInt(ageStr);
            if (age < 1 || age > 120) {
                showError(getString(R.string.error_invalid_age));
                return;
            }
        } catch (NumberFormatException e) {
            showError(getString(R.string.error_invalid_age));
            return;
        }

        // 3. Parse and validate Weight
        try {
            weight = Double.parseDouble(weightStr);
            if (weight < 5.0 || weight > 400.0) {
                showError(getString(R.string.error_invalid_weight));
                return;
            }
        } catch (NumberFormatException e) {
            showError(getString(R.string.error_invalid_weight));
            return;
        }

        // 4. Retrieve selected activity level
        String selectedCategory = spinnerActivity.getSelectedItem().toString();
        double activityMultiplier;

        // Map activity category to standard multiplier
        switch (selectedCategory) {
            case "Sedentary":
                activityMultiplier = 1.20;
                break;
            case "Very Active":
                activityMultiplier = 1.725;
                break;
            case "Moderately Active":
            default:
                activityMultiplier = 1.55;
                break;
        }

        // 5. Calculate Basal Metabolic Rate (BMR) & Daily Calorie Requirement
        // Standard Mifflin-St Jeor formula baseline:
        // BMR = (10 * weight_kg) + (6.25 * standard_height_cm) - (5 * age_years) - 78
        double standardHeightCm = 170.0;
        double bmr = (10.0 * weight) + (6.25 * standardHeightCm) - (5.0 * age) - 78.0;
        if (bmr < 500.0) {
            bmr = 500.0;
        }

        double dailyCalories = bmr * activityMultiplier;
        long roundedCalories = Math.round(dailyCalories);

        // 6. Display results
        tvCalories.setText(String.format(Locale.US, "%,d kcal / day", roundedCalories));
        tvCategoryBadge.setText(selectedCategory);

        // Badge styling based on category
        switch (selectedCategory) {
            case "Sedentary":
                tvCategoryBadge.setTextColor(Color.parseColor("#475569"));
                break;
            case "Moderately Active":
                tvCategoryBadge.setTextColor(Color.parseColor("#B45309"));
                break;
            case "Very Active":
                tvCategoryBadge.setTextColor(Color.parseColor("#047857"));
                break;
        }

        tvBreakdown.setText(String.format(
                Locale.US,
                "Estimated BMR: %,d kcal • Activity Multiplier: %.3fx",
                Math.round(bmr),
                activityMultiplier
        ));

        layoutResult.setVisibility(View.VISIBLE);
    }

    private void clearFields() {
        etAge.setText("");
        etWeight.setText("");
        spinnerActivity.setSelection(0);
        layoutResult.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        etAge.requestFocus();
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
        layoutResult.setVisibility(View.GONE);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
