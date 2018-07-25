package com.o2o.massage.core.constants;

/**
 *
 */
public enum EnumOrderCommentStatus {
    /**
     * 无
     */
    NONE(0),
    /**
     * 待点评
     */
    WaitComment(1),
    /**
     * 点评完成
     */
    CommentDone(2);
    private int value;

    EnumOrderCommentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static EnumOrderCommentStatus valueOf(int value) {
        for (EnumOrderCommentStatus c : EnumOrderCommentStatus.values()) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return EnumOrderCommentStatus.NONE;
    }

}
