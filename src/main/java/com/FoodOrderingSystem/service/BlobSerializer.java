package com.FoodOrderingSystem.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


	public class BlobSerializer extends JsonSerializer<Blob> {
	    @Override
	    public void serialize(Blob value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
	        try {
	            byte[] bytes = value.getBytes(1, (int) value.length());
	            String base64Encoded = Base64.getEncoder().encodeToString(bytes);
	            gen.writeString(base64Encoded);
	        } catch (SQLException e) {
	            throw new IOException("Error serializing Blob", e);
	        }
	    }
	}
