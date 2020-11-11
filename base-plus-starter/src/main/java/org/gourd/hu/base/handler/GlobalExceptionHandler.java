package org.gourd.hu.base.handler;


import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.BusinessException;
import org.gourd.hu.base.exception.PreAuthorizeException;
import org.gourd.hu.base.exception.enums.IResponseEnum;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.request.bean.RequestDetail;
import org.gourd.hu.base.request.holder.RequestDetailThreadLocal;
import org.gourd.hu.base.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一异常处理器
 * @author gourd.hu
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

	/**
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		log.debug("请求有参数才进来:{} ",binder.getObjectName());
	}

	/**
	 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
	 * @param model
	 */
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("author", "gourd.hu");
	}

	/**
	 * 处理自定义业务异常
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = BusinessException.class)
	public ErrorResponse handleException(BusinessException ex) {
		// 打印堆栈信息
		printRequestDetail();
		printApiCodeException(ex.getResponseEnum(), ex);
		return ErrorResponse.result(ex.getResponseEnum().getCode(),ex.getResponseEnum().getMessage());
	}

	/**
	 * 处理权限异常
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = PreAuthorizeException.class)
	public ErrorResponse handleException(PreAuthorizeException ex) {
		// 打印堆栈信息
		printRequestDetail();
		printApiCodeException(ex.getResponseEnum(), ex);
		return ErrorResponse.result(ex.getResponseEnum().getCode(),ex.getResponseEnum().getMessage());
	}

	/**
	 * 非法参数验证异常
	 *
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ErrorResponse handleException(MethodArgumentNotValidException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.BAD_REQUEST, ex);
		BindingResult bindingResult = ex.getBindingResult();
		List<String> list = new ArrayList<>();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			list.add(fieldError.getDefaultMessage());
		}
		return ErrorResponse.result(ResponseEnum.BAD_REQUEST,list);
	}

	/**
	 * 非法参数验证异常
	 *
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({BindException.class})
	public ErrorResponse handleException(BindException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.BAD_REQUEST, ex);
		BindingResult bindingResult = ex.getBindingResult();
		List<String> list = new ArrayList<>();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			list.add(fieldError.getDefaultMessage());
		}
		return ErrorResponse.result(ResponseEnum.BAD_REQUEST,list);
	}

	/**
	 * 缺少请求参数异常处理
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.OK)
	public ErrorResponse handleException(MissingServletRequestParameterException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.BAD_REQUEST, ex);
		return ErrorResponse.result(ResponseEnum.BAD_REQUEST);
	}

	/**
	 * 404异常处理
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ErrorResponse handleException(NoHandlerFoundException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.NOT_FOUND, ex);
		return ErrorResponse.result(ResponseEnum.NOT_FOUND);
	}

	/**
	 * 不支持方法异常处理
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.OK)
	public ErrorResponse handleException(HttpRequestMethodNotSupportedException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.METHOD_NOT_ALLOWED, ex);
		return ErrorResponse.result(ResponseEnum.METHOD_NOT_ALLOWED);
	}

	/**
	 * 非法获取异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = IllegalAccessException.class)
	@ResponseStatus(HttpStatus.OK)
	public ErrorResponse handleException(IllegalAccessException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.ILLEGAL_ACCESS, ex);
		return ErrorResponse.result(ResponseEnum.ILLEGAL_ACCESS);
	}

	/**
	 * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ErrorResponse handleException(MaxUploadSizeExceededException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.FILE_TOO_LARGE, ex);
		return ErrorResponse.result(ResponseEnum.FILE_TOO_LARGE);
	}

	/**
	 * 处理字段太长
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorResponse handleException(DataIntegrityViolationException ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.FIELD_TOO_LARGE, ex);
		return ErrorResponse.result(ResponseEnum.FIELD_TOO_LARGE);
	}

	/**
	 * 默认的异常处理
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleException(Exception ex) {
		printRequestDetail();
		printApiCodeException(ResponseEnum.INTERNAL_SERVER_ERROR, ex);
		return ErrorResponse.result(ResponseEnum.INTERNAL_SERVER_ERROR.getCode(),ex.getCause().getMessage());
	}

	/**
	 * 获取httpStatus格式化字符串
	 *
	 * @param responseEnum
	 * @return
	 */
	private String getApiCodeString(IResponseEnum responseEnum) {
		if (responseEnum != null) {
			return String.format("errorCode: %s, errorMessage: %s", responseEnum.getCode(), responseEnum.getMessage());
		}
		return null;
	}

	/**
	 * 打印请求详情
	 */
	private void printRequestDetail() {
		RequestDetail requestDetail = RequestDetailThreadLocal.getRequestDetail();
		if (requestDetail != null) {
			log.error("异常来源：ip: {}, path: {}", requestDetail.getIp(), requestDetail.getPath());
		}
	}
	/**
	 * 打印错误码及异常
	 *
	 * @param responseEnum
	 * @param exception
	 */
	private void printApiCodeException(IResponseEnum responseEnum, Exception exception) {
		log.error(getApiCodeString(responseEnum), exception);
	}

}