package org.gourd.hu.core.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * 根据IP地址获取详细的地域信息
 *
 * @author gourd.hu
 */
@Slf4j
public class IpAddressUtil {

	private static final String UNKNOWN = "unknown";
	private static final String IPV6_LOCAL = "0:0:0:0:0:0:0:1";

	/**
	 * 获取IP地址
	 * @Author  gourd.hu
	 * @param request
	 * @return  String
	 *
	 */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Real-IP");
        if(!StringUtils.isEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
			return ip;
		}
        ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ip != null && ip.length() != 0)
		{
			ip = ip.split(",")[0];
		}
		if (IPV6_LOCAL.equals(ip)){
			ip = getLocalhostIp();
		}
        return ip;
    }

    /**
     * 获取本地ip地址
	 *
	 * @return
     */
	public static String getLocalhostIp(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error("获取ip异常：",e);
		}
		return null;
	}


	/**
	 * 根据ip获取地址
	 *
	 * @param ip
	 * @return
	 */
	public static String getAddresses(String ip) {
		String urlStr ="http://ip.taobao.com/service/getIpInfo.php";
		String returnStr = getResult(urlStr, ip);
		if (returnStr != null) {
			// 处理返回的省市区信息
			String[] temp = returnStr.split(",");
			if (temp.length < 3) {
				// 无效IP，局域网测试
				return "0";
			}
			String region = (temp[5].split(":"))[1].replaceAll("\"", "");
			// 省份
			region = decodeUnicode(region);

			String country = "";
			String area = "";
			// String region = "";
			String city = "";
			String county = "";
			String isp = "";
			for (int i = 0; i < temp.length; i++) {
				switch (i) {
					case 1:
						country = (temp[i].split(":"))[2].replaceAll("\"", "");
						// 国家
						country = decodeUnicode(country);
						break;
					case 3:
						area = (temp[i].split(":"))[1].replaceAll("\"", "");
						// 地区
						area = decodeUnicode(area);
						break;
					case 5:
						region = (temp[i].split(":"))[1].replaceAll("\"", "");
						// 省份
						region = decodeUnicode(region);
						break;
					case 7:
						city = (temp[i].split(":"))[1].replaceAll("\"", "");
						// 市区
						city = decodeUnicode(city);
						break;
					case 9:
						county = (temp[i].split(":"))[1].replaceAll("\"", "");
						// 地区
						county = decodeUnicode(county);
						break;
					case 11:
						isp = (temp[i].split(":"))[1].replaceAll("\"", "");
						// ISP公司
						isp = decodeUnicode(isp);
						break;
				}
			}
			String address = country+region+city+county;
			if(StringUtils.isEmpty(address)){
				address = "地球村";
			}
			return address;
		}
		return null;
	}

	/**
	 * @param urlStr 请求的地址
	 *
	 * @return
	 */
	private static String getResult(String urlStr, String ip) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			// 新建连接实例
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接超时时间，单位毫秒
			connection.setConnectTimeout(5000);
			// 设置读取数据超时时间，单位毫秒
			connection.setReadTimeout(5000);
			// 是否打开输出流 true|false
			connection.setDoOutput(true);
			// 是否打开输入流true|false
			connection.setDoInput(true);
			// 提交方法POST|GET
			connection.setRequestMethod("POST");
			// 是否缓存true|false
			connection.setUseCaches(false);
			// 打开连接端口
			connection.connect();
			// 打开输出流往对端服务器写数据
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.writeBytes("ip="+ip);
			out.flush();// 刷新
			out.close();// 关闭输出流
			// 往对端写完数据对端服务器返回数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	/**
	 * unicode 转换成 中文
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException("Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}
}  
