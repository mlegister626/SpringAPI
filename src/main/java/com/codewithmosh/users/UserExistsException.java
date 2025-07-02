package com.codewithmosh.users;

public class UserExistsException extends  RuntimeException{
    public UserExistsException(String userExists){
        super(userExists);
    }
}
