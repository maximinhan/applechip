package com.applechip.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.applechip.core.abstact.AbstractObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysema.query.annotations.QueryProjection;

@Data
@EqualsAndHashCode(callSuper = false, of = { "code", "value" })
@NoArgsConstructor
public class CodeValue extends AbstractObject implements Comparable<CodeValue> {

	private static final long serialVersionUID = -7016161450074984593L;

	@QueryProjection
	public CodeValue(final String code, final String value) {
		this.code = code;
		this.value = value;
	}

	@QueryProjection
	public CodeValue(final String code, final Integer value) {
		this.code = code;
		this.value = value.toString();
	}

	@JsonProperty("code")
	private String code;

	@JsonProperty("value")
	private String value;

	public int compareTo(CodeValue codeValue) {
		return this.getCode().compareTo(codeValue.getCode());
	}
}
