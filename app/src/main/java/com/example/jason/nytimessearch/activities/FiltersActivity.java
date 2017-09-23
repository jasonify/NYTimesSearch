package com.example.jason.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.jason.nytimessearch.R;
import com.example.jason.nytimessearch.fragments.DatePickerFragment;

import java.util.Calendar;

public class FiltersActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {


    Button btnSave;
    Button btnDatePicker;
    Spinner spnrSortOrder;
    CheckBox cbArts;
    CheckBox cbFashion;
    CheckBox cbSports;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        bindClickHandlers();
        loadInitialData();
        setupCheckboxes();

    }

    private void loadInitialData() {
        // TODO: load initial data for spinner and date (passed back and forth between views)
        int sortingOrder = getIntent().getIntExtra("sorting_order", 0);
        spnrSortOrder.setSelection(sortingOrder);

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

    public void setupCheckboxes() {
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbFashion = (CheckBox) findViewById(R.id.cbFashion);
        cbSports = (CheckBox) findViewById(R.id.cbSports);

        // Defines a listener for every time a checkbox is checked or unchecked
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked) {
                // compoundButton is the checkbox
                // boolean is whether or not checkbox is checked
                // Check which checkbox was clicked
                switch (view.getId()) {
                    case R.id.cbArts:
                        if (checked) {
                            // Put some meat on the sandwich
                        } else {
                            // Remove the meat
                        }
                        break;
                    case R.id.cbFashion:
                        if (checked) {
                            // Put some meat on the sandwich
                        } else {
                            // Remove the meat
                        }
                        break;
                    case R.id.cbSports:
                        if (checked) {
                            // Cheese me
                        } else {
                            // I'm lactose intolerant
                        }
                        break;
                }
            }

        };
        cbArts.setOnCheckedChangeListener(checkListener);
        cbFashion.setOnCheckedChangeListener(checkListener);
        cbSports.setOnCheckedChangeListener(checkListener);


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



