package com.cheng.manage.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 * AES加密解密工具类,加密的密匙必须是16位,如果是16位以上,例如32,64,128位,需要替换本地jdk文件
 * 找到本地jdk安装目录下\jre\lib\security目录,例如: D:\Java\JDK\jdk1.8.0_91\jre\lib\security
 * 替换local_policy.jar 和 US_export_policy.jar 替换的文件在当前工程jce_policy-8文件夹中
 */
public class AESCBCUtils {

	private String key;
	private String iv;

	/**
	 * @param key
	 *            加密密匙,必须是16位字符串,如果是16位以上需要修改本地jdk
	 * @param iv
	 *            加密向量
	 */
	public AESCBCUtils(String key, String iv) {
		this.key = key;
		this.iv = iv;
	}

	/**
	 *
	 * @param key
	 *            加密密匙,必须是16位字符串,如果是16位以上需要修改本地jdk
	 */
	public AESCBCUtils(String key) {
		this.key = key;
		this.iv = getIV();
	}

	private String getIV(){
		String chars  = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";
		Random rand = new Random();
		String s = "";
		for(int i = 0 ; i < 16 ; i++){
			s += chars.charAt((rand.nextInt(100)) % (chars.length()));
		}
		System.out.println("iv--->"+s);
		return s;

	}

	/**
	 * AES加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String data){
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return new sun.misc.BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("AES加密异常");
		}
	}

	/**
	 * AES解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String desEncrypt(String data) throws Exception {
		try {
			byte[] encrypted1 = new sun.misc.BASE64Decoder().decodeBuffer(data);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			throw new RuntimeException("AES解密异常");
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}






}
