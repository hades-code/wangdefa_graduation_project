package org.lhq.exception;

import lombok.extern.slf4j.Slf4j;
import org.lhq.entity.vo.ResultVO;
import org.lhq.common.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: wangdefa_graduation_project
 * @description: 全局异常处理
 * @author: Wang defa
 * @create: 2020-12-11 10:06
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ProjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<Object> projectException(ProjectException e){
        log.error("业务异常",e);
        return ResultVO.error(ResultCode.FAIL,e.getMessage());
    }
}
