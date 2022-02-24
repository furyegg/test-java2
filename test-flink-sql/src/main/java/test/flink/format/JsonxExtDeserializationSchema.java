package test.flink.format;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.formats.common.TimestampFormat;
import org.apache.flink.formats.json.JsonToRowDataConverters;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.RowType;
import test.util.StrUtils;

import java.io.IOException;
import java.util.List;

import static org.apache.flink.util.Preconditions.checkNotNull;

@Slf4j
public class JsonxExtDeserializationSchema implements DeserializationSchema<RowData> {
    private final RowType rowType;
    private final TypeInformation<RowData> resultTypeInfo;
    private final JsonToRowDataConverters.JsonToRowDataConverter runtimeConverter;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public JsonxExtDeserializationSchema(
            RowType rowType,
            TypeInformation<RowData> resultTypeInfo) {
        this.rowType = rowType;
        this.resultTypeInfo = resultTypeInfo;
        runtimeConverter = new JsonToRowDataConverters(true, false, TimestampFormat.SQL)
                .createConverter(checkNotNull(rowType));
    }
    
    @Override
    public RowData deserialize(byte[] message) throws IOException {
        String content = new String(message);
        io.vavr.collection.List<String> items = parseLogItems(content);
    
        ObjectNode root = (ObjectNode) objectMapper.readTree(items.head());
        
        if (!items.tail().isEmpty()) {
            ArrayNode arrayNode = root.putArray("ext_fields");
            for (String field : items.tail()) {
                arrayNode = arrayNode.add(field);
            }
        }
        
        return (RowData) runtimeConverter.convert(root);
    }
    
    private io.vavr.collection.List<String> parseLogItems(String content) {
        int jsonEnd = content.lastIndexOf('}');
        String json = content.substring(0, jsonEnd + 1);
        String ext = content.substring(jsonEnd + 1);
        List<String> extFields = StrUtils.splitBy(ext, "\\s++");
        return io.vavr.collection.List.of(json).appendAll(extFields);
    }
    
    @Override
    public boolean isEndOfStream(RowData nextElement) {
        return false;
    }
    
    @Override
    public TypeInformation<RowData> getProducedType() {
        return resultTypeInfo;
    }
}
