package life.majiang.community.enums;

//评论类型枚举类 问题的回复是1 ,评论下面的回复是2
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }


    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
