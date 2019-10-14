package com.achen.recipeGenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

@Service
public class ClarifaiRecognitionServiceImpl implements ClarifaiRecognitionService {
	@Autowired
	ClarifaiClient client;
	
	@Override
	public List<Concept> sendImageToClarifaiSync(byte[] image) {
		Model<Concept> foodModel = client.getDefaultModels().foodModel();
		PredictRequest<Concept> request = foodModel.predict().withInputs(
		        ClarifaiInput.forImage(image));
		List<ClarifaiOutput<Concept>> results = request.executeSync().get();
		
		return results.get(0).data();
	}
	
}
