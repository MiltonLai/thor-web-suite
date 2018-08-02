package com.rockbb.thor.commons.lib.utilities;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;

public class SecureUtil {
    private static Logger logger = LoggerFactory.getLogger(SecureUtil.class);

    /**
     * java.util.UUID的简单包装
     */
    public static String uuid() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * MD5编码
     */
    public static String md5(String s) {
        return DigestUtils.md5Hex(s);
    }

    /**
     * 随机生成salt进行口令sha256加密
     * @return {salt, hash}
     */
    public static String[] saltHash(String password) {
        String salt = uuid().substring(0, 8);
        return new String[]{salt, saltHash(salt, password)};
    }

    public static String aesEbcEnc(String password) {
        String key = StaticConfig.get("legacy_aes_key");
        Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, secretKey);
            byte[] encoded = cipher.doFinal(password.getBytes());
            return Hex.encodeHexString(encoded);
        } catch (GeneralSecurityException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 加salt进行口令sha256加密, 输出64位hex
     */
    public static String saltHash(String salt, String password) {
        char c = salt.charAt(0); c = (char)((c % 3) + 1);
        int count = 1 << c;
        for (int i = 0; i < count; i++) {
            salt = salt + password;
            salt = DigestUtils.sha256Hex(salt);
        }
        return salt;
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        /*String[] s = saltHash("123123");
        System.out.println(s[0] + " " + s[1]);
        for (int i = 0; i < 100; i++ ) {
            System.out.println(saltHash(s[0], "jackrock"));
            System.out.println(saltHash(s[0], "cc"));
            System.out.println(saltHash(s[0], "bbb"));
        }*/

        System.out.println("time: " + (System.currentTimeMillis() - t1));
    }
}
