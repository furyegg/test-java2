package test.kafka;

import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> chars = Lists.newArrayList();
        for (char c : "abc".toCharArray()) {
            chars.add(String.valueOf(c));
        }
        String content = StreamEx.of(chars)
                .map(c -> String.valueOf(c))
                .joining(",");
        System.out.println(content);
        
        System.out.println(createLine());
    }
    
    private static String createLine() {
        return null;
    }
}
