package test.util;

import lombok.experimental.UtilityClass;
import one.util.streamex.StreamEx;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@UtilityClass
public class StrUtils {
    /**
     * 根据指定正则分隔字符串，并且去掉每一个元素两端的空格
     */
    public static List<String> splitBy(String str, String regex) {
        if (StringUtils.isBlank(str)) {
            return Lists.newArrayList();
        }
        return StreamEx.of(str.split(regex))
                .map(e -> e.trim())
                .filter(e -> StringUtils.isNoneBlank(e))
                .toList();
    }
}