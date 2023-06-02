package com.keitian.boardmanageserver.account.dao;

import java.util.Optional;

import com.keitian.boardmanageserver.account.dto.AccountDTO;

public interface AccountRepositry {
    Optional<AccountDTO> findOneWithAuthoritiesByLoginID(String loginID);
}
