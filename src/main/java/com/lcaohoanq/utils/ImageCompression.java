package com.lcaohoanq.utils;

import com.github.luben.zstd.Zstd;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class ImageCompression {

    public static byte[] compressImage(String originalImagePath) {
        try {
            // Read the original image
            byte[] originalData = Files.readAllBytes(Paths.get(originalImagePath));
            // Compress the image data
            return Zstd.compress(originalData);
        } catch (IOException e) {
            System.out.println("Error when compressing the image: " + e.getMessage());
            return null;
        }
    }

    @Deprecated
    public static void main(String[] args) {
        String originalImagePath = "src/main/resources/META-INF/resources/icons/anonymous_user.png";
        String compressedImagePath = "src/main/resources/META-INF/resources/icons/anonymous_user.zst";
        String decompressedImagePath = "src/main/resources/META-INF/resources/icons/decompressed.png";

        try {
            // Read the original image
            byte[] originalData = Files.readAllBytes(Paths.get(originalImagePath));

            // Compress the image data
            byte[] compressedData = Zstd.compress(originalData);
            Files.write(Paths.get(compressedImagePath), compressedData);

            // Decompress the image data
            byte[] decompressedData = Zstd.decompress(compressedData, originalData.length);
            Files.write(Paths.get(decompressedImagePath), decompressedData);

            System.out.println("Compression and decompression completed.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
