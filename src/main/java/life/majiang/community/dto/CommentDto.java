package life.majiang.community.dto;

import lombok.Data;
/*评论页面传输json 反序列化*/
@Data
public class CommentDto {
    private Long parentId;
    private String content;
    private  Integer type;
}
