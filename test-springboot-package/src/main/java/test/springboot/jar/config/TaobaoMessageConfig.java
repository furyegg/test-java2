package test.springboot.jar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class TaobaoMessageConfig {
    @Value("${taobao.message.topics}")
    private String topics;
    
    @Value("#{${taobao.message.topicMap}}")
    private Map<String, String> topicMap;
    
    public String getTopics() {
        return topics;
    }
    
    public void setTopics(String topics) {
        this.topics = topics;
    }
    
    public Map<String, String> getTopicMap() {
        return topicMap;
    }
    
    public void setTopicMap(Map<String, String> topicMap) {
        this.topicMap = topicMap;
    }
}