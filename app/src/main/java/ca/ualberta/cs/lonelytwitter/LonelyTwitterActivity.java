package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private EditText Text_date;
	private ListView oldTweetsList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Text_date = (EditText) findViewById(R.id.Text_date);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);
		Button moodButton1 = (Button) findViewById(R.id.mood1);
		Button moodButton2 = (Button) findViewById(R.id.mood2);
		Button dateButton = (Button) findViewById(R.id.change_date);
		Button checkdateButton = (Button) findViewById(R.id.check_date);


		dateButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String text = bodyText.getText().toString();
				Tweet tweet = new Tweet(text);
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY");
				java.util.Date myDate;
				try {
					myDate = df.parse(Text_date.getText().toString());
					tweet.mood.change_date(myDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		});
		checkdateButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String text = bodyText.getText().toString();
				Tweet tweet = new Tweet(text);
				TextView textView = (TextView) findViewById(R.id.current_message);
				textView.setText("Current mood date: "+tweet.mood.check_date());

			}
		});

		moodButton1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String text = bodyText.getText().toString();
				Tweet tweet = new Tweet(text);
				tweet.add_mood1();
				TextView textView = (TextView) findViewById(R.id.current_message);
				textView.setText(tweet.message+tweet.total_mood);

			}
		});
		moodButton2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String text = bodyText.getText().toString();
				Tweet tweet = new Tweet(text);
				tweet.add_mood2();
				tweet = new Tweet(tweet.total_mood);
				TextView textView = (TextView) findViewById(R.id.current_message);
				textView.setText(tweet.message+tweet.total_mood);

			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
                Tweet tweet = new Tweet(text);
				text = text + tweet.total_mood;
				saveInFile(text, new Date(System.currentTimeMillis()));
				finish();

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		String[] tweets = loadFromFile();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}

	private String[] loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			while (line != null) {
				tweets.add(line);
				line = in.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweets.toArray(new String[tweets.size()]);
	}
	
	private void saveInFile(String text, Date date) {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_APPEND);
			fos.write(new String(date.toString() + " | " + text)
					.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}