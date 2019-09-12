package com.gliesereum.karma.service.client.impl;

import com.gliesereum.karma.model.entity.client.ClientEntity;
import com.gliesereum.karma.model.repository.jpa.client.ClientRepository;
import com.gliesereum.karma.service.client.ClientService;
import com.gliesereum.share.common.converter.DefaultConverter;
import com.gliesereum.share.common.model.dto.karma.client.ClientDto;
import com.gliesereum.share.common.service.auditable.impl.AuditableServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author yvlasiuk
 * @version 1.0
 */

@Slf4j
@Service
public class ClientServiceImpl extends AuditableServiceImpl<ClientDto, ClientEntity> implements ClientService {

    private static final Class<ClientDto> DTO_CLASS = ClientDto.class;
    private static final Class<ClientEntity> ENTITY_CLASS = ClientEntity.class;

    private final ClientRepository clientRepository;

    @Value("${image-url.user.avatar}")
    private String defaultUserAvatar;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, DefaultConverter defaultConverter) {
        super(clientRepository, defaultConverter, DTO_CLASS, ENTITY_CLASS);
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto create(ClientDto dto) {
        setLogoIfNull(dto);
        return super.create(dto);
    }

    @Override
    public ClientDto update(ClientDto dto) {
        setLogoIfNull(dto);
        return super.update(dto);
    }

    private void setLogoIfNull(ClientDto user) {
        if (user != null) {
            if (user.getAvatarUrl() == null) {
                user.setAvatarUrl(defaultUserAvatar);
            }
        }
    }
}