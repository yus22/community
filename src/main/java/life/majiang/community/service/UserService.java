package life.majiang.community.service;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==0){
//            插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //不等于空就更新
            User dbUser=users.get(0);
            User updateUser= new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setBio(user.getBio());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            //这块看看example里存的是啥 是不是id
            example.createCriteria().andIdEqualTo(dbUser.getId());
            //updateByExampleSelective 部分更新,具体sql可以去xml里去看
            userMapper.updateByExampleSelective(updateUser,example);
        }


    }
}
