package org.gourd.hu.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Serializable工具(JDK)
 * @author gourd.hu
 *
 */
public class SerializableUtil {

    private static final Logger log = LoggerFactory.getLogger(SerializableUtil.class);

    /**
     * 序列化
     * @param object
     * @return byte[]
     * @author gourd.hu
     * @date 2018/9/4 15:14
     */
    public static byte[] serializable(Object object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException e) {
            log.error("SerializableUtil工具类序列化出现IOException异常:{}",e.getStackTrace());
        }
        return null;
    }

    /**
     * 反序列化
     * @param bytes
     * @return java.lang.Object
     * @author gourd.hu
     * @date 2018/9/4 15:14
     */
    public static Object unserializable(byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois= new ObjectInputStream(bais)){
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            log.error("SerializableUtil工具类反序列化出现ClassNotFoundException异常:{}",e.getStackTrace());
        } catch (IOException e) {
            log.error("SerializableUtil工具类反序列化出现IOException异常:{}",e.getStackTrace());
        }
        return null;
    }
}