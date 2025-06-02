package com.app.projectstyleecommerce.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {
    // phương thức này nhận 1 mảng byte chứa dữ liệu hình ảnh và nén nó bằng cách sử dụng thuật toán nén Deflater
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(tmp);
            outputStream.write(tmp, 0, count);

        }
        try{
            outputStream.close();
        }catch (Exception ignored){

        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try{
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);

            }
            outputStream.close();
        } catch (Exception ignored){}
        return outputStream.toByteArray();
    }


}
