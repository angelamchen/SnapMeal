package com.achen.recipeGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import okhttp3.OkHttpClient;

@Configuration
public class ClarifaiReognitionConfig {
	
	@Bean
	public ClarifaiClient clarifaiClient() {
		return new ClarifaiBuilder("1100f3670e8f45c78bfaffec39c36deb")
				.client(new OkHttpClient())
				.buildSync();
	}
}
