package com.example.generator.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.StringJoiner;

/**
 * @author Administrator-YHL
 */
@Slf4j
public class IOUtil {

    public static final int BUFF_LENGTH = 1024 * 8;

    public static byte[] readBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        write(is, os);
        return os.toByteArray();
    }

    public static String readString(InputStream is) {
        return new String(readBytes(is));
    }

    public static String readString(BufferedReader reader) {
        StringJoiner join = new StringJoiner("\n", "", "\n");
        String buf = null;
        try {
            while ((buf = reader.readLine()) != null) {
                join.add(buf);
            }
        } catch (IOException e) {
            log.error("", e);
        }
        return join.toString();
    }

    public static boolean write(byte[] bytes, OutputStream out) {
        try {
            out.write(bytes, 0, bytes.length);
            out.flush();
            return true;
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

    public static boolean write(InputStream in, OutputStream out) {
        try {
            int read = 0; // 读取的字节数
            byte[] buffer = new byte[BUFF_LENGTH];
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            return true;
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

    public static void write(InputStream in, FileChannel out) {
        try {
            byte[] buff = new byte[BUFF_LENGTH];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                ByteBuffer buf = ByteBuffer.wrap(buff, 0, read);
                out.write(buf);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void write(FileChannel in, OutputStream out) {
        try {
            ByteBuffer buf = ByteBuffer.allocate(BUFF_LENGTH);
            byte[] buff = new byte[BUFF_LENGTH];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                buf.flip();
                buf.get(buff, 0, read);
                buf.clear();
                out.write(buff, 0, read);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void close(AutoCloseable... list) {
        if (list == null) {
            return;
        }
        for (AutoCloseable ac : list) {
            try {
                if (ac != null) {
                    ac.close();
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    public static void flush(Flushable... list) {
        if (list == null) {
            return;
        }
        for (Flushable ac : list) {
            try {
                if (ac != null) {
                    ac.flush();
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
