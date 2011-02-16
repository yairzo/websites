
package huard.iws.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * This utility class allows for string MD5 digestion.
 * @author miner
 */
public class MD5Encoder
{
    private static final int MD_LENGTH = 32 ;
    private static final String LEADING_ZEROS = "00000000000000000000000000000000" ;
    /**
     *
     */
    private MD5Encoder()
    {
        super();
    }

    /**
     * Create an md5 cipher using the given text. Result is returned as hexadecimal string.
     * @param text
     * @return
     */
    public static String digest(String text)
    {
        try
        {
            // Create a Message Digest from a Factory method
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            // Create the message
            byte[] message = text.getBytes();

            // Update the message digest with some more bytes
            // This can be performed multiple times before creating the hash
            messageDigest.update(message);

            // Create the digest from the message
            byte[] digestBytes = messageDigest.digest();

            // Now turn to hexadecimal string:

            BigInteger hash = new BigInteger(1, digestBytes);
            String result = hash.toString(16);
            if ( result.length() < MD_LENGTH )
                result = LEADING_ZEROS.substring(0, MD_LENGTH-result.length()) + result ;
            return result;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}