package com.enenim.scaffold.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Security{

	private static final Logger logger = LoggerFactory.getLogger(Security.class);

	@Value("${security_decode_key}")
    private static String KEY;

	private static String encrypt(String input, String key){
        byte[] crypted = null;
	  try{
	    SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	      crypted = cipher.doFinal(input.getBytes());
	    }catch(Exception e){
	  		logger.error(e.getMessage());
	    }
		byte[] values = Base64.encodeBase64(crypted);
	    values = StringUtils.isEmpty(values) ? "".getBytes() : values;
	    return new String(values);
	}

	private static String decrypt(String input, String key){
	    byte[] output = "".getBytes();
	    try{
	      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.DECRYPT_MODE, skey);
	      output = cipher.doFinal(Base64.decodeBase64(input));
	    }catch(Exception e){
	    	logger.error(e.getMessage());
	    }
	    return new String(output);
	}

	public static String decrypt(String input){
		return decrypt(input, KEY);
	}

	public static String encypt(String value){
		String generateKey = final_key(KEY);
		return encrypt(value, generateKey);
	}

	private static String final_key(String key) {
		String sha1Value = DigestUtils.sha1Hex(key);
		return new StringBuilder(sha1Value).reverse().substring(4,20);
	}
}