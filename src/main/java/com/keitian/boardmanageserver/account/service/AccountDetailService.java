package com.keitian.boardmanageserver.account.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keitian.boardmanageserver.account.dao.AccountRepositry;
import com.keitian.boardmanageserver.account.dto.AccountDTO;
import com.keitian.boardmanageserver.global.AuthorityEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {
    private final AccountRepositry accountRepositry;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginID) throws UsernameNotFoundException {
        return accountRepositry.findOneWithAuthoritiesByLoginID(loginID)
                .map(account -> createUser(loginID, account))
                .orElseThrow(() -> new UsernameNotFoundException(loginID + "->Not Exist"));
    }

    private User createUser(String loginId, AccountDTO accountDTO) {

        List<GrantedAuthority> grantedAuthorities = getAuthSet(accountDTO)
                .stream().map(auth -> new SimpleGrantedAuthority(auth))
                .collect(Collectors.toList());

        return new User(accountDTO.getAccount(),
                accountDTO.getPassword(),
                grantedAuthorities);
    };

    private Set<String> getAuthSet(AccountDTO accountDTO) {
        Set<String> auth = new HashSet<>();
        AuthorityEnum[] authority = AuthorityEnum.values();
        for (AuthorityEnum authorityEnum : authority) {
            if ((authorityEnum.getAuthLvl() | accountDTO.getRole()) == authorityEnum.getAuthLvl())
                auth.add(authorityEnum.name());
        }
        return auth;
    }

}
