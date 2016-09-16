package crypto;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * A tester for the CryptoUtils class.
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
    
    public KeyPair generateKeyPair()
    {
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
}
