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
    @NotNull
    private AccountStatus status;

}
