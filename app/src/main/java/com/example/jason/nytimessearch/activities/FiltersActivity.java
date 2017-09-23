package com.example.jason.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

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

    int year;
    int month;
    int day;

    Boolean dateWasChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        bindClickHandlers();
        setupCheckboxes();

        loadInitialData();

        updateDateButton();

    }

    public void updateDateButton() {
        btnDatePicker.setText("Date start: " + year + "/" + (month+1) + "/" + day  );
    }
    private void loadInitialData() {
        // TODO: load initial data for spinner and date (passed back and forth between views)
        String sortingOrder = getIntent().getStringExtra("sorting_order");
        setSpinnerToValue(spnrSortOrder, sortingOrder);

        year = getIntent().getIntExtra("year", 2017);
        month = getIntent().getIntExtra("month", 1);
        day = getIntent().getIntExtra("day", 1);

        Boolean isArtsChecked = getIntent().getBooleanExtra("is_arts_checked", false);
        Boolean isFashionChecked = getIntent().getBooleanExtra("is_fashion_checked", false);
        Boolean isSportsChecked = getIntent().getBooleanExtra("is_sports_checked", false);


        cbArts.setChecked(isArtsChecked);
        cbFashion.setChecked(isFashionChecked);
        cbSports.setChecked(isSportsChecked);
    }


    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }


    private void bindClickHandlers() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDatePicker = (Button) findViewById(R.id.btnDatePicker);
        spnrSortOrder = (Spinner) findViewById(R.id.spnrSortOrder);



        final DatePickerFragment newFragment = new DatePickerFragment();



        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Supply num input as an argument.
                Bundle dateArgs= new Bundle();
                dateArgs.putInt("year", year);
                dateArgs.putInt("month", month);
                dateArgs.putInt("day", day);

                newFragment.setArguments(dateArgs);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sortOrder = spnrSortOrder.getSelectedItem().toString();
                Log.d("debug", sortOrder);
                Intent data = new Intent();

                data.putExtra("year", year);
                data.putExtra("month", month);
                data.putExtra("day", day);


                data.putExtra("date_changed", dateWasChanged);


                // Pass relevant data back as a result
                data.putExtra("sorting_order", sortOrder);
                data.putExtra("is_arts_checked", cbArts.isChecked());
                data.putExtra("is_fashion_checked", cbFashion.isChecked());
                data.putExtra("is_sports_checked", cbSports.isChecked());

                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish();

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
        year = cal.get(Calendar.YEAR);
        month  = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        dateWasChanged = true;
        updateDateButton();

    }

}



