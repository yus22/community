package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;
/*用于展示页面回复*/
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}
