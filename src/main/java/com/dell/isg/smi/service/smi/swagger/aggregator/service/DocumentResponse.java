/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.service.smi.swagger.aggregator.service;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "service", "version", "href" })
public class DocumentResponse {

	public DocumentResponse(String service, String version, String href) {
		super();
		this.service = service;
		this.version = version;
		this.href = href;
	}

	@JsonProperty("service")
	private String service;
	@JsonProperty("version")
	private String version;
	@JsonProperty("href")
	private String href;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("service")
	public String getService() {
		return service;
	}

	@JsonProperty("service")
	public void setService(String service) {
		this.service = service;
	}

	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	@JsonProperty("version")
	public void setVersion(String version) {
		this.version = version;
	}

	@JsonProperty("href")
	public String getHref() {
		return href;
	}

	@JsonProperty("href")
	public void setHref(String href) {
		this.href = href;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}