package com.droid.mulikamwizi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

public class MulikaMwizi extends Activity {

	private boolean isLightOn = false;
	private Camera camera;
	private ToggleButton Flashbtn;
	private String APP_LOG_TAG = "MulikaMwizi";
	
	@Override
	protected void onStop(){
		super.onStop();
		if(camera != null){
			camera.release();
			Log.i(APP_LOG_TAG,"Camera released::onStop()");
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mulika_mwizi);
		
		Flashbtn = (ToggleButton)findViewById(R.id.FlashlightBtn);
		
		Context context = this;
		PackageManager pm = context.getPackageManager();
		
		//check whether device supports camera
		if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
			Log.e(APP_LOG_TAG,"Device has no camera");
			
			new AlertDialog.Builder(this).
			setIcon(R.drawable.alerts_and_states_warning).
			setTitle("Warning").
			setMessage("Sorry dude. Your phone doesn't have a flash/LED :( ").
			setNegativeButton("Exit", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(APP_LOG_TAG,"Exiting...");
					finish();
				}
			}).
			setPositiveButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(APP_LOG_TAG,"staying around");
				}
			}).
			show();
			
		}
		
		camera = Camera.open();
		final Parameters Camparams = camera.getParameters();
		
		Flashbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isLightOn){
					Log.i(APP_LOG_TAG,"flash is off::onClick");
					Flashbtn.setText("OFF");
					Camparams.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(Camparams);
					camera.stopPreview();
					isLightOn = false;
				}else{
					Log.i(APP_LOG_TAG,"flash is on::onClick");
					Flashbtn.setText("ON");
					Camparams.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(Camparams);
					camera.startPreview();
					isLightOn = true;
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mulika_mwizi, menu);
		return true;
	}

}
