package com.basics.dbsqlite;

import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class DbMongo {

	public static final int PORT = 27017;
	public static final String HOST = "localhost";
	public static final String FIELD_EMAIL = "email";
	
	public Map<String, Object> doWork(String txtVal) {

		Map<String, Object> map = new HashMap<>();
		map.put("keyAny", "valAny " + txtVal);
		return map;
	}

	public static String doWorkStatic(String txtVal) {
		return "hello " + txtVal;
	}

	public String getUserRecord(String dbName, String collectionNAme, String email) {

		Document document = null;

		MongoClient mongoClient = new MongoClient(HOST, PORT);
		MongoDatabase mongoDb = mongoClient.getDatabase(dbName);
		MongoCollection<Document> mongoCollection = mongoDb.getCollection(collectionNAme);
		Bson bsonCompare = eq(FIELD_EMAIL, email);
		
		document = mongoCollection.find(bsonCompare).first();
		mongoClient.close();
		return document.toJson();
	}
}