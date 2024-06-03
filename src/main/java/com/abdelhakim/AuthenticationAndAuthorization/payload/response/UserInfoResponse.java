package com.abdelhakim.AuthenticationAndAuthorization.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserInfoResponse {
	private Long id;
	private String email;
	private List<String> roles;

	public UserInfoResponse(Long id, String email, List<String> roles) {
		this.id = id;
		this.email = email;
		this.roles = roles;
	}
}
