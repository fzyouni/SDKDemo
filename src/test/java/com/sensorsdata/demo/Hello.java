package com.sensorsdata.demo;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * TODO
 *
 * @author fz
 * @version 1.0.0
 * @since 2021/07/28 19:09
 */
public class Hello {

  @Test
  public void test() throws IOException {
    FileOutputStream os = new FileOutputStream("file.log.2021-07-28");
    FileChannel channel = os.getChannel();
    FileLock lock = null;
    try {
      os.close();
      lock = channel.lock(0, Long.MAX_VALUE, false);
      os.write(2);
    } finally {
      if (lock != null) {
        try {
          lock.release();
        } catch (IOException e) {
          throw new RuntimeException("fail to release file lock.", e);
        }
      }
    }


  }
}
