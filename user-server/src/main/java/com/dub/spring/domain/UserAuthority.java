package com.dub.spring.domain;

public class UserAuthority {

	private String authority;

    public UserAuthority() { }

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
