package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,bio,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{bio},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);//是一个类就不用写
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);//不是类就要写一个Param注解
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
