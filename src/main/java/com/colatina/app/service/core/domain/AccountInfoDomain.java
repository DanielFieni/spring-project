package com.colatina.app.service.core.domain;

import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountInfoDomain {

    private Integer id;
    private String name;
    private String lastName;
    private WalletDomain wallet;
    private AccountStatus status;

    public boolean isActive(){
        return this.status.equals(AccountStatus.ACTIVE);
    }

}
