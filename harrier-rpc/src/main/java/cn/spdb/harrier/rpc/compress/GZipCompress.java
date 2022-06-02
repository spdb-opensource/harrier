
package cn.spdb.harrier.rpc.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Compress supports by GZIP.
 *
 * @author xiemalin
 * @since 1.4
 */
public class GZipCompress implements Compress {

    /** default buffer size. */
    private static final int BUFFER_SIZE = 256;


    public byte[] compress(byte[] array) throws IOException {
        return compress0(array);
    }
    
    public byte[] compress0(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }
        byte[] ret = null;
        try(ByteArrayOutputStream out = new ByteArrayOutputStream();
        		GZIPOutputStream gzip = new GZIPOutputStream(out);) {
            gzip.write(array);
            gzip.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            throw e;
        }
        return ret;
    }

    
    public byte[] unCompress(byte[] array) throws IOException {
        return unCompress0(array);
    }
    
    public byte[] unCompress0(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(array);

        try {
            GZIPInputStream gunzip = new GZIPInputStream(in) {
                
            };
            byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            throw e;
        }
        return out.toByteArray();
    }

}
