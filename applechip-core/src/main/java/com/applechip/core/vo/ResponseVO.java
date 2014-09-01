package com.applechip.core.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

import com.applechip.core.abstact.AbstractObject;
import com.applechip.core.constant.ReturnCodeConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "result")
@Getter
@Setter
public class ResponseVO extends AbstractObject {

  private static final long serialVersionUID = 6206768885561511703L;

  @XmlElement(name = "code")
  @JsonProperty("code")
  private String code;

  @XmlElement(name = "message")
  @JsonProperty("message")
  private String message;

  @XmlElement(name = "debugMessage")
  @JsonProperty("debugMessage")
  private String debugMessage;

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

  public ResponseVO() {
    this(null, null, null);
  }

  private ResponseVO(String code) {
    this(code, null, null);
  }

  private ResponseVO(String code, String message) {
    this(code, message, null);
  }

  private ResponseVO(String code, String message, String debugMessage) {
    this.code = code;
    this.message = message;
    this.debugMessage = debugMessage;
  }

  public static ResponseVO getResponseVO() {
    return new ResponseVO(ReturnCodeConstant.OK);
  }

  public static ResponseVO getResponseVO(String code) {
    return new ResponseVO(code);
  }

  public static ResponseVO getResponseVO(String code, String message) {
    return new ResponseVO(code, message);
  }

  @Getter
  @Setter
  public static class Error extends AbstractObject {

    private static final long serialVersionUID = -4630723838834739897L;

    @JsonProperty("field")
    private String field;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public Error() {}

    public Error(String field, String errorMessage) {
      this.field = field;
      this.errorMessage = errorMessage;
    }
  }
}
