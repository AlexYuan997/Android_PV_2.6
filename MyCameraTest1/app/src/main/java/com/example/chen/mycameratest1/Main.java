package com.example.chen.mycameratest1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity {
	
	CameraPreview cp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cp = new CameraPreview(this);
		setContentView(cp);
		Log.i("webcam", "start.");
	}
}
