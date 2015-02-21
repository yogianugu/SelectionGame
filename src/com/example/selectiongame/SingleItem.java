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

public class SingleItem {

	private int imageId;
	private String itemName;

	public SingleItem(int imageId, String itemName) {
		this.imageId = imageId;
		this.itemName = itemName;
	}

	/**
	 * @return the imageId
	 */
	public int getImageId() {
		return imageId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
}
