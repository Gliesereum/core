package com.gliesereum.karma.controller.carwash;

import com.gliesereum.karma.service.carwash.CarWashService;
import com.gliesereum.karma.service.comment.CommentService;
import com.gliesereum.karma.service.media.MediaService;
import com.gliesereum.share.common.exception.client.ClientException;
import com.gliesereum.share.common.model.dto.karma.carwash.CarWashDto;
import com.gliesereum.share.common.model.dto.karma.comment.CommentDto;
import com.gliesereum.share.common.model.dto.karma.comment.RatingDto;
import com.gliesereum.share.common.model.dto.karma.media.MediaDto;
import com.gliesereum.share.common.model.response.MapResponse;
import com.gliesereum.share.common.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.gliesereum.share.common.exception.messages.KarmaExceptionMessage.*;

/**
 * @author vitalij
 * @version 1.0
 * @since 12/7/18
 */
@RestController
@RequestMapping("/carwash")
public class CarWashController {

    @Autowired
    private CarWashService carWashService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CarWashDto> getAll() {
        return carWashService.getAll();
    }

    @GetMapping("/{id}")
    public CarWashDto getById(@PathVariable("id") UUID id) {
        return carWashService.getById(id);
    }

    @PostMapping
    public CarWashDto create(@RequestBody CarWashDto carWash) {
        return carWashService.create(carWash);
    }

    @PutMapping
    public CarWashDto update(@RequestBody CarWashDto carWash) {
        return carWashService.update(carWash);
    }

    @DeleteMapping("/{id}")
    public MapResponse delete(@PathVariable("id") UUID id) {
        carWashService.delete(id);
        return new MapResponse("true");
    }

    @GetMapping("/{id}/media")
    public List<MediaDto> getMediaByCarWash(@PathVariable("id") UUID id) {
        return mediaService.getByObjectId(id);
    }

    @PostMapping("/media")
    public MediaDto create(@RequestBody @Valid MediaDto media) {
        if (carWashService.currentUserHavePermissionToAction(media.getObjectId())) {
            return mediaService.create(media);
        } else {
            throw new ClientException(DONT_HAVE_PERMISSION_TO_ACTION_CARWASH);
        }
    }

    @PutMapping("/media")
    public MediaDto update(@RequestBody @Valid MediaDto media) {
        if (carWashService.currentUserHavePermissionToAction(media.getObjectId())) {
            return mediaService.update(media);
        } else {
            throw new ClientException(DONT_HAVE_PERMISSION_TO_ACTION_CARWASH);
        }
    }

    @DeleteMapping("/{id}/media/{mediaId}")
    public MapResponse delete(@PathVariable("id") UUID carWashId, @PathVariable("mediaId") UUID mediaId) {
        if (carWashService.currentUserHavePermissionToAction(carWashId)) {
            mediaService.delete(mediaId, carWashId);
        } else {
            throw new ClientException(DONT_HAVE_PERMISSION_TO_ACTION_CARWASH);
        }
        return new MapResponse("true");
    }

    @GetMapping("/{id}/rating")
    public RatingDto getRating(@PathVariable("id") UUID id) {
        return commentService.getRating(id);
    }

    @GetMapping("/{id}/comment")
    public List<CommentDto> getCommentByCarWash(@PathVariable("id") UUID id) {
        return commentService.findByObjectId(id);
    }

    @PostMapping("/{id}/comment")
    public CommentDto addComment(@PathVariable("id") UUID id,
                                 @RequestBody @Valid CommentDto comment) {
        UUID userId = SecurityUtil.getUserId();
        if (userId == null) {
            throw new ClientException(ANONYMOUS_CANT_COMMENT);
        }
        if (!mediaService.isExist(id)) {
            throw new ClientException(CARWASH_NOT_FOUND);
        }
        return commentService.addComment(id, userId, comment);
    }

    @PutMapping("/comment")
    public CommentDto updateComment(@RequestBody @Valid CommentDto comment) {
        UUID userId = SecurityUtil.getUserId();
        if (userId == null) {
            throw new ClientException(ANONYMOUS_CANT_COMMENT);
        }
        return commentService.updateComment(userId, comment);
    }

    @DeleteMapping("/comment/{commentId}")
    public MapResponse deleteComment(@PathVariable("commentId") UUID commentId) {
        UUID userId = SecurityUtil.getUserId();
        if (userId == null) {
            throw new ClientException(ANONYMOUS_CANT_COMMENT);
        }
        commentService.deleteComment(commentId, userId);
        return new MapResponse("true");
    }

}
