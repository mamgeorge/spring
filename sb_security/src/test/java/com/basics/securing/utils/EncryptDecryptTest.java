package com.basics.securing.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * AES      Advanced Encryption Standard
 * GCM      Galois Counter Mode
 * PBKDF2   Password-Based Key Derivation Function 2
 * HMAC     Hashbased Message Authentication Code
 * SHA256   Secure Hash Algorithm
 */
public class EncryptDecryptTest {
	//
	private static final String PATHFILE_LOCAL = "src/test/resources/";
	private static final String FILE_ORIG = PATHFILE_LOCAL + "txtOriginals.txt";
	private static final String FILE_ENCR = PATHFILE_LOCAL + "txtEncrypted.txt";
	//
	private static final String ASSERT_MSG = "ASSERT_MSG";
	private static final String TXT_CONTENT = "North America, Unites States, Ohio, Martin George";
	private static final String PASSWORD = "ABCD1234";

	@Test void test_getRandomIV( ) {
		//
		byte[] bytesIV = EncryptDecrypt.getRandomIV(EncryptDecrypt.BYTES_IV_LEN);
		System.out.println(bytesIV.toString() + " / " + bytesIV.length);
		Assertions.assertTrue(bytesIV.length > 10, ASSERT_MSG);
	}

	@Test void test_getAESKey( ) {
		//
		SecretKey secretKey = EncryptDecrypt.getAESKey(EncryptDecrypt.AES_KEY_BIT);
		String txtLines = showSecretKey(secretKey);
		//
		System.out.println("getAESKey " + txtLines);
		Assertions.assertTrue(secretKey.toString().length() > 10, ASSERT_MSG);
	}

	@Test void test_getAESKeyFromPassword( ) {
		//
		SecretKey secretKey = EncryptDecrypt.getAESKeyFromPassword(PASSWORD, EncryptDecrypt.SALT_SAMPLE.getBytes(UTF_8));
		String txtLines = showSecretKey(secretKey);
		//
		System.out.println("getAESKeyFromPassword " + txtLines);
		Assertions.assertTrue(secretKey.toString().length() > 10, ASSERT_MSG);
	}

	@Test void test_encrypt( ) {
		//
		String txtLines = "";
		byte[] bytesEncrypted = null;
		//
		byte[] bytesContent = TXT_CONTENT.getBytes();
		byte[] bytesIV = EncryptDecrypt.getRandomIV(EncryptDecrypt.BYTES_IV_LEN);
		SecretKey secretKey = EncryptDecrypt.getAESKey(EncryptDecrypt.AES_KEY_BIT);
		//
		bytesEncrypted = EncryptDecrypt.encrypt(bytesContent, secretKey, bytesIV);
		txtLines += "\tbytesEncrypted txt: " + new String(bytesEncrypted, UTF_8) + "\n";
		txtLines += "\tbytesEncrypted Hex: " + EncryptDecrypt.getHex(bytesEncrypted) + "\n";
		txtLines += "\tbytesEncrypted Blk: " + EncryptDecrypt.getHexWithBlockSize(bytesEncrypted, EncryptDecrypt.BLOCK_SIZE);
		System.out.println(txtLines);
		Assertions.assertTrue(bytesEncrypted.length > 10, ASSERT_MSG);
	}

