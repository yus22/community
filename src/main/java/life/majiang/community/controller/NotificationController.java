package life.majiang.community.controller;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.model.NotificationExample;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationSerVice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationSerVice notificationSerVice;
    @GetMapping("/notification/{id}")
    public String edit(HttpServletRequest request, @PathVariable(name = "id") Long id, Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationSerVice.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMMENT.getType()==notificationDTO.getType()
            ||NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()
        ){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }

    }
}
