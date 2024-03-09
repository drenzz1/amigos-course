package com.foo;

public record CustomerUpdateRequest (
        String name,String email,Integer age
){
}
