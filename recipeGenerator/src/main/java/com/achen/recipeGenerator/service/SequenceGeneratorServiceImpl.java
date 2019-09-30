package com.achen.recipeGenerator.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.DatabaseSequence;

@Service("sequenceGeneratorService")
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {
	
	@Autowired
	MongoTemplate mongoOperations;

	@Override
	public long generateSequence(String seqName) {
		Query query = new Query(Criteria.where("_id").is(seqName));
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true).upsert(true);
		
		DatabaseSequence counter = mongoOperations.findAndModify(query,
				new Update().inc("seq", 1), options, DatabaseSequence.class);
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
}
