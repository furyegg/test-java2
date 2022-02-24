package test.flink;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import test.util.RegEx;

import java.util.List;

public class Test {
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"direction\":\"down\",\"domain\":\"qn.live-customsource.acg.tv\",\"droped\":0,\"durationMs\":1000,\"fpsInMeta\":30,\"frames\":{\"audio\":0,\"data\":0,\"video\":1},\"id\":\"zDcAAIhQzntSQccW\",\"info\":{\"hub\":\"live-qn\",\"streamId\":\"fake.live-qn.live_1294763929_3798788\",\"uid\":\"1381188778\"},\"length\":8162,\"localAddr\":\"10.20.59.23:26806\",\"method\":\"publish\",\"remoteAddr\":\"10.20.59.36:1935\",\"role\":\"customsource\",\"time\":\"2022-01-05T11:13:32.754+08:00\",\"url\":\"rtmp://qn.live-customsource.acg.tv/.i/bGl2ZS1xbjpsaXZlLXFuL2xpdmVfMTI5NDc2MzkyOV8zNzk4Nzg4?e=1641354207\\u0026stype=custom\\u0026token=UP6qRhiPebttLMmbni6kbnNQpC%29WwT%3A5QbHTkcXREeiMtDFEBG7iilITMA%3D\\u0026nobiz__=true\"}\tpili-forward\tjjh2072";
        List<String> items = RegEx.of("^.++((?<=[^\\}]++$))")
                .matchedGroups(json)
                .toList();
        System.out.println(items);
    }
}
