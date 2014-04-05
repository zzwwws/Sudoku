package com.example.sudoku;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class GamePanelActivity extends Activity implements OnClickListener {

	private GridView gridView;
	private MyAdapter adapter;
	private ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private ArrayList<Integer> blankList = new ArrayList<Integer>();
	private TextView[] optionNum = new TextView[5];
	private TextView errorTimesTv;
	private int[] tvId = { R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5 };
	private int errorTims = 5;

	private static final int MSG_REFRESH_TIMES = 1;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REFRESH_TIMES:
				errorTimesTv.setText(errorTims + "");
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_panel);

		blankList = getIntent().getIntegerArrayListExtra("blankList");
		arrayList = DataUtils.getSudokuList();

		gridView = (GridView) this.findViewById(R.id.gridview);
		adapter = new MyAdapter(this, arrayList);
		gridView.setAdapter(adapter);
		gridView.setNumColumns(9);

		for (int i = 0; i < 5; i++) {
			optionNum[i] = (TextView) this.findViewById(tvId[i]);
			optionNum[i].setText(i + 2 + "");
			optionNum[i].setOnClickListener(this);
		}
		errorTimesTv = (TextView) this.findViewById(R.id.err_times);
		errorTimesTv.setText(errorTims + "");
	}

	private class MyAdapter extends BaseAdapter {

		LayoutInflater inflater;
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> solved = new ArrayList<Integer>();
		int choosePos = -1;
		int chooseNum = -1;

		public MyAdapter(Context context, ArrayList<Integer> list) {
			this.list = list;
			inflater = ((Activity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public int getChoosePos() {
			return this.choosePos;
		}

		public void setChooseNum(int num) {
			this.chooseNum = num;
		}

		@Override
		public View getView(final int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub

			View view = inflater.inflate(R.layout.grid_item, null);
			TextView mTextView = (TextView) view
					.findViewById(R.id.grid_item_tv);
			mTextView.setText(list.get(position) + "");
			mTextView.setTextColor(Color.BLACK);
			mTextView.setTag(position);
			if (blankList.contains(position)) {
				if (choosePos == position) {
					if (chooseNum > 0) {
						mTextView.setText(chooseNum + "");
						if (list.get(position) == chooseNum) {
							mTextView.setBackgroundColor(0xfff8f8f8);
							solved.add(position);
							if(solved.size() == blankList.size())win();
						} else {
							refreshErrorTimes();
							mTextView.setBackgroundColor(0xfff497b2);
						}
					} else {
						mTextView.setBackgroundColor(0xfff8f898);
						mTextView.setText("");
					}
				} else if (solved.contains(position)) {
					mTextView.setBackgroundColor(0xfff8f8f8);
					mTextView.setText(list.get(position) + "");
				} else {
					mTextView.setBackgroundColor(0xfff8f8f8);
					mTextView.setText("");
				}

				mTextView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						chooseNum = -1;
						choosePos = (Integer) (arg0.getTag());
						notifyDataSetChanged();
						int num = list.get(position);
						notifyOptionTvChanged(num);
					}
				});
			} else {
				mTextView.setBackgroundColor(0xffc8c8c8);
			}
			DisplayMetrics metrics = getApplicationContext().getResources()
					.getDisplayMetrics();
			int height = (int) Math.floor(metrics.widthPixels) / 9;
			mTextView.setHeight(height);
			if(position % 3 == 2 && position % 9 != 8){
				mTextView.setCompoundDrawablesWithIntrinsicBounds(
						0,
						0,
						R.drawable.img_div_vertial_bold_line,
						R.drawable.img_horizontal_line_bg);	
				if(position == 20 || position == 47 || position == 23 || position == 50){
					mTextView.setCompoundDrawablesWithIntrinsicBounds(
							0,
							0,
							R.drawable.img_div_vertial_bold_line,
							R.drawable.img_div_horizontal_bold_line);	
				}
			}else if((position > 17 && position < 27) || (position > 44 && position < 54)){
				mTextView.setCompoundDrawablesWithIntrinsicBounds(
						0,
						0,
						R.drawable.img_vertical_line_bg,
						R.drawable.img_div_horizontal_bold_line);	
			}else{
				mTextView.setCompoundDrawablesWithIntrinsicBounds(
						0,
						0,
						R.drawable.img_vertical_line_bg,
						R.drawable.img_horizontal_line_bg);
			}
			

			return view;
		}

	}

	public void notifyOptionTvChanged(int num) {
		Random random = new Random();
		int rightPos = random.nextInt(5);
		optionNum[rightPos].setText(num + "");
		ArrayList<Integer> vlist = new ArrayList<Integer>();
		vlist.add(num);
		for (int i = 0; i < 5; i++) {
			int next = random.nextInt(9) + 1;
			while (vlist.contains(Integer.valueOf(next))) {
				next = random.nextInt(9) + 1;
			}
			vlist.add(next);
			if (i != rightPos) {
				optionNum[i].setText(next + "");
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv1:
		case R.id.tv2:
		case R.id.tv3:
		case R.id.tv4:
		case R.id.tv5:
			if (adapter.getChoosePos() != -1) {
				int chooseNum = Integer.parseInt((String) ((TextView) v)
						.getText());
				adapter.setChooseNum(chooseNum);
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	public void win() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setMessage("Congratulations, you win the game!!!");
		builder.setTitle("Win");
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GamePanelActivity.this.finish();
					}
				});
		builder.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GamePanelActivity.this.finish();
					}
				});
		Dialog mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public void refreshErrorTimes() {
		if (errorTims == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setMessage("Sorry, you lose the game!!!");
			builder.setTitle("Failed");
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							GamePanelActivity.this.finish();
						}
					});
			builder.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							GamePanelActivity.this.finish();
						}
					});
			Dialog mAlertDialog = builder.create();
			mAlertDialog.show();
		} else {
			errorTims--;
			handler.sendEmptyMessage(MSG_REFRESH_TIMES);
		}

	}

}
