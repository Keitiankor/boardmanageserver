package com.keitian.boardmanageserver.global;

import lombok.Getter;

@Getter
public enum AuthorityEnum {
    ROLE_USER(1),
    ROLE_MANAGER(128),
    ROLE_ADMIN(256),
    ;

    private final int authLvl;

    AuthorityEnum(int authLvl) {
        this.authLvl = authLvl;
    }
}
