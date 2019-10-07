package com.achen.recipeGenerator.models;

public class ImageRequestDto {
	String imageString;

	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	@Override
	public String toString() {
		return "ImageRequestDto [imageString=" + imageString + "]";
	}
}
