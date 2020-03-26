package life.majiang.community.advice;

import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*异常处理 拦截Controller异常*/
@ControllerAdvice
@ResponseBody
public class CustmoizeExceptionHandler {

//   ModelAndView 就是跳转到html页面用的
    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request, Throwable ex, Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
//            传递的是json请求,返回json请求,有错误提示时候用 抛异常返回
            if (ex instanceof CustomizeException){
                return ResultDTO.errorOf((CustomizeException)ex);
            }else{
                return ResultDTO.errorOf(CustomizeErrorCode.SYSTERM_ERROR);
            }


        }else{
//            页面错误跳转
            if (ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYSTERM_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }


    }

}
