package org.lhq.exception;

/**
 * @program: wangdefa_graduation_project
 * @description: 系统异常处理
 * @author: Wang defa
 * @create: 2020-12-10 20:14
 */


public class ProjectException extends Exception{
    private static final long serialVersionUID = 1L;
    public ProjectException(){

    }
    public ProjectException(String message){
        super(message);
    }
}
