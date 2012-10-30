package com.vjs.supplydemandanalysis.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.Utils;
import com.vjs.supplydemandanalysis.database.LoginDAO;

public class HomeActivity extends SuperActivity implements OnClickListener {
	LoginDAO loginDao = new LoginDAO(this);
	String loginUrl = "sda.freeiz.com/";// "192.168.0.3"; // "localhost";
	EditText username;
	EditText password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Utils.isLoggedIn(this)) {
			setContentView(R.layout.home);
			Button adminBtn = (Button) findViewById(R.id.homeAdminButton);
			adminBtn.setTextSize(Utils.BUTTONTEXTSIZE);
			adminBtn.setOnClickListener(this);
			Button analysisBtn = (Button) findViewById(R.id.homeAnalysisButton);
			analysisBtn.setTextSize(Utils.BUTTONTEXTSIZE);
			analysisBtn.setOnClickListener(this);
			Button myAccountButton = (Button) findViewById(R.id.homeMyAccountButton);
			myAccountButton.setTextSize(Utils.BUTTONTEXTSIZE);
			myAccountButton.setOnClickListener(this);
			// Set up the buttons
			setUpCoreButtonControls(this);
		} else {
			setContentView(R.layout.loginscreen);
			Button loginBtn = (Button) findViewById(R.id.loginBtn);
			loginBtn.setOnClickListener(this);
			username = (EditText) findViewById(R.id.usernameEdit);
			password = (EditText) findViewById(R.id.passwordEdit);
			ImageButton helpBtn = (ImageButton) findViewById(R.id.helpButtonLoginPage);
			helpBtn.setOnClickListener(this);
			helpBtn.setBackgroundColor(Color.BLACK);
		}
	}

	public void onClick(View arg0) {
		String message = "Decent help message here please";
		Intent i;
		switch (arg0.getId()) {
		case R.id.homeAdminButton:
			i = new Intent(this, AdminActivity.class);
			startActivity(i);
			break;
		case R.id.homeAnalysisButton:
			i = new Intent(this, SelectCollectionToAnalyseActivity.class);
			startActivity(i);
			break;
		case R.id.homeMyAccountButton:
			i = new Intent(this, MyAccountActivity.class);
			startActivity(i);
			break;
		case R.id.loginBtn:
			String user = username.getText().toString();
			String pass = password.getText().toString();
			if (loginAndDownload(user, pass)) {
				Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT)
						.show();
				Utils.setLoggedIn(this, user);
				onResume();
				break;
			} else {
				Toast.makeText(this, "Failed To Login!", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		case R.id.helpButton:
			new AlertDialog.Builder(HomeActivity.this)
					.setTitle("Help")
					.setMessage(message)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							}).show();
			break;
		case R.id.helpButtonLoginPage:
			new AlertDialog.Builder(HomeActivity.this)
					.setTitle("Help")
					.setMessage(message)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							}).show();
			break;
		case R.id.homeButton:
			Toast toast = Toast.makeText(this, "This is the homepage",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.backButton:
			HomeActivity.this.finish();
			break;
		}
	}

	/**
	 * get the db data, insert into db, mark db as ready / logged in
	 */
	private boolean loginAndDownload(String user, String pass) {
		boolean loggedIn = false;
		BufferedReader in = null;
		user = "vj";
		pass = "vjisgreat";
		try {
			// get local db version and send it
			String dbVersion = loginDao.getDbVersion(user);
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI("http://" + loginUrl
					+ "/getDbDetails.php?username=" + user + "&password="
					+ pass + "&dbVersion=" + dbVersion));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			final List<String> sqlCommands = new ArrayList<String>();
			String line = "";
			while ((line = in.readLine()) != null) {
				sqlCommands.add(line);
			}
			in.close();
			// logged in
			if (sqlCommands.isEmpty()) {
				loggedIn = true;
			} else if (!sqlCommands.get(0).startsWith("error")) {
				loggedIn = true;
				new AlertDialog.Builder(HomeActivity.this)
						.setTitle("Database Update Available")
						.setMessage(
								"Would you like to update to the latest database version?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										for (String command : sqlCommands) {
											// write to DB
											loginDao.runDbSetupFromLogin(command);
										}
										Toast.makeText(getApplicationContext(),
												"Updated!", Toast.LENGTH_SHORT)
												.show();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// do nothing
									}
								}).show();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return loggedIn;
	}
}