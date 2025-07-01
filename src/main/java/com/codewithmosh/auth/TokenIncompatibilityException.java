package com.codewithmosh.auth;

public class TokenIncompatibilityException extends RuntimeException {
    public TokenIncompatibilityException(String s) {
        super(s);
    }
}
