package com.colatina.app.service.dataprovider.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "wallet")
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_wallet")
    @SequenceGenerator(name = "seq_wallet", allocationSize = 1, sequenceName = "seq_wallet")
    @Column(name = "id")
    private Integer id;

    @Column(name = "balance")
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

}
