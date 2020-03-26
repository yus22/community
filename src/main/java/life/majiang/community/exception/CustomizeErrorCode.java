package life.majiang.community.exception;

//定义枚举类 实现
public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了,要不要换个试试?"),
    TARGET_PARAM_NOT_FOUND(2001,"未选择任何问题或回复"),
    NO_LOGIN(2003,"当前操作需要登录"),
    SYSTERM_ERROR(2003,"服务器冒烟了,请稍后重试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),

            ;

    private  Integer code;
    private  String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }


}
