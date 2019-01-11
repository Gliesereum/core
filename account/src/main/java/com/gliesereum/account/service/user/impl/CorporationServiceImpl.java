package com.gliesereum.account.service.user.impl;

import com.gliesereum.account.model.entity.CorporationEntity;
import com.gliesereum.account.model.repository.jpa.user.CorporationRepository;
import com.gliesereum.account.service.depository.DepositoryService;
import com.gliesereum.account.service.user.CorporationService;
import com.gliesereum.account.service.user.UserCorporationService;
import com.gliesereum.account.service.user.UserService;
import com.gliesereum.share.common.converter.DefaultConverter;
import com.gliesereum.share.common.exception.client.ClientException;
import com.gliesereum.share.common.exchange.service.media.MediaExchangeService;
import com.gliesereum.share.common.model.dto.account.enumerated.KYCStatus;
import com.gliesereum.share.common.model.dto.account.enumerated.VerifiedStatus;
import com.gliesereum.share.common.model.dto.account.user.CorporationDto;
import com.gliesereum.share.common.model.dto.account.user.DepositoryDto;
import com.gliesereum.share.common.model.dto.account.user.UserCorporationDto;
import com.gliesereum.share.common.model.dto.media.UserFileDto;
import com.gliesereum.share.common.service.DefaultServiceImpl;
import com.gliesereum.share.common.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.gliesereum.share.common.exception.messages.CommonExceptionMessage.ID_NOT_SPECIFIED;
import static com.gliesereum.share.common.exception.messages.CommonExceptionMessage.NOT_EXIST_BY_ID;
import static com.gliesereum.share.common.exception.messages.MediaExceptionMessage.UPLOAD_FAILED;
import static com.gliesereum.share.common.exception.messages.UserExceptionMessage.*;

/**
 * @author vitalij
 * @since 12/4/18
 */
@Slf4j
@Service
public class CorporationServiceImpl extends DefaultServiceImpl<CorporationDto, CorporationEntity> implements CorporationService {

    private static final Class<CorporationDto> DTO_CLASS = CorporationDto.class;
    private static final Class<CorporationEntity> ENTITY_CLASS = CorporationEntity.class;

    public CorporationServiceImpl(CorporationRepository repository, DefaultConverter converter) {
        super(repository, converter, DTO_CLASS, ENTITY_CLASS);
    }

    private CorporationRepository repository;

    @Autowired
    private UserCorporationService userCorporationService;

    @Autowired
    private UserService userservice;

    @Autowired
    private DepositoryService depositoryService;

    @Autowired
    private MediaExchangeService mediaExchangeService;

    @Override
    public CorporationDto create(CorporationDto dto) {
        CorporationDto result = null;
        checkUserByStatus();
        dto.setKYCStatus(KYCStatus.KYC_NOT_PASSED);
        dto.setVerifiedStatus(VerifiedStatus.UNVERIFIED);
        dto.setParentCorporationId(null);
        result = super.create(dto);
        if (result != null) {
            userCorporationService.create(new UserCorporationDto(SecurityUtil.getUserId(), result.getId()));
        }
        return result;
    }

    @Override
    public CorporationDto update(CorporationDto dto) {
        if (dto != null) {
            if (dto.getId() == null) {
                throw new ClientException(ID_NOT_SPECIFIED);
            }
            checkCurrentUserForPermissionActionThisCorporation(dto.getId());
            checkByUpdateStatus(dto);
            CorporationDto byId = super.getById(dto.getId());
            if (byId == null) {
                throw new ClientException(NOT_EXIST_BY_ID);
            }
            dto.setParentCorporationId(byId.getParentCorporationId());
            CorporationEntity entity = converter.convert(dto, entityClass);
            entity = repository.saveAndFlush(entity);
            dto = converter.convert(entity, dtoClass);
        }
        return dto;
    }

    @Override
    public void delete(UUID id) {
        checkCurrentUserForPermissionActionThisCorporation(id);
        super.delete(id);
    }

    @Override
    public void addUser(UUID idCorporation, UUID idUser) {
        checkActionWithUserFromCorporation(idCorporation, idUser);
        userCorporationService.create(new UserCorporationDto(idUser, idCorporation));
    }

