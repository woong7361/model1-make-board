package com.study.encryption;

import com.study.exception.WrapCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.study.config.ConfigConst.*;
import static com.study.constant.ExceptionConstant.ENCRYPTION_EXCEPTION_MESSAGE;

/**
 * 암호화 클래스의 Cipher 구현체
 */
public class CipherEncrypt implements EncryptManager{
    private final Logger logger = LoggerFactory.getLogger(CipherEncrypt.class);

    @Override
    public String encrypt(String text){
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            logger.error("origin text: {}", text, e);
            throw new WrapCheckedException(ENCRYPTION_EXCEPTION_MESSAGE, e);
        }
    }
}
