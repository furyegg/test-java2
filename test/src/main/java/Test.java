import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Pattern p = Pattern.compile("cat");
//        Matcher m = p.matcher("one cat two cats in the yard");
//        StringBuffer sb = new StringBuffer();
//        while (m.find()) {
//            m.appendReplacement(sb, "dog");
//        }
//        m.appendTail(sb);
//        System.out.println(sb.toString());
    
        Pattern p = Pattern.compile("cat");
        Matcher m = p.matcher("one cat two cats in the yard");
        while (m.find()) {
            System.out.println(m.group());
        }
    }

}