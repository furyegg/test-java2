package test.flink.schema;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import test.flink.model.RtLog;

import java.io.IOException;

public class RtLogSchema implements DeserializationSchema<RtLog> {
    @Override
    public RtLog deserialize(byte[] message) throws IOException {
        JSONObject json = JSON.parseObject(new String(message));
        return new RtLog(
                json.getLongValue("request_time") * 1000,
                json.getString("domain"),
                json.getString("cdn_prodiver"));
    }
    
    @Override
    public boolean isEndOfStream(RtLog nextElement) {
        return false;
    }
    
    @Override
    public TypeInformation<RtLog> getProducedType() {
        return TypeInformation.of(RtLog.class);
    }
}
