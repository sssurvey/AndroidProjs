package com.haomins.www.convertor_10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

	//private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView textView = (TextView) findViewById(R.id.textView5); //to allow the text view to become scrollable
		textView.setMovementMethod(new ScrollingMovementMethod());
	}

	//flags for the program
	public Boolean f2c = false;
	public String compareableString = "Fahrenheit to Celsius";
	//public Boolean c2f = false;
	//going to add some toast first
	//meanwhile it select the mode c2f or f2c using two boolean flags

	public void radioClicked(View v){ //C2F F2C
		String selectionText = ((RadioButton) v).getText().toString();
		Toast.makeText(this, "" + selectionText, Toast.LENGTH_SHORT).show();
		if (selectionText.equals(compareableString)){
			f2c = true;
		} else { //C2F
			f2c = false;
		}
	}

	public static String log = "";//this is for logging, set this to static so i can expend it all the time
	//this is the core function of this program
	public void doConvertion (View v) {
		EditText w1 = findViewById(R.id.editText6);
		double w1Value = Double.parseDouble(w1.getText().toString() + "0"); //added 0 here so if user don't type in anything, the program won't crush
		double answer;
		String f2cString = "F to C -> ";
		String c2fString = "C to F -> ";
		String unitC = "C";
		String unitF = "F";
		DecimalFormat df = new DecimalFormat(".#"); //to setup output with only one digit


		TextView answerTextTerminal = findViewById(R.id.textView5); //for logging
		TextView answerText = findViewById(R.id.textView4); //for clicked answer

		if (f2c == true) {
			answer = (w1Value - 32) * 5 / 9;
			answerText.setText(f2cString + "\n" + df.format(answer) + unitC);
			log = "" + f2cString + df.format(answer) + unitC + "\n" + log;

		} else {
			answer = (w1Value * 5/9) + 32;
			answerText.setText(c2fString + "\n" + df.format(answer) + unitF);
			log = "" + c2fString + df.format(answer) + unitF + "\n" + log;
		}
		answerTextTerminal.setText("" + log);


	}

}
