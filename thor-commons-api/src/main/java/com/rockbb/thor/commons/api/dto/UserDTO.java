package com.rockbb.thor.commons.api.dto;

public class UserDTO extends BaseUserDTO {

    public UserDTO anonymous() {
        super.anonymous();
        return this;
    }

    public void mask() {
        super.mask();
    }

}