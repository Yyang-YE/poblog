package com.project.poblog.domain.user;

import com.project.poblog.global.exception.GlobalException;
import com.project.poblog.global.response.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {

	ADMIN("관리자"),
	MEMBER("일반회원");

	private final String label;

	public static Role map(String label) {
		return Arrays.stream(values())
			.filter(r -> r.label.equals(label))
			.findAny().orElse(null);
	}
}
