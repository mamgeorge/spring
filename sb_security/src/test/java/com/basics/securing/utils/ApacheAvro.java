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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.basics.securing.utils.UtilityMain.EOL;

public class ApacheAvro {

	public static final String TESTPATH = "src/test/resources/";

	@Test void testAvroSchema( ) {

		String txtLines = "";
		String avroSchemaFile = TESTPATH + "user.avsc";
		String avroObjectFile = TESTPATH + "user.avro";

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
				txtLines += "\t" + genericRecord + EOL;
			}
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}
}
