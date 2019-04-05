package com.gliesereum.share.common.exception.messages;

/**
 * @author vitalij
 */
public class LandingGalleryExceptionMessage {

    public static final ExceptionMessage ID_IS_EMPTY = new ExceptionMessage(1600, 400, "Id is empty");
    public static final ExceptionMessage MODEL_IS_EMPTY = new ExceptionMessage(1601, 400, "Model is empty");
    public static final ExceptionMessage MEDIA_NOT_FOUND_BY_ID = new ExceptionMessage(1602, 400, "Media not found by id");
    public static final ExceptionMessage ART_BOND_NOT_FOUND_BY_ID = new ExceptionMessage(1603, 400, "Art bond not found by id");
    public static final ExceptionMessage BLOCK_MEDIA_TYPE_IS_EMPTY = new ExceptionMessage(1604, 400, "Block media type is empty");
    public static final ExceptionMessage CUSTOMER_NOT_FOUND_BY_ID = new ExceptionMessage(1605, 400, "Customer not found by id");
    public static final ExceptionMessage OFFER_STATE_IS_EMPTY = new ExceptionMessage(1606, 400, "Offer state is empty");
    public static final ExceptionMessage OFFER_NOT_FOUND_BY_ID = new ExceptionMessage(1607, 400, "Offer not found by id");
    public static final ExceptionMessage CUSTOMER_NOT_FOUND_BY_USER_ID = new ExceptionMessage(1608, 400, "Customer not found by user id");
    public static final ExceptionMessage ART_BOND_NOT_AVAILABLE_FOR_INVESTMENT = new ExceptionMessage(1609, 400, "Art bond not available for investment");
    public static final ExceptionMessage SUM_OF_INVESTMENT_CAN_NOT_BE_ZERO = new ExceptionMessage(1610, 400, "Sum of investment can't be 0");
    public static final ExceptionMessage SUM_EXCEEDS_AMOUNT_ALLOWED_FOR_INVESTMENT = new ExceptionMessage(1611, 400, "Sum exceeds the amount allowed for investment");
    public static final ExceptionMessage CUSTOMER_ALREADY_EXIST = new ExceptionMessage(1612, 400, "Customer already exist");

}
