package com.basics.samples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OauthToken {

	String access_token;
	String token_type;
	String expires_in;
	String id_token;
}
