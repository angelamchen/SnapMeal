package com.achen.recipeGenerator.service;

import org.springframework.stereotype.Service;

@Service("sequenceGeneratorService")
public interface SequenceGeneratorService {

	public long generateSequence(String seqName);
	
}
