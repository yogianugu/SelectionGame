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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PicAdapter extends BaseAdapter {

	private static int MATRIX_SIZE = 25;
	private ArrayList<SingleItem> itemList;
	private Context context;
	private int randomItem, randomItemCount;
	private GridView gridView;
	private String selectedItem;
	private String itemText;
	private ArrayList<Integer> lockedPositions = new ArrayList<Integer>();

	public PicAdapter(Context context, int randomItem, int randomItemCount) {
		this.context = context;
		this.randomItem = randomItem;
		this.randomItemCount = randomItemCount;
		generateMatrix();
	}

	public void generateMatrix() {
		int[] itemId = { R.drawable.apple, R.drawable.lemon, R.drawable.mango,
				R.drawable.peach, R.drawable.strawberry, R.drawable.tomato };
		String[] itemNames = context.getResources().getStringArray(
				R.array.items);
		ArrayList<Integer> positions = new ArrayList<Integer>();
		selectedItem = itemNames[randomItem];
		if (randomItemCount > 1) {
			itemText = itemText + "s";
		}

		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < MATRIX_SIZE; i++) {
			numbers.add(i);
		}
		Collections.shuffle(numbers);
		for (int j = 0; j < randomItemCount; j++) {
			positions.add(numbers.get(j));
		}

		Random rand = new Random();
		itemList = new ArrayList<SingleItem>();
		for (int j = 0; j < MATRIX_SIZE; j++) {
			if (positions.contains(j)) {
				itemList.add(new SingleItem(itemId[randomItem],
						itemNames[randomItem]));
			} else {
				int itemNumber = rand.nextInt(itemId.length);
				while (itemNumber == randomItem) {
					itemNumber = rand.nextInt(itemId.length);
				}
				itemList.add(new SingleItem(itemId[itemNumber],
						itemNames[itemNumber]));
			}
		}
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int i) {
		return itemList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	class ViewHolder {

		ImageView singleItem;
		// Dialog Box
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		StringBuilder dialogMsg;

		public ViewHolder(View v) {
			singleItem = (ImageView) v.findViewById(R.id.imageView1);

			singleItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					singleItem.setAlpha(0.5f);
					int position = (Integer) singleItem.getTag();
					if (!lockedPositions.contains(position)) {
						if (itemList.get(position).getItemName()
								.equalsIgnoreCase(selectedItem)
								&& !lockedPositions.contains(position)) {
							randomItemCount--;
							lockedPositions.add(position);
							SingleItem singleItem = null;
							Collections.sort(lockedPositions,
									Collections.reverseOrder());
							for (Integer lockPosition : lockedPositions) {
								singleItem = itemList.remove(lockPosition
										.intValue());
							}
							Collections.shuffle(itemList);
							// need to sort in ascending while inserting the
							// values
							Collections.sort(lockedPositions);

							for (Integer lockPosition : lockedPositions) {
								itemList.add(lockPosition, singleItem);
							}
							PicAdapter adapter = (PicAdapter) gridView
									.getAdapter();
							adapter.notifyDataSetChanged();
							itemText = ((MainActivity) context)
									.textOfTextView(selectedItem);
							((MainActivity) context).getTextView1().setText(
									"Find All " + itemText + " ("
											+ randomItemCount + ")");
						} else {
							dialogMsg = new StringBuilder(context
									.getResources().getString(
											R.string.failure_message));
							builder.setMessage(dialogMsg).setTitle(
									R.string.failure_title);
							// Add the buttons
							builder.setPositiveButton(R.string.reset_btn,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.dismiss();
											((MainActivity) context).init();
										}
									});
							builder.setNegativeButton(R.string.exit_btn,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.dismiss();
											((MainActivity) context).finish();
										}
									});
							AlertDialog dialog = builder.create();
							dialog.show();
						}
					}
					if (randomItemCount == 0) {
						dialogMsg = new StringBuilder(context.getResources()
								.getString(R.string.success_message));
						dialogMsg.append(itemText).append("!!");
						builder.setMessage(dialogMsg).setTitle(
								R.string.success_title);
						// Add the buttons
						builder.setPositiveButton(R.string.new_game,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.dismiss();
										((MainActivity) context).init();
									}
								});
						builder.setNegativeButton(R.string.ok_text,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.dismiss();
									}
								});
						AlertDialog dialog = builder.create();
						dialog.show();
					}
				}
			});

		}
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View row = view;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.single_item, viewGroup, false);
			holder = new ViewHolder(row);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		this.gridView = (GridView) viewGroup;
		holder.singleItem.setImageResource(itemList.get(i).getImageId());
		holder.singleItem.setTag(i);
		return row;
	}

	public int getRandomItemCount() {
		return randomItemCount;
	}

}