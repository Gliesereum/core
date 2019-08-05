package com.gliesereum.karma.facade.client;

import com.gliesereum.share.common.model.dto.karma.client.ClientDto;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author yvlasiuk
 * @version 1.0
 */
public interface ClientFacade {

    Map<UUID, ClientDto> getClientMapByIds(Collection<UUID> ids, Collection<UUID> businessIds);

    Page<ClientDto> getCustomersByBusinessIdsOrCorporationId(List<UUID> businessIds, UUID corporationId, Integer page, Integer size, String query);
}
