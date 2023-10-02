package com.colatina.app.service.core.domain;

import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class WalletDomain {

    private Integer id;

    @NotNull
    private BigDecimal balance;

}
