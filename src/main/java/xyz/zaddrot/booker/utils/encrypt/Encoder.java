package xyz.zaddrot.booker.utils.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.IDEAEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by night on 14.06.2016.
 */
public class Encoder {
    final private Logger logger = LogManager.getLogger(getClass());
    private String getMac(){
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "." : ""));
            return sb.toString();
        } catch (UnknownHostException | SocketException e) { e.printStackTrace(); }
        return null;
    }

    private String key = DigestUtils.sha256Hex((getMac()));
    private PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new IDEAEngine()));

    /**
     * Шифровние на основе MAC-Адресса
     **/
    public String encryptData(String in) {
        byte[] inBytes = in.getBytes();
        cipher.init(true, new KeyParameter(key.getBytes()));
        byte[] outBytes = new byte[cipher.getOutputSize(inBytes.length)];
        int len = cipher.processBytes(inBytes, 0, inBytes.length, outBytes, 0);

        try {
            cipher.doFinal(outBytes, len);
        }catch(CryptoException e){ logger.error(e); }

        return new String(Hex.encode(outBytes));
    }
    public String decryptData(String text){
        try {
            byte[] inBytes = Hex.decode(text.getBytes());
            cipher.init(false, new KeyParameter(key.getBytes()));
            byte[] outBytes = new byte[cipher.getOutputSize(inBytes.length)];
            int len1 = cipher.processBytes(inBytes, 0, inBytes.length, outBytes, 0);

            try {
                cipher.doFinal(outBytes, len1);
            }catch(CryptoException ignore){}

            return new String(outBytes).trim();
        }catch (ArrayIndexOutOfBoundsException e) { logger.warn(e); }
        return null;
    }
}
