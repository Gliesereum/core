package com.gliesereum.karma.service.chat.impl;

import com.gliesereum.karma.model.entity.chat.ChatEntity;
import com.gliesereum.karma.model.repository.jpa.chat.ChatRepository;
import com.gliesereum.karma.service.business.BaseBusinessService;
import com.gliesereum.karma.service.chat.ChatService;
import com.gliesereum.share.common.converter.DefaultConverter;
import com.gliesereum.share.common.exception.client.ClientException;
import com.gliesereum.share.common.model.dto.karma.chat.ChatDto;
import com.gliesereum.share.common.service.DefaultServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.gliesereum.share.common.exception.messages.KarmaExceptionMessage.BUSINESS_ID_EMPTY;
import static com.gliesereum.share.common.exception.messages.KarmaExceptionMessage.DONT_HAVE_PERMISSION_TO_ACTION_BUSINESS;

/**
 * @author vitalij
 * @version 1.0
 */
@Slf4j
@Service
public class ChatServiceImpl extends DefaultServiceImpl<ChatDto, ChatEntity> implements ChatService {

    private final ChatRepository repository;

    private static final Class<ChatDto> DTO_CLASS = ChatDto.class;
    private static final Class<ChatEntity> ENTITY_CLASS = ChatEntity.class;

    public ChatServiceImpl(ChatRepository repository, DefaultConverter defaultConverter) {
        super(repository, defaultConverter, DTO_CLASS, ENTITY_CLASS);
        this.repository = repository;
    }

    @Autowired
    private BaseBusinessService businessService;

    @Override
    public List<ChatDto> getAllByUserId(UUID userId) {
        List<ChatEntity> entities = repository.getAllByUserId(userId);
        return converter.convert(entities, dtoClass);
    }

    @Override
    public ChatDto getByUserIdAndBusinessId(UUID userId, UUID businessId) {
        ChatEntity entity = repository.getByUserIdAndBusinessId(userId, businessId);
        return converter.convert(entity, dtoClass);
    }

    @Override
    public List<ChatDto> getAllByBusinessId(UUID businessId) {
        if (businessId == null) {
            throw new ClientException(BUSINESS_ID_EMPTY);
        }
        if (!businessService.currentUserHavePermissionToActionInBusinessLikeOwner(businessId)) {
            throw new ClientException(DONT_HAVE_PERMISSION_TO_ACTION_BUSINESS);
        }
        List<ChatEntity> entities = repository.getAllByBusinessId(businessId);
        return converter.convert(entities, dtoClass);
    }
}