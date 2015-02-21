/**
 * HomeWork Assignment 1
 * File Name: MainActivity.java
 * Names:
 * 	Sushmitha Yalla
 * 	Udipta Roy	
 * 	Peter Oram
 *
 */

package com.example.selectiongame;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView1;
	private GridView gridView1;
	private int randomItemCount;
	private String[] items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView1 = (TextView) findViewById(R.id.textView1);
		gridView1 = (GridView) findViewById(R.id.gridView1);

		init();

		Button resetBtn = (Button) findViewById(R.id.button1);
		resetBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				init();
			}
		});

		Button exitBtn = (Button) findViewById(R.id.button2);
		exitBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void init() {
		items = getResources().getStringArray(R.array.items);

		Random rand = new Random();
		int randomItem = rand.nextInt(items.length);
		randomItemCount = rand.nextInt(8) + 1;
		final PicAdapter picAdapter = new PicAdapter(this, randomItem,
				randomItemCount);
		textView1.setText("Find All " + textOfTextView(items[randomItem]) + " (" + randomItemCount + ")");
		gridView1.setAdapter(picAdapter);
	}

	public TextView getTextView1() {
		return textView1;
	}
	
	public String textOfTextView(String itemText){
		if (randomItemCount > 1) {
			if(itemText.equals(items[0]) ||
					itemText.equals(items[1]) ||
					itemText.equals(items[2])){
			itemText = itemText + "s";
			}
			if(itemText.equals(items[3]) || itemText.equals(items[5])){
				itemText = itemText + "es";
			}
			if(itemText.equals(items[4])){
				itemText = itemText.replace("y", "ies");
			}			
		}
		return itemText;
	}
}
