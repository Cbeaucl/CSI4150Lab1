package crypto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

/**
 * A tester for the CryptoUtils class.
 * 
 * @author www.codejava.net
 *
 */
public class CryptoUtilsTest {
	public void main(String[] args) {
		KeyPair cryptoKeys = generateKeyPair();
		cryptoKeys.getPrivate();
		KeyPair signingKeys = generateKeyPair();
		String key = "Mary has one cat";
		File inputFile = new File("PlainText.txt");
		File encryptedFile = new File("PlainText.encrypted");
		File decryptedFile = new File("PlainText.decrypted");

		try {
			CryptoUtils.encrypt(key, inputFile, encryptedFile);
			CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
		} catch (CryptoException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public KeyPair generateKeyPair() {
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance("DSA", "SUN");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			generator.initialize(1024, random);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return generator.generateKeyPair();
	}

	public byte[] signData(PrivateKey privateKey, String fileName) throws NoSuchAlgorithmException,
			NoSuchProviderException, InvalidKeyException, SignatureException, IOException {
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		dsa.initSign(privateKey);
		try {
			FileInputStream stream = new FileInputStream(fileName);
			BufferedInputStream bufin = new BufferedInputStream(stream);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = bufin.read(buffer)) >= 0) {
				dsa.update(buffer, 0, len);
			}
			bufin.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to open file " + fileName + ".");
		}

		return dsa.sign();

	}
}
