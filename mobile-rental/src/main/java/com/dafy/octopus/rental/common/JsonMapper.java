package com.dafy.octopus.rental.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by  on 2016/4/5.
 */
public class JsonMapper extends ObjectMapper {

    private static final String DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";

    public ObjectMapper getMapper() {
        return this;
    }

    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        this.setDateFormat(dateFormat);
        this.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public Object findSerializer(Annotated a) {
                if(a instanceof AnnotatedMethod) {
                    AnnotatedElement m=a.getAnnotated();
                    DateTimeFormat an=m.getAnnotation(DateTimeFormat.class);
                    if(an!=null) {
                        if(!DEFAULT_DATE_FORMAT.equals(an.pattern())) {
                            return new JsonDateSerializer(an.pattern());
                        }
                    }
                }
                return super.findSerializer(a);
            }
        });
    }

    public String toJson(Object obj) {
        try {
            return writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("转换json字符失败!");
        }
    }

    public <T> T toObject(String json, Class<T> clazz) {
        try {
            return readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("将json字符转换为对象时失败!");
        }
    }

    public static class JsonDateSerializer extends JsonSerializer<Date> {
        private SimpleDateFormat dateFormat;
        public JsonDateSerializer(String format) {
            dateFormat = new SimpleDateFormat(format);
        }

        @Override
        public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
                throws IOException, JsonProcessingException {
            String value = dateFormat.format(date);
            gen.writeString(value);
        }
    }
}
