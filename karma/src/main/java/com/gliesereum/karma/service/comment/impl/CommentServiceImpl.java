package com.gliesereum.karma.service.comment.impl;

import com.gliesereum.karma.model.entity.comment.CommentEntity;
import com.gliesereum.karma.model.repository.jpa.comment.CommentRepository;
import com.gliesereum.karma.service.comment.CommentService;
import com.gliesereum.share.common.converter.DefaultConverter;
import com.gliesereum.share.common.exception.client.ClientException;
import com.gliesereum.share.common.exchange.service.account.UserExchangeService;
import com.gliesereum.share.common.model.dto.account.user.UserDto;
import com.gliesereum.share.common.model.dto.karma.comment.CommentDto;
import com.gliesereum.share.common.model.dto.karma.comment.CommentFullDto;
import com.gliesereum.share.common.model.dto.karma.comment.RatingDto;
import com.gliesereum.share.common.service.DefaultServiceImpl;
import com.gliesereum.share.common.util.SecurityUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gliesereum.share.common.exception.messages.CommonExceptionMessage.ID_NOT_SPECIFIED;
import static com.gliesereum.share.common.exception.messages.CommonExceptionMessage.USER_IS_ANONYMOUS;
import static com.gliesereum.share.common.exception.messages.KarmaExceptionMessage.*;

/**
 * @author yvlasiuk
 * @version 1.0
 */

@Service
public class CommentServiceImpl extends DefaultServiceImpl<CommentDto, CommentEntity> implements CommentService {

    private static final Class<CommentDto> DTO_CLASS = CommentDto.class;
    private static final Class<CommentEntity> ENTITY_CLASS = CommentEntity.class;

    private final CommentRepository commentRepository;

    @Autowired
    private UserExchangeService userExchangeService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, DefaultConverter defaultConverter) {
        super(commentRepository, defaultConverter, DTO_CLASS, ENTITY_CLASS);
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDto> findByObjectId(UUID objectId) {
        List<CommentDto> result = null;
        if (objectId != null) {
            List<CommentEntity> entities = commentRepository.findByObjectIdOrderByDateCreatedDesc(objectId);
            result = converter.convert(entities, dtoClass);
        }
        return result;
    }

    @Override
    public List<CommentFullDto> findFullByObjectId(UUID objectId) {
        List<CommentFullDto> result = null;
        if (objectId != null) {
            List<CommentEntity> entities = commentRepository.findByObjectIdOrderByDateCreatedDesc(objectId);
            result = converter.convert(entities, CommentFullDto.class);
            setUserInfo(result);
        }
        return result;
    }

    @Override
    public CommentFullDto findByObjectIdForCurrentUser(UUID objectId) {
        CommentFullDto result = null;
        if (SecurityUtil.isAnonymous()) {
            throw new ClientException(USER_IS_ANONYMOUS);
        }
        UUID userId = SecurityUtil.getUserId();
        if (objectId != null) {
            CommentEntity entity = commentRepository.findByObjectIdAndOwnerId(objectId, userId);
            result = converter.convert(entity, CommentFullDto.class);
            if (result != null) {
                setUserInfo(Arrays.asList(result));
            }
        }
        return result;
    }

    @Override
    public CommentDto addComment(UUID objectId, UUID userId, CommentDto comment) {
        if (commentRepository.existsByObjectIdAndOwnerId(objectId, userId)) {
            throw new ClientException(COMMENT_FOR_USER_EXIST);
        }
        comment.setOwnerId(userId);
        comment.setObjectId(objectId);
        comment.setDateCreated(LocalDateTime.now());
        return create(comment);
    }

    @Override
    public CommentDto updateComment(UUID userId, CommentDto comment) {
        UUID id = comment.getId();
        if (id == null) {
            throw new ClientException(ID_NOT_SPECIFIED);
        }
        CommentDto existed = super.getById(id);
        if (!existed.getOwnerId().equals(userId)) {
            throw new ClientException(CURRENT_USER_CANT_EDIT_THIS_COMMENT);
        }
        existed.setRating(comment.getRating());
        existed.setText(comment.getText());
        existed.setDateCreated(LocalDateTime.now());
        return update(existed);
    }

    @Override
    public void deleteComment(UUID commentId, UUID userId) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        CommentEntity existed = commentOptional.orElseThrow(() -> new ClientException(COMMENT_NOT_FOUND));
        if (!existed.getOwnerId().equals(userId)) {
            throw new ClientException(CURRENT_USER_CANT_EDIT_THIS_COMMENT);
        }
        commentRepository.delete(existed);
    }

    @Override
    public RatingDto getRating(UUID objectId) {
        RatingDto rating = new RatingDto();
        List<CommentEntity> comments = commentRepository.findByObjectId(objectId);
        if (CollectionUtils.isNotEmpty(comments)) {
            int count = comments.size();
            int ratingSum = comments.stream().mapToInt(CommentEntity::getRating).sum();
            BigDecimal ratingAvg = new BigDecimal(ratingSum).divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_DOWN);
            rating.setCount(count);
            rating.setRating(ratingAvg);
        } else {
            rating.setCount(0);
            rating.setRating(new BigDecimal(0));
        }
        return rating;
    }

    private void setUserInfo(List<CommentFullDto> comments) {
        if(CollectionUtils.isNotEmpty(comments)) {
            List<UUID> ownerIds = comments.stream().map(CommentFullDto::getOwnerId).collect(Collectors.toList());
            List<UserDto> users = userExchangeService.findByIds(ownerIds);
            if (CollectionUtils.isNotEmpty(users)) {
                comments.forEach(i -> {
                    for (UserDto user : users) {
                        if (user.getId().equals(i.getOwnerId())) {
                            i.setFirstName(user.getFirstName());
                            i.setLastName(user.getLastName());
                            i.setMiddleName(user.getMiddleName());
                            break;
                        }
                    }
                });

            }
        }
    }
}
