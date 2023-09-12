package com.basics.securing.utils;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.basics.securing.utils.UtilityMainTest.PATHRESOURCE_TEST;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.basics.securing.utils.UtilityMain.EOL;

class ApacheAvro {

	@Test void testAvroSchema( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String avroSchemaFile = PATHRESOURCE_TEST + "user.avsc";
		String avroObjectFile = PATHRESOURCE_TEST + "user.avro";

		try {
			Schema schema = new Schema.Parser().parse(new File(avroSchemaFile));

			// generate
			GenericRecord user1 = new GenericData.Record(schema);
			user1.put("name", "Amy");
			user1.put("favorite_number", 256);

			GenericRecord user2 = new GenericData.Record(schema);
			user2.put("name", "Ben");
			user2.put("favorite_number", 7);
			user2.put("favorite_color", "red");

			// Serialize
			File file = new File(avroObjectFile);
			DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
			DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
			dataFileWriter.create(schema, file);
			dataFileWriter.append(user1);
			dataFileWriter.append(user2);
			dataFileWriter.close();

			// Deserialize
			DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
			DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader);
			GenericRecord genericRecord = null;
			while ( dataFileReader.hasNext() ) {
				genericRecord = dataFileReader.next(genericRecord);
				stringBuilder.append("\t").append(genericRecord).append(EOL);
			}
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}
}
