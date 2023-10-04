package com.colatina.app.service.core.domain;

import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class AccountDomain {

    private Integer id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 100)
    private String lastName;

    @NotNull
    @Size(min = 11, max = 11)
    private String document;

    @NotNull
    private AccountStatus status;

    @NotNull
    @Past
    private LocalDate birthDate;

    private WalletDomain wallet;

    public boolean checkAge() {
        int years = Period.between(this.getBirthDate(), LocalDate.now()).getYears();
        return years >= 18;
    }

    public boolean isActive(){
        return this.status.equals(AccountStatus.ACTIVE);
    }

}
