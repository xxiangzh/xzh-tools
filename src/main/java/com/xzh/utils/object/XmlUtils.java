package com.xzh.utils.object;

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

    /**
     * 将XML压缩成一行
     *
     * @param xmlString
     * @return
     */
    public static String zipXml(String xmlString) {
        boolean flag = true;
        boolean quotesFlag = true;
        StringBuilder ans = new StringBuilder();
        String tmp = "";
        for (int i = 0; i < xmlString.length(); i++) {
            if ('"' == xmlString.charAt(i)) {
                ans.append(xmlString.charAt(i));
                quotesFlag = !quotesFlag;
            } else if ('<' == xmlString.charAt(i)) {
                tmp = tmp.trim();
                ans.append(tmp);
                flag = true;
                ans.append(xmlString.charAt(i));
            } else if ('>' == xmlString.charAt(i)) {
                if (quotesFlag) {
                    flag = false;
                    ans.append(xmlString.charAt(i));
                    tmp = "";
                } else {
                    ans.append(">");
                }
            } else if (flag) {
                ans.append(xmlString.charAt(i));
            } else {
                tmp += xmlString.charAt(i);
            }
        }
        return ans.toString();
    }
}
