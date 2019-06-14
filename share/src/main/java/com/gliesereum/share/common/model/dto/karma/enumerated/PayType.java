package com.gliesereum.share.common.model.dto.karma.enumerated;

/**
 * @author vitalij
 * @version 1.0
 */
public enum PayType {

    CASH, NO_CASH;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
