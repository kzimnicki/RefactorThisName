package cc.explain.server.core;

import cc.explain.server.api.HashData;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;

/**
 * Hash code is based on Media Player Classic. In natural language it calculates: size + 64bit
 * checksum of the first and last 64k (even if they overlap because the file is smaller than
 * 128k).
 */
public class OpenSubtitlesHasher {
        
        /**
         * Size of the chunks that will be hashed in bytes (64 KB)
         */
        private static final int HASH_CHUNK_SIZE = 64 * 1024;
        
        
        public static String computeHash(File file) throws IOException {
                long size = file.length();
                long chunkSizeForFile = Math.min(HASH_CHUNK_SIZE, size);
                
                FileChannel fileChannel = new FileInputStream(file).getChannel();
                
                try {
                        long head = computeHashForChunk(fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, chunkSizeForFile));
                        long tail = computeHashForChunk(fileChannel.map(FileChannel.MapMode.READ_ONLY, Math.max(size - HASH_CHUNK_SIZE, 0), chunkSizeForFile));
                        
                        return String.format("%016x", size + head + tail);
                } finally {
                        fileChannel.close();
                }
        }
        

        public static String computeHash(InputStream stream, long length) throws IOException {
                System.out.println("length: "+length);
                int chunkSizeForFile = (int) Math.min(HASH_CHUNK_SIZE, length);
                
                // buffer that will contain the head and the tail chunk, chunks will overlap if length is smaller than two chunks
                byte[] chunkBytes = new byte[(int) Math.min(2 * HASH_CHUNK_SIZE, length)];
                
                DataInputStream in = new DataInputStream(stream);
                
                // first chunk
                in.readFully(chunkBytes, 0, chunkSizeForFile);
//                for (byte currentByte : chunkBytes) {
//                    System.out.println(String.format("%02X ", currentByte));
//                }
                
                long position = chunkSizeForFile;
                long tailChunkPosition = length - chunkSizeForFile;
                
                // seek to position of the tail chunk, or not at all if length is smaller than two chunks
                while (position < tailChunkPosition && (position += in.skip(tailChunkPosition - position)) >= 0);
                
                // second chunk, or the rest of the data if length is smaller than two chunks
                in.readFully(chunkBytes, chunkSizeForFile, chunkBytes.length - chunkSizeForFile);

//                for(byte currentByte : chunkBytes){
//                    System.out.println(currentByte);
//                }

                long head = computeHashForChunk(ByteBuffer.wrap(chunkBytes, 0, HASH_CHUNK_SIZE));
                System.out.println("head: "+head);
                long tail = computeHashForChunk(ByteBuffer.wrap(chunkBytes, chunkBytes.length - chunkSizeForFile, chunkSizeForFile));
                
                return String.format("%016x", length + head + tail);
        }
        

        private static long computeHashForChunk(ByteBuffer buffer) {
                
                LongBuffer longBuffer = buffer.order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();
                long hash = 0;
                    int x =0;
                while (longBuffer.hasRemaining()) {
                    long value = longBuffer.get();

                    hash += value;
                    if(x++> 8180){
                        System.out.println("value:" +value);
                        System.out.println("hash:"+ hash);
                    }

                }
                   System.out.println("x="+x);

                return hash;
        }

    public static void main(String[] args) throws IOException {
        File file = new File("f:\\The.Big.Bang.Theory.S06E08.HDTV.x264-LOL.mp4");
        InputStream fileInputStream = new FileInputStream(file);
        System.out.println(computeHash(file));

    }

    public static String computeHash(HashData data) {
        byte[] headBytes = Base64.decodeBase64(data.getHead().split(",")[1]);
//        for(byte currentByte : headBytes){
//            System.out.println(String.format("%02X ", currentByte));
//        }
        System.out.println("length: "+headBytes.length);
        System.out.println("first: "+headBytes[0]);

        byte[] tailBytes =Base64.decodeBase64(data.getTail().split(",")[1]);
        long head = computeHashForChunk(ByteBuffer.wrap(headBytes, 0, HASH_CHUNK_SIZE));
        long tail = computeHashForChunk(ByteBuffer.wrap(tailBytes, 0, HASH_CHUNK_SIZE));
        System.out.println(head);
        System.out.println(tail);
        return String.format("%016x", Long.valueOf(data.getSize()) + head + tail);
    }
}