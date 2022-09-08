
package cn.spdb.harrier.rpc.compress;

import java.io.IOException;

import org.xerial.snappy.Snappy;


public class SnappyCompress implements Compress {


    public byte[] compress(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }
        return Snappy.compress(array);
    }


    public byte[] unCompress(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }
        return Snappy.uncompress(array);
    }

    
}
