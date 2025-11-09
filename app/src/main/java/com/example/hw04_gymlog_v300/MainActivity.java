package com.example.hw04_gymlog_v300;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw04_gymlog_v300.Database.GymLogRepository;
import com.example.hw04_gymlog_v300.Database.entities.GymLog;
import com.example.hw04_gymlog_v300.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "DAC_GYMLOG";
    private ActivityMainBinding binding;
    private GymLogRepository repository;
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new GymLogRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });

    }

    private void insertGymLogRecord(){
        GymLog log = new GymLog(mExercise, mWeight, mReps);
        repository.insertGymLog(log);
    }

    private void updateDisplay(){
        String currentInfo = binding.logDisplayTextView.getText().toString();
        String newDisplay = String.format(Locale.US, "Exercise: %s%nWeight: %.2f%nReps: %d%n=-=-=-=%n", mExercise, mWeight, mReps);
        newDisplay+= currentInfo;

        binding.logDisplayTextView.setText(newDisplay);
    }


    private void getInformationFromDisplay(){
        mExercise = binding.exerciseInputEditText.getText().toString();

        try {
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight edit text.");
        }

        try {
            mReps = Integer.parseInt(binding.repsInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Reps edit text.");
        }


    }
}