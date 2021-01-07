package org.lhq.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.lhq.annotation.JsonParam;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component
public class JsonParamArgumentResolver implements HandlerMethodArgumentResolver {
	private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	private final Charset charset;

	public JsonParamArgumentResolver(){
		this(DEFAULT_CHARSET);
	}
	public JsonParamArgumentResolver(Charset defaultCharset){
		this.charset = defaultCharset;
	}
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(JsonParam.class);
	}


	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws IOException {
		String requestBody = getRequestBody(webRequest);
		Object value = null;
		if (StrUtil.isNotBlank(requestBody)){
			JSONObject jsonObject = JSONUtil.parseObj(requestBody);
			value = jsonObject.get(Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).value());
			Class<?> type = Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).type();
			if( value != null && Collection.class.isAssignableFrom(value.getClass())) {
				ArrayList<Object> objects = new ArrayList<>();
				JSONArray jsonArray = JSONUtil.parseArray(StrUtil.toString(value));
				for (Object json : jsonArray) {
					Object convert = Convert.convert(type, json);
					objects.add(convert);
				}
					return objects;
			}
			value = Convert.convert(type, value);
		}
		if (Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).required() && value == null){
			throw new RuntimeException(Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).value() + "不能为空");
		}
		return value;
	}
	private String getRequestBody(NativeWebRequest webRequest){
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		String jsonBody = (String)servletRequest.getAttribute(JSON_REQUEST_BODY);
		if (StrUtil.isEmpty(jsonBody)){
			try {
				jsonBody = StreamUtils.copyToString(servletRequest.getInputStream(),charset);
				servletRequest.setAttribute(JSON_REQUEST_BODY, jsonBody);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return jsonBody;
	}
}
