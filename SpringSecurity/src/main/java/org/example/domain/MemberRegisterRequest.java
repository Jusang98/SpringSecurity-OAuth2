package org.example.domain;

import lombok.Data;

@Data
public class MemberRegisterRequest {
    private String id;
    private String name;
    private String pwd;

}
