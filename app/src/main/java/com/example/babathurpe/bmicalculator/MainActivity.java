package com.example.babathurpe.bmicalculator;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.text.NumberFormat;


public class MainActivity extends ActionBarActivity {

    public static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private double weightAmount = 0.0;
    private double heightAmount = 0.0;
    private TextView weightText;
    private TextView heightText;
    private TextView bmiResultView;
    private TextView bmiResultValueView;
    private ToggleButton toggle;
    private EditText editWeight;
    private EditText editHeight;
    private Button calBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightText = (TextView) findViewById(R.id.heightTextView);
        weightText = (TextView) findViewById(R.id.weightTextView);
        bmiResultView = (TextView) findViewById(R.id.bmiResultTextView);
        bmiResultValueView = (TextView) findViewById(R.id.bmiValueResultTextView);
        toggle = (ToggleButton) findViewById(R.id.unitToggleButton);
        calBtn = (Button) findViewById(R.id.button);

        editWeight = (EditText) findViewById(R.id.weightEditText);
        editWeight.setEnabled(false);
        editWeight.addTextChangedListener(editWeightTextWatcher);

        editHeight = (EditText) findViewById(R.id.heightEditText);
        editHeight.setEnabled(false);
        editWeight.addTextChangedListener(editHeightTextWatcher);

        //toggle.toggle();

    }


    //English -- pounds(lb) and inches
    private void updateEnglishResult() {
        double bmiResult = (weightAmount * 703.0) / (heightAmount * heightAmount);
        String result = String.valueOf(bmiResult);
        bmiResultView.setText(result);

        if(bmiResult < 18.5 ){
            bmiResultValueView.setText("UnderWeight");
        } else if(bmiResult <=25){
            bmiResultValueView.setText("Normal");
        } else if(bmiResult <= 30){
            bmiResultValueView.setText("Overweight");
        } else {
            bmiResultValueView.setText("Obese");
        }

    }

    //Metric -- kg and metres
    private void updateMetricResult() {
        double bmiResult = weightAmount / (heightAmount * heightAmount);
        //bmiResultView.setText(numberFormat.format(bmiResult));
        String result = String.valueOf(bmiResult);
        bmiResultView.setText(result);

        if(bmiResult < 18.5 ){
            bmiResultValueView.setText("UnderWeight");
        } else if(bmiResult >= 18.5){
            bmiResultValueView.setText("Normal");
        } else if(bmiResult >= 25){
            bmiResultValueView.setText("Overweight");
        } else if(bmiResult >= 30){
            bmiResultValueView.setText("Obese");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //listen to toggle button change
    public void tgl_btn_clicked(View v) {
        //toggle button is on - english

            if (toggle.isChecked()) {
                heightText.setText("Height (in)");
                weightText.setText("Weight (lb)");
                editHeight.setEnabled(true);
                editWeight.setEnabled(true);
                //updateMetricResult();
            }
            //toggle button is off - metric
            else {
                heightText.setText("Height (m)");
                weightText.setText("Weight (kg)");
                editHeight.setEnabled(true);
                editWeight.setEnabled(true);
                //updateEnglishResult();
            }
    }

    public void calculateBtn(View v){
        if (v.getId() == R.id.button) {
            //On
            if (toggle.isChecked() && editWeight.isEnabled() && editHeight.isEnabled()) {
                updateEnglishResult();
            }

            if (!toggle.isChecked() && editWeight.isEnabled() && editHeight.isEnabled()) {
                updateMetricResult();
            }

            if (!editWeight.isEnabled() && !editHeight.isEnabled()) {
                //alert
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Error");
                alert.setMessage("Choose on Metric option!!");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
        }
    }

    private TextWatcher editWeightTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                weightAmount = Double.parseDouble(s.toString());
            } catch(NumberFormatException e){
                weightAmount = 0.0;
            }
            //updateEnglishResult();
            //updateMetricResult();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher editHeightTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                heightAmount = Double.parseDouble(s.toString());
            } catch(NumberFormatException e){
                heightAmount = 0.0;
            }
            //updateEnglishResult();
            //updateMetricResult();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}
