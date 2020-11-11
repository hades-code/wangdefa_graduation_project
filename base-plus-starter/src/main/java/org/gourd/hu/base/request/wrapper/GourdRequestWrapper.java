package org.gourd.hu.base.request.wrapper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 请求包装类
 *
 * @author gourd.hu
 */
@Slf4j
public class GourdRequestWrapper extends HttpServletRequestWrapper {

    @Getter
    private final String body;
 

    public GourdRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder sb = new StringBuilder();
        BufferedReader isr = null;
        try(InputStream ins = request.getInputStream()){
            if(ins != null){
                isr = new BufferedReader(new InputStreamReader(ins));
                char[] charBuffer = new char[128];
                int readCount;
                while((readCount = isr.read(charBuffer)) != -1){
                    sb.append(charBuffer,0,readCount);
                }
            }
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }finally {
            if(isr != null) {
                isr.close();
            }
        }
        body = sb.toString();
    }
 
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
 
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayIns = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletIns = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
 
            @Override
            public boolean isReady() {
                return false;
            }
 
            @Override
            public void setReadListener(ReadListener readListener) {
 
            }
 
            @Override
            public int read() {
                return byteArrayIns.read();
            }
        };
        return  servletIns;
    }
}