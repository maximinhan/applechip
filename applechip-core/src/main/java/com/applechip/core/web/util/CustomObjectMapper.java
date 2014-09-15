package com.applechip.core.web.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//for using @JsonIgnoreProperties(ignoreUnknown = true)

/*
 * String body = {\"resultCode\":1000,\"resultMessage\":\"test\",\"siteId\":1111}

 @Data
 public class ConstraintData {
 private int siteId;	
 }

 ObjectMapper mapper = new ObjectMapper();
 ConstraintData data = mapper.readValue(body, ConstraintData.class);
 */
public class CustomObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = -1985265789913809046L;

	public CustomObjectMapper() {
		super();
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