    @Override
    public void removeUser(UUID idCorporation, UUID idUser) {
        checkActionWithUserFromCorporation(idCorporation, idUser);
        UserCorporationDto userCorporation = userCorporationService.getByUserIdAndCorporationId(idUser, idCorporation);
        if (userCorporation == null) {
            throw new ClientException(DONT_FOUND_DEPENDENCY_USER_AND_CORPORATION);
        }
        userCorporationService.delete(userCorporation.getId());
    }

    @Override
    public void updateKycStatus(KYCStatus status, UUID idCorporation) {
        if (status == null) {
            throw new ClientException(KYC_STATUS_IS_EMPTY);
        }
        if (idCorporation == null) {
            throw new ClientException(CORPORATION_ID_IS_EMPTY);
        }
        CorporationDto corporation = getById(idCorporation);
        if (corporation == null) {
            throw new ClientException(CORPORATION_NOT_FOUND);
        }
        corporation.setKYCStatus(status);
        if (status.equals(KYCStatus.KYC_PASSED)) {
            corporation.setVerifiedStatus(VerifiedStatus.VERIFIED);
        } else {
            corporation.setVerifiedStatus(VerifiedStatus.UNVERIFIED);
        }
        super.update(corporation);
    }

    @Override
    public List<CorporationDto> getAllRequest() {
        List<CorporationEntity> entities = repository.findByKYCStatus(KYCStatus.KYC_IN_PROCESS);
        return converter.convert(entities, DTO_CLASS);
    }

    @Override
    public void uploadDocument(MultipartFile file, UUID idCorporation) {
        /*checkCurrentUserForPermissionActionThisCorporation(idCorporation);
        CorporationDto corporation = getById(idCorporation);
        if (corporation == null) {
            throw new ClientException(CORPORATION_NOT_FOUND);
        }*/
        encryptAndSave(file, idCorporation);
        /*if (!corporation.getKYCStatus().equals(KYCStatus.KYC_PASSED)) {
            corporation.setKYCStatus(KYCStatus.KYC_IN_PROCESS);
            super.update(corporation);
        }*/
    }

    private void checkActionWithUserFromCorporation(UUID idCorporation, UUID idUser) {
        checkUserByStatus();
        checkCurrentUserForPermissionActionThisCorporation(idCorporation);
        if (userservice.getById(idUser) == null) {
            throw new ClientException(USER_NOT_FOUND);
        }
    }

    private void checkUserByStatus() {
        if (SecurityUtil.getUser() == null) {
            throw new ClientException(USER_NOT_AUTHENTICATION);
        }
        UserDto user = SecurityUtil.getUser().getUser();
        if (user.getBanStatus().equals(BanStatus.BAN)) {
            throw new ClientException(USER_IN_BAN);
        }
    }

    private void checkCurrentUserForPermissionActionThisCorporation(UUID id) {
        UUID userId = SecurityUtil.getUserId();
        UserCorporationDto userCorporation = userCorporationService.getByUserIdAndCorporationId(userId, id);
        if (userCorporation == null) {
            throw new ClientException(USER_DONT_HAVE_PERMISSION_TO_CORPORATION);
        }
    }

    private void checkByUpdateStatus(CorporationDto dto) {
        CorporationDto corporation = getById(dto.getId());
        if (corporation == null) {
            throw new ClientException(CORPORATION_NOT_FOUND);
        }
        if (!corporation.getKYCStatus().equals(dto.getKYCStatus())) {
            throw new ClientException(DONT_HAVE_PERMISSION_TO_CHANGE_KYS_STATUS);
        }
        if (!corporation.getVerifiedStatus().equals(dto.getVerifiedStatus())) {
            throw new ClientException(DONT_HAVE_PERMISSION_TO_CHANGE_VERIFIED_STATUS);
        }
    }

    private void encryptAndSave(MultipartFile file, UUID idCorporation) {
        //todo encrypt, gzip file and save
        UserFileDto userFile = mediaExchangeService.uploadFile(file);
        if (userFile != null) {
            depositoryService.create(new DepositoryDto(userFile.getUrl(), idCorporation));
        } else {
            throw new ClientException(UPLOAD_FAILED);
        }
    }

}
