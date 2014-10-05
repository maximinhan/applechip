package com.applechip.core.web.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {
  private static final long serialVersionUID = -1985265789913809046L;

  public CustomObjectMapper() {
    super();
    this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.TRUE);
  }
}
