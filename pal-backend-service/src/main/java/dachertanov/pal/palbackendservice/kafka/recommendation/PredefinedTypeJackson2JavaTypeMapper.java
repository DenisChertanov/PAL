package dachertanov.pal.palbackendservice.kafka.recommendation;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;

/**
 * Маппер java-типа на тип сообщения в kafka,
 * отличается от дефолтного тем, что если явно указали тип сообщения - он не перезаписывается
 * и позволяет не передавать ContentType и KeyType заголовки
 */
class PredefinedTypeJackson2JavaTypeMapper extends DefaultJackson2JavaTypeMapper {

    @Override
    public void fromJavaType(JavaType javaType, Headers headers) {
        String classIdFieldName = getClassIdFieldName();
        if (headers.lastHeader(classIdFieldName) == null) {
            addHeader(headers, classIdFieldName, javaType.getRawClass());
        }
    }
}
