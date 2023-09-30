package com.github.tmslpm.gamepowunlimited.utils;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.function.Consumer;

public interface HelperFile {

    /**
     * delete all file matching with globbing pattern in directory
     * @param directory the path entry
     * @param globbingPattern example: "*.{c,h,cpp,hpp,java}"
     */
    static void deleteFiles(Path directory, String globbingPattern) {
        HelperFile.forEachDirectory(directory, globbingPattern, path -> {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Return all file matching with globbing pattern in directory
     * @param directory the path entry
     * @param globbingPattern example: "*.{c,h,cpp,hpp,java}"
     */
    static void forEachDirectory(Path directory, String globbingPattern, Consumer<Path> pathConsumer) {
        try (DirectoryStream<Path> autoCloseStream = Files.newDirectoryStream(directory, globbingPattern)) {
            autoCloseStream.forEach(pathConsumer);
        } catch (DirectoryIteratorException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a unique file name
     * @param prefixName - the prefix for the generated name
     * @param total - the total number of names to generate
     * @return String - the unique file name, example "filename_1696026829"
     */
    static String[] createUniqueName(String prefixName, int total) {
        String[] nameGen = new String[total];
        long lastUnixTime =Instant.now().getEpochSecond(), nowUnixTime = lastUnixTime;
        for (int i = 0; i < nameGen.length; i++) {
            while (nowUnixTime <= lastUnixTime) {
                nowUnixTime = Instant.now().getEpochSecond();
            }
            lastUnixTime = nowUnixTime;
            nameGen[i] = prefixName + nowUnixTime;
        }
        return nameGen;
    }
}
