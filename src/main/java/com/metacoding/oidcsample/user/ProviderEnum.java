package com.metacoding.oidcsample.user;

import lombok.Getter;

@Getter
public enum ProviderEnum {
    LOCAL("local"), KAKAO("kakao"), APPLE("apple");

    ProviderEnum(String value) {
        this.value = value;
    }

    private String value;
}
