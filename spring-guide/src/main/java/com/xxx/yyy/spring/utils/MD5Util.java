package com.xxx.yyy.spring.utils;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: maoyan
 * @date: 2022/1/20 22:17:02
 * @description:
 */

public class MD5Util {
    private static MessageDigest md5Digest = null;
    private static final String seed = "sxaa1k89dc";

    public MD5Util() {
    }

    static {
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            throw new RuntimeException("MD5 not supported", var1);
        }
    }

    public static byte[] md5digest(String key) {
        if (key == null) {
            return null;
        } else {
            MessageDigest md5;
            try {
                md5 = (MessageDigest) md5Digest.clone();
            } catch (CloneNotSupportedException var3) {
                throw new RuntimeException("clone of MD5 not supported", var3);
            }

            md5.update(key.getBytes());
            return md5.digest();
        }
    }

    public static byte[] md5digest(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        } else {
            MessageDigest md5;
            try {
                md5 = (MessageDigest) md5Digest.clone();
            } catch (CloneNotSupportedException var3) {
                throw new RuntimeException("clone of MD5 not supported", var3);
            }

            md5.update(byteBuffer);
            return md5.digest();
        }
    }

    public static String generateCheckString(String key) {
        if (key == null) {
            return null;
        } else {
            key = key + "sxaa1k89dc";
            return digest(key);
        }
    }

    public static String digest(String key) {
        byte[] bs = md5digest(key);
        return byte2hex(bs);
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs;
    }

    private static byte[] hex2byte(byte[] b) {
        if (b.length % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        } else {
            byte[] b2 = new byte[b.length / 2];

            for (int n = 0; n < b.length; n += 2) {
                String item = new String(b, n, 2);
                b2[n / 2] = (byte) Integer.parseInt(item, 16);
            }

            return b2;
        }
    }

    private static String byte2SingleHex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        byte[] bytes = md5digest(byteBuffer);
        return byte2hex(bytes);
    }

    public static void main(String[] args) {
//        System.out.println(digest("14050988&groupop"));
        String checkCode = "";

        String idNumber = "130282198405100028";
        String eventId = "2401";
        String eventCode = "kpl";
        String ticketSecreteKey = "o9yZNAd6aWvoy7aVH60&eJ^n&x";
        String stdSign = MD5Util.digest(Joiner.on(StringUtils.EMPTY).join(
                StringUtils.defaultIfEmpty(checkCode, StringUtils.EMPTY),
                StringUtils.defaultString(idNumber, StringUtils.EMPTY),
               eventId, eventCode,
               ticketSecreteKey));

        System.out.println(stdSign);

    }
}
