package com.example.sudoku;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button)this.findViewById(R.id.btn);
		btn.setOnClickListener(this);
		Button btn1 = (Button)this.findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		Button btn2 = (Button)this.findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		
		Intent intent = new Intent(MainActivity.this, GamePanelActivity.class);
		ArrayList<Integer> blankList = new ArrayList<Integer>();
		Random random = new Random();
		switch(arg0.getId()){
		case R.id.btn:
			blankList.clear();
			for(int i = 0; i < 20; i++){
				int pos = random.nextInt(81);
				while(blankList.contains(pos)){
					pos = random.nextInt(81);
				}
				blankList.add(pos);
			}
			break;
		case R.id.btn1:
			blankList.clear();
			for(int i = 0; i < 35; i++){
				int pos = random.nextInt(81);
				while(blankList.contains(pos)){
					pos = random.nextInt(81);
				}
				blankList.add(pos);
			}
			break;
		case R.id.btn2:
			blankList.clear();
			for(int i = 0; i < 50; i++){
				int pos = random.nextInt(81);
				while(blankList.contains(pos)){
					pos = random.nextInt(81);
				}
				blankList.add(pos);
			}
			break;
		}
		intent.putIntegerArrayListExtra("blankList", blankList);
		startActivity(intent);
	}

}
