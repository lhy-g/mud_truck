package com.tc.test;

 
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class IOTest {

	@Test
	public void test() throws IOException {
			long time1 = System.currentTimeMillis();
		 ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
         FileInputStream fis = new FileInputStream("tmp_newCoupon.jpg");

         int len;
         while ((len = fis.read()) != -1) {
             // 把读取到的数据逐个写到内存中
             bos.write(len);
         }

         // 将缓冲区的数据全部获取出来，并赋值给 array
         // 该方法可以通过 String 的方法，使用指定的字符集，通过解码字节将缓冲区内容转换为字符串
         byte[] array = bos.toByteArray();
         long time2 = System.currentTimeMillis();
         System.out.println((time2-time1));
         // 指定解码的字符集
         System.out.println(bos.size());

         // 将缓冲区的内容转换为字符串。该方法使用平台默认字符集，通过解码字节将缓冲区内容转换为字符串。
//         System.out.println(bos.toString());

         fis.close();
 
	}
}