	@Test void test_encryptPWD( ) {
		//
		String txtLines = "";
		//
		String contentEncryptedBase64 = EncryptDecrypt.encryptPWD(TXT_CONTENT, PASSWORD);
		try {
			Path pathEncr = Paths.get(FILE_ENCR);
			Files.write(pathEncr, contentEncryptedBase64.getBytes(UTF_8));
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		//
		byte[] bytesEncryptedBase64 = contentEncryptedBase64.getBytes(UTF_8);
		txtLines += "\tTXT_CONTENT.........: " + TXT_CONTENT + "\n";
		txtLines += "\tcontentEncrypted txt: " + contentEncryptedBase64 + "\n";
		txtLines += "\tcontentEncrypted Blk: " + EncryptDecrypt.getHexWithBlockSize(bytesEncryptedBase64, EncryptDecrypt.BLOCK_SIZE);
		System.out.println(txtLines);
		Assertions.assertTrue(contentEncryptedBase64.length() > 10, ASSERT_MSG);
	}

	@Test void test_encryptFile( ) {
		//
		EncryptDecrypt.encryptFile(FILE_ORIG, FILE_ENCR, PASSWORD);
		//
		String contentEncryptedBase64 = UtilityMain.getFileLocal(FILE_ENCR);
		String txtLines = "contentEncryptedBase64:\n" + contentEncryptedBase64;
		System.out.println(txtLines);
		Assertions.assertTrue(contentEncryptedBase64.length() > 10, ASSERT_MSG);
	}

	@Test void test_decrypt( ) {
		//
		String txtLines = "";
		//
		byte[] bytesContent = TXT_CONTENT.getBytes();
		byte[] bytesIV = EncryptDecrypt.getRandomIV(EncryptDecrypt.BYTES_IV_LEN);
		SecretKey secretKey = EncryptDecrypt.getAESKey(EncryptDecrypt.AES_KEY_BIT);
		byte[] bytesEncrypted = EncryptDecrypt.encrypt(bytesContent, secretKey, bytesIV);
		//
		String contentDecrypted = EncryptDecrypt.decrypt(bytesEncrypted, secretKey, bytesIV);
		//
		txtLines += "original!: " + TXT_CONTENT + "\n";
		txtLines += "decrypted: " + contentDecrypted;
		System.out.println(txtLines);
		Assertions.assertTrue(contentDecrypted.equals(TXT_CONTENT), ASSERT_MSG);
	}

	@Test void test_decryptPWD( ) {
		//
		String txtLines = "";
		//
		String contentEncrypted = EncryptDecrypt.encryptPWD(TXT_CONTENT, PASSWORD);
		String contentDecrypted = EncryptDecrypt.decryptPWD(contentEncrypted, PASSWORD);
		try {
			Path pathOrig = Paths.get(FILE_ORIG);
			Files.write(pathOrig, contentDecrypted.getBytes(UTF_8));
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		//
		txtLines += "original!: " + TXT_CONTENT + "\n";
		txtLines += "encrypted: " + contentEncrypted + "\n";
		txtLines += "decrypted: " + contentDecrypted;
		System.out.println(txtLines);
		Assertions.assertTrue(contentDecrypted.equals(TXT_CONTENT), ASSERT_MSG);
	}

	@Test void test_decryptFile( ) {
		//
		String contentDecrypted = EncryptDecrypt.decryptFile(FILE_ENCR, PASSWORD);
		//
		String txtLines = "contentDecrypted:\n" + contentDecrypted;
		System.out.println(txtLines);
		Assertions.assertTrue(contentDecrypted.length() > 10, ASSERT_MSG);
	}

	//############
	private String showSecretKey(SecretKey secretKey) {
		//
		String FRMT = "\t%-15s %s\n";
		String txtLines = "SecretKey" + "\n";
		txtLines += String.format(FRMT, "secretKey", secretKey.toString());
		txtLines += String.format(FRMT, "getAlgorithm", secretKey.getAlgorithm());
		txtLines += String.format(FRMT, "getFormat", secretKey.getFormat());
		txtLines += String.format(FRMT, "getEncoded", secretKey.getEncoded());
		txtLines += String.format(FRMT, "hexEncoded", EncryptDecrypt.getHex(secretKey.getEncoded()));
		txtLines +=
			String.format(FRMT, "hexBlocked", EncryptDecrypt.getHexWithBlockSize(secretKey.getEncoded(), EncryptDecrypt.BLOCK_SIZE));
		return txtLines;
	}
}