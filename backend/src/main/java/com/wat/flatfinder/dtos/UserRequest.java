package com.wat.flatfinder.dtos;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String email;
    private String preffered_district;
    private String password;

}
