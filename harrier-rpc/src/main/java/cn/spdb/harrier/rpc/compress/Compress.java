
package cn.spdb.harrier.rpc.compress;

import java.io.IOException;

/**
 * Compress interface.
 *
 * @author xiemalin
 * @since 1.4
 */
public interface Compress {

    /**
     * Compress.
     *
     * @param array the array
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    byte[] compress(byte[] array) throws IOException;
    
    
    /**
     * Un compress.
     *
     * @param array the array
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    byte[] unCompress(byte[] array) throws IOException;
}
