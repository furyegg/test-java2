package test.springboot.jar.metric;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LocalFileStatistic {
    public static FileInfo getFileInfo(String dir) {
        Path path = Paths.get(dir);
        return getFileInfo(path);
    }
    
    public static FileInfo getFileInfo(Path path) {
        if (path.getFileName().toString().startsWith(".")) {
            return FileInfo.empty();
        }
        try {
            if (Files.isDirectory(path)) {
                List<Path> files = Files.list(path).collect(Collectors.toList());
                FileInfo total = FileInfo.empty();
                for (Path file : files) {
                    FileInfo fileInfo = getFileInfo(file);
                    total = total.add(fileInfo);
                }
                return total;
            } else {
                return FileInfo.ofSize(Files.size(path));
            }
        } catch (IOException e) {
            log.error("failed to get size of dir: " + path.toString(), e);
            return FileInfo.empty();
        }
    }
}