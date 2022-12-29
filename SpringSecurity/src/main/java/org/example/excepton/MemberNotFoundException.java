package org.example.excepton;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String memberId){
        super("member not found: member_id"+ memberId);
    }
}
