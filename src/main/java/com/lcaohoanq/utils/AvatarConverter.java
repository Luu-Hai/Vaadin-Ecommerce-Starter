package com.lcaohoanq.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AvatarConverter {
    public static byte[] convertAvatarUrlToByteArray(String avatarUrl)
        throws IOException {
        URL url = new URL(avatarUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        }
    }

    public static void main(String[] args) {
        try {
            String avatarUrl = "https://lh3.googleusercontent.com/a/ACg8ocI8l43pmLpxygjit-UEyFIML1h-3j3AE4RzBEYwoVxG2ugO5Dk=s96-c";
            byte[] avatarBytes = convertAvatarUrlToByteArray(avatarUrl);
            System.out.println("Avatar downloaded and converted to byte array successfully.");
            // You can now use avatarBytes as needed, for example, saving it to the User object
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to convert avatar URL to byte array.");
        }
    }
}
