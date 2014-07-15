package com.applechip.core.abstact;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.applechip.core.constant.ReturnCodeConstant;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "RESULT")
public class ResponseVO extends AbstractObject {

  private static final long serialVersionUID = 6206768885561511703L;

  @XmlElement(name = "RETURN_CODE")
  @JsonProperty("returnCode")
  private String returnCode;

  @XmlElement(name = "MESSAGE")
  @JsonProperty("message")
  private String message;

  @XmlElement(name = "DEBUG_MESSAGE")
  @JsonProperty("debugMessage")
  private String debugMessage;

  public ResponseVO() {}

  public ResponseVO(String returnCode) {
    this.returnCode = returnCode;
  }

  public ResponseVO(String returnCode, String message) {
    this.returnCode = returnCode;
    this.message = message;
  }

  public ResponseVO(String returnCode, String message, String debugMessage) {
    this.returnCode = returnCode;
    this.message = message;
    this.debugMessage = debugMessage;
  }

  public static ResponseVO ok() {
    return new ResponseVO(ReturnCodeConstant.OK);
  }

  public static ResponseVO getResponseVO(String returnCode) {
    return new ResponseVO(returnCode);
  }

  public static ResponseVO getResponseVO(String returnCode, String message) {
    return new ResponseVO(returnCode, message);
  }

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getDebugMessage() {
    return debugMessage;
  }

  public void setDebugMessage(String debugMessage) {
    this.debugMessage = debugMessage;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
  
}
