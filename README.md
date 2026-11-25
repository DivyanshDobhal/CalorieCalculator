# Calorie Calculator — Android Application

An Android application developed to solve **Problem Statement 3**: Calculating a person's estimated daily calorie requirement based on their **age**, **weight**, and **activity level**.

---

## Problem Statement 3 Requirements

> **Problem Statement 3:**  
> The app should have three input fields for age (in years), weight (in kilograms), and activity level.  
> Upon selecting the activity level and pressing the **"Calculate Calories"** button, the app should display the **estimated daily calorie requirement** and the corresponding **activity category** (*Sedentary*, *Moderately Active*, or *Very Active*).

---

## Features & Implementation

- **Input 1: Age (in years):** Numeric input field (`EditText` with `number` keyboard) with validation ($1 \le \text{age} \le 120$).
- **Input 2: Weight (in kilograms):** Decimal numeric input field (`EditText` with `numberDecimal` keyboard) with validation ($5.0 \le \text{weight} \le 400.0\text{ kg}$).
- **Input 3: Activity Level:** Native dropdown selector (`Spinner`) with the three required options:
  - `Sedentary` (Little or no physical exercise)
  - `Moderately Active` (Moderate exercise 3–5 days/week)
  - `Very Active` (Intense exercise 6–7 days/week)
- **Calculate Button:** Pressing **"Calculate Calories"** performs input validation, evaluates the metabolic equation, dismisses the keyboard, and reveals the result card.
- **Results Display:**
  - **Estimated Daily Calorie Requirement:** Formatted clearly in kilocalories (e.g., `2,150 kcal / day`).
  - **Corresponding Activity Category:** Displayed in a styled badge (*Sedentary*, *Moderately Active*, or *Very Active*).
  - **Metabolic Details:** Shows estimated Basal Metabolic Rate (BMR) and the applied activity multiplier.
- **Clear Button:** Resets all inputs and hides the results card for quick subsequent entries.
- **Interactive Web Preview:** An `index.html` file is included in the project root to preview and test the exact logic in any browser.

---

## Calorie Calculation Formula

The application uses the standard, medically accepted **Mifflin-St Jeor Equation** for Basal Metabolic Rate (BMR) and standard Total Daily Energy Expenditure (TDEE) multipliers:

### 1. Basal Metabolic Rate (BMR)
$$\text{BMR} = (10 \times \text{weight in kg}) + (6.25 \times \text{standard height in cm}) - (5 \times \text{age in years}) - 78$$
*(Using reference standard height of 170 cm)*

$$\text{BMR} = (10 \times \text{weight}) - (5 \times \text{age}) + 984.5$$

### 2. Activity Multipliers (TDEE)
$$\text{Daily Calories} = \text{BMR} \times \text{Activity Multiplier}$$

| Activity Level | Multiplier | Description |
| :--- | :---: | :--- |
| **Sedentary** | `1.200` | Desk job, little or no regular exercise |
| **Moderately Active** | `1.550` | Moderate workout/sports 3 to 5 days per week |
| **Very Active** | `1.725` | Heavy exercise, athletic training 6 to 7 days per week |

---

## Project Structure

```
CalorieCalculator/
├── build.gradle                          # Top-level Gradle configuration
├── settings.gradle                       # Project settings & repositories
├── gradle.properties                     # JVM and AndroidX properties
├── index.html                            # Interactive browser preview
├── README.md                             # Documentation
└── app/
    ├── build.gradle                      # App module Gradle configuration
    └── src/
        └── main/
            ├── AndroidManifest.xml       # App Manifest configuration
            ├── java/com/example/caloriecalculator/
            │   └── MainActivity.java     # Main Activity business logic
            └── res/
                ├── drawable/             # UI drawables & button backgrounds
                │   ├── button_background.xml
                │   ├── card_background.xml
                │   ├── input_background.xml
                │   ├── result_card_background.xml
                │   └── badge_background.xml
                ├── layout/
                │   └── activity_main.xml # Layout with 3 inputs, button & result
                └── values/
                    ├── arrays.xml        # Activity levels array
                    ├── colors.xml        # Color definitions
                    ├── strings.xml       # String resources
                    └── styles.xml        # Theme definition
```

---

## How to Open and Run in Android Studio

1. Open **Android Studio**.
2. Select **File $\to$ Open...** (or click **Open** on the Welcome screen).
3. Navigate to:
   ```
   /Users/divyanshdobhal/Desktop/PROJECTS/CalorieCalculator
   ```
4. Select the `CalorieCalculator` folder and click **Open**.
5. Wait a few moments for Android Studio to complete Gradle sync.
6. Select an Android Emulator (or connect a physical device via USB debugging).
7. Click the green **Run** button (`▶`) or press `Shift + F10`.

---

## Running the Web Version

Double-click `index.html` or open it in Google Chrome, Safari, or Microsoft Edge to test the interface immediately without starting Android Studio.
