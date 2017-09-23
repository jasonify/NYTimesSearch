package com.example.jason.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.jason.nytimessearch.R;
import com.example.jason.nytimessearch.fragments.DatePickerFragment;

import java.util.Calendar;

public class FiltersActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {


    Button btnSave;
    Button btnDatePicker;
    Spinner spnrSortOrder;

//    CheckBox cb

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        bindClickHandlers();
        loadInitialData();
    }

    private void loadInitialData() {
        // TODO: load initial data for spinner and date (passed back and forth between views)
//        spnrSortOrder.setSelection(1);

        /*
        // Set arguments for date
        // https://stackoverflow.com/questions/15459209/passing-argument-to-dialogfragment

        public static MyDialogFragment newInstance(int num) {
    MyDialogFragment f = new MyDialogFragment();

    // Supply num input as an argument.
    Bundle args = new Bundle();
    args.putInt("num", num);
    f.setArguments(args);

    return f;
}


    // Get arguments

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mNum = getArguments().getInt("num");
    ...
}

         */
    }

    private void bindClickHandlers() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDatePicker = (Button) findViewById(R.id.btnDatePicker);
        spnrSortOrder = (Spinner) findViewById(R.id.spnrSortOrder);

        final DatePickerFragment newFragment = new DatePickerFragment();

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sortOrder = spnrSortOrder.getSelectedItem().toString();
                Log.d("debug", sortOrder);

            }
        });
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        int y = c.getTime().getYear();

        Calendar cal = c;
        int y= cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
    }

}
