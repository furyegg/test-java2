package test.flink.schema;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import test.flink.model.Line;

import java.io.IOException;

public class LineSchema implements DeserializationSchema<Line> {
    @Override
    public Line deserialize(byte[] message) throws IOException {
        return JSON.parseObject(message, Line.class);
    }
    
    @Override
    public boolean isEndOfStream(Line nextElement) {
        return false;
    }
    
    @Override
    public TypeInformation<Line> getProducedType() {
        return TypeInformation.of(Line.class);
    }
}
