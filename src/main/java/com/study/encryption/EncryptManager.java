package com.study.encryption;

/**
 * 암호화 클래스
 */
public interface EncryptManager {

    /**
     * 주어진 문자열을 암호화한다.
     * @param text
     * @return 암호화된 문자열 반환
     */
    public String encrypt(String text);
}
