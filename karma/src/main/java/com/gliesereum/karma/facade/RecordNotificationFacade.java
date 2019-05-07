package com.gliesereum.karma.facade;

import com.gliesereum.share.common.model.dto.karma.record.AbstractRecordDto;
import com.gliesereum.share.common.model.dto.karma.record.BaseRecordDto;

/**
 * @author yvlasiuk
 * @version 1.0
 */
public interface RecordNotificationFacade {

    void recordCreateNotification(AbstractRecordDto record);

    void recordUpdateNotification(AbstractRecordDto record);

    void recordRemindNotification(BaseRecordDto record);
}
