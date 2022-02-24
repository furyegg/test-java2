package test.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegEx {
    private static final LoadingCache<Tuple2<String, Integer>, Pattern> PATTERN_CACHE = Caffeine.newBuilder()
            .softValues()
            .build(regex -> Pattern.compile(regex._1, regex._2));

    private List<String> regexs = new ArrayList<>();
    private int flags;

    public static RegEx of(String regex) {
        return new RegEx(regex, 0);
    }

    public static RegEx of(String regex, int flag) {
        return new RegEx(regex, flag);
    }
    
    public static RegEx of(String... regexs) {
        return new RegEx(Arrays.asList(regexs), 0);
    }

    public static RegEx of(List<String> regexs) {
        return new RegEx(regexs, 0);
    }

    public static RegEx of(List<String> regexs, int flag) {
        return new RegEx(regexs, flag);
    }

    public static RegEx ofCaseInsensitive(String regex) {
        return new RegEx(regex, Pattern.CASE_INSENSITIVE);
    }

    public static RegEx ofCaseInsensitive(List<String> regexs) {
        return new RegEx(regexs, Pattern.CASE_INSENSITIVE);
    }

    public RegEx(String regex, int flags) {
        regexs.add(regex);
        this.flags = flags;
    }

    public RegEx(List<String> regexs, int flags) {
        this.regexs.addAll(regexs);
        this.flags = flags;
    }

    private Pattern[] patterns() {
        return StreamEx.of(regexs).map(regex -> PATTERN_CACHE.get(Tuple.of(regex, flags))).toArray(Pattern.class);
    }

    public boolean find(String text) {
        return patterns()[0].matcher(text).find();
    }

    public String replace(String text, Function1<String, String> f) {
        Matcher m = patterns()[0].matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, f.apply(m.group()));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public boolean matches(String text) {
        return patterns()[0].matcher(text).matches();
    }

    public boolean anyMatch(final String text) {
        return StreamEx.of(patterns()).anyMatch(p -> p.matcher(text).matches());
    }

    /**
     * Matched groups not include original text.
     */
    public static class MatchedGroups {
        private static Function1<String, Integer> INTEGER_CONVERTER = text -> Integer.valueOf(text);
        private List<String> matchedGroups;
        private int sizeEqual = -1;
        private Supplier<String> errorMessage;

        public MatchedGroups(List<String> matchedGroups) {
            this.matchedGroups = matchedGroups;
        }

        public static MatchedGroups empty() {
            return new MatchedGroups(new ArrayList<>());
        }

        /**
         * @param expectedSize Expected size of matched groups not include original text.
         */
        public MatchedGroups sizeEqual(int expectedSize) {
            sizeEqual(expectedSize, () -> "Not matched, expected matched group size is: " + expectedSize);
            return this;
        }

        public MatchedGroups sizeEqual(int expectedSize, Supplier<String> errorMessage) {
            this.errorMessage = errorMessage;
            sizeEqual = expectedSize;
            return this;
        }

        public Optional<Integer> toInt() {
            return toSingle(INTEGER_CONVERTER);
        }
        
        public Optional<String> toSingle() {
            if (matchedGroups.size() == 1) {
                return Optional.of(matchedGroups.get(0));
            } else {
                return Optional.empty();
            }
        }

        public <T> Optional<T> toSingle(Function1<String, T> converter) {
            if (matchedGroups.size() == 1) {
                return Optional.of(converter.apply(matchedGroups.get(0)));
            } else {
                return Optional.empty();
            }
        }

        public List<Integer> toIntList() {
            checkSize();
            return toList(INTEGER_CONVERTER);
        }

        public List<String> toList() {
            checkSize();
            if (matchedGroups.isEmpty()) {
                return new ArrayList<>();
            }
            return matchedGroups;
        }

        public io.vavr.Tuple2<String, String> toTuple2() {
            sizeEqual(2);
            checkSize();
            List<String> result = toList();
            return io.vavr.Tuple.of(result.get(0), result.get(1));
        }

        public <T> List<T> toList(Function1<String, T> converter) {
            checkSize();
            if (matchedGroups.isEmpty()) {
                return new ArrayList<>();
            }
            return StreamEx.of(matchedGroups).map(converter).toList();
        }

        private void checkSize() {
            if (sizeEqual < 0) {
                return;
            }
            if (matchedGroups.size() != sizeEqual) {
                throw new IllegalStateException(errorMessage.get());
            }
        }
    }

    public MatchedGroups matchedGroups(String text) {
        Matcher m = patterns()[0].matcher(text);
        if (m.find()) {
            List<String> groups = new ArrayList<>();
            groups.add(m.group(1));
            while (m.find()) {
                groups.add(m.group(1));
            }
            return new MatchedGroups(groups);
        } else {
            return MatchedGroups.empty();
        }
    }
}