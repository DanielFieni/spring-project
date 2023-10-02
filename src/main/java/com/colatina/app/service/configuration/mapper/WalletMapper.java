package com.colatina.app.service.configuration.mapper;

import com.colatina.app.service.core.domain.WalletDomain;
import com.colatina.app.service.dataprovider.entity.WalletEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper extends EntityMapper<WalletDomain, WalletEntity> {
}
