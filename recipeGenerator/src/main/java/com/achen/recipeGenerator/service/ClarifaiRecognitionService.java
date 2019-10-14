package com.achen.recipeGenerator.service;

import java.util.List;

import clarifai2.dto.prediction.Concept;

public interface ClarifaiRecognitionService {
	public List<Concept> sendImageToClarifaiSync(byte[] image);
}
