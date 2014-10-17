package com.applechip.core.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.applechip.core.constant.ReturnConstant;
import com.applechip.core.object.AbstractObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class ResponseVO extends AbstractObject {
  private static final long serialVersionUID = 6206768885561511703L;
  @XmlElement(name = "code")
  @JsonProperty("code")
  private String code;
  @XmlElement(name = "message")
  @JsonProperty("message")
  private String message;
  @XmlElement(name = "debug")
  @JsonProperty("debug")
  private String debug;

  @JsonProperty("formName")
  private String formName;
  @JsonProperty("errors")
  private List<Error> errors;

  @XmlTransient
  @JsonIgnore
  private String sessionkey;
  @XmlTransient
  @JsonIgnore
  private String tracekey;

  private ResponseVO(String code) {
    this(code, null);
  }

  private ResponseVO(String code, String message) {
    this(code, message, null);
  }

  private ResponseVO(String code, String message, String debug) {
    this.code = code;
    this.message = message;
    this.debug = debug;
  }

  public static ResponseVO getResponseVO() {
    return new ResponseVO(ReturnConstant.OK);
  }

  public static ResponseVO getResponseVO(String code) {
    return new ResponseVO(code);
  }

  public static ResponseVO getResponseVO(String code, String message) {
    return new ResponseVO(code, message);
  }

  public static ResponseVO getResponseVO(String code, String message, String debug) {
    return new ResponseVO(code, message, debug);
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Error extends AbstractObject {
    private static final long serialVersionUID = -4630723838834739897L;
    @JsonProperty("field")
    private String field;
    @JsonProperty("message")
    private String message;
  }
}
