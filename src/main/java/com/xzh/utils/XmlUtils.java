package com.xzh.utils;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml工具
 *
 * @author 向振华
 * @date 2021/10/16 09:48
 */
@Slf4j
public class XmlUtils {

    public static String toXmlString(Object obj, String charset) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString().replace(" standalone=\"yes\"", "");
        } catch (JAXBException e) {
            log.error("XML转换异常", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T toJavaBean(String xml, Class<T> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            log.error("XML转换异常", e);
            return null;
        }
    }
}
