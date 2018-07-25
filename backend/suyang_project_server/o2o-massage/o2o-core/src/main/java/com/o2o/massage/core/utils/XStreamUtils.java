/*
 * XiaoMi PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.o2o.massage.core.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

public class XStreamUtils {
    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2014-02-18
     */
    public static class EXXStream extends XStream {
        public EXXStream() {
            super(new XppDriver() {
                public HierarchicalStreamWriter createWriter(Writer out) {
                    return new PrettyPrintWriter(out) {
                        // 对所有xml节点的转换都增加CDATA标记
                        boolean cdata = true;

                        @Override
                        public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
                            super.startNode(name, clazz);
                        }

                        @Override
                        public String encodeNode(String name) {
                            return name;
                        }

                        @Override
                        protected void writeText(QuickWriter writer, String text) {
                            if (cdata) {
                                writer.write("<![CDATA[");
                                writer.write(text);
                                writer.write("]]>");
                            } else {
                                writer.write(text);
                            }
                        }
                    };
                }
            });
        }

    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String xml, Class<T> type) {
        XStream xstream = new EXXStream();
        xstream.processAnnotations(type);
        return (T) xstream.fromXML(xml);
    }

    public static String toXml(Object o) {
        XStream xstream = new EXXStream();
        xstream.processAnnotations(o.getClass());
        return xstream.toXML(o);
    }
}
