package com.tc.core.utils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Hashtable;
 
import java.util.Random;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
 
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.tc.core.exception.BizException;

import cn.hutool.core.codec.Base64Encoder;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/**
 * 我的二维码工具类
 * 
 */
@Slf4j
public class MyCodeUtil {

	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 830;
	// LOGO宽度
	private static final int WIDTH = 30;
	// LOGO高度
	private static final int HEIGHT = 30;

	private  static volatile int count=1;
	
	public  static final String COUPON_JPG = "tmp_coupon.jpg";

	public static final String NEW_COUPON_JPG = "tmp_newCoupon.jpg";
	
	//public  static final String couponPath = "D:\\新建文件夹 (2)\\coupon.jpg";

	//public static final String newCouponPath = "D:\\新建文件夹 (2)\\coupon2.jpg";
 
	
	
	/** */
	public static String generateQRCodeToEncode1(String content) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MyCodeUtil.encode(content, out);
		byte[] fimalByte = out.toByteArray();
		return Base64Encoder.encode(fimalByte);
	}

	/**
	 *  构建促销劵图片    (10倍压缩)
	 * @param content   二维码内容
	 * @param image     背景图片
	 * @return    生成Base64Encode
	 * @throws Exception
	 */
	public static String generateQRCodeToEncode3(String content, String image) throws Exception {
		File file = null;
		File toPic = null;
		try {
			if (StringUtils.isEmpty(image)) {
				return null;
			}
			byte[] fimalByte = generateQRCodeToByte(content, image);
			file = new File(System.currentTimeMillis() + COUPON_JPG);
			FileUtils.writeByteArrayToFile(file, fimalByte);
			toPic = new File(System.currentTimeMillis() + NEW_COUPON_JPG);
			Thumbnails.of(file).scale(0.2f).toFile(toPic);
			fimalByte = FileUtils.readFileToByteArray(toPic);
			return Base64Encoder.encode(fimalByte);
		} catch (Exception e) {
			return null;
		} finally {
			FileUtils.forceDelete(toPic);
			FileUtils.forceDelete(file);
		}

	}
	
	public static String generateQRCodeToEncode4(String content,String image) throws Exception {
//		FileOutputStream fos = null;
//		BufferedOutputStream bos = null;
//		ByteArrayOutputStream bas = null;
//		FileInputStream fis = null;
		try {
			if (StringUtils.isEmpty(image)) {
				return null;
			}
			byte[] fimalByte = generateQRCodeToByte(content, image);
			File file = new File(DateUtils.getGuid()+"tmp_coupon.jpg");
			FileUtils.writeByteArrayToFile(file, fimalByte);
			File toPic = new File(DateUtils.getGuid()+"tmp_newCoupon.jpg");
			// 压缩
			Thumbnails.of(file).scale(0.2f).toFile(toPic);
			fimalByte = FileUtils.readFileToByteArray(toPic);
			FileUtils.forceDelete(toPic);
			FileUtils.forceDelete(file);
			return Base64Encoder.encode(fimalByte);
		} catch (Exception e) {
			return null;
		} finally {
		}
		
	}
	
	public static <T extends java.io.Closeable> void close(T t) {
	    try {
	        if (t != null) {
	            t.close();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 *  构建促销劵图片
	 * @param content   二维码内容
	 * @param image     背景图片
	 * @return    生成Base64Encode
	 * @throws Exception
	 */
	public static String generateQRCodeToEncode(String content,String image) throws Exception {
		if(StringUtils.isEmpty(image)) {
			return null;
		}
		byte[] fimalByte  = generateQRCodeToByte(content,image);
	//	String path = "C:\\Users\\Admin\\Desktop\\1509\\"+count+".jpg";
		//byte2image(fimalByte,path);
		//count++;
		return Base64Encoder.encode(fimalByte);
	}
	
	 public static void byte2image(byte[] data,String path){
			if (data.length < 3 || path.equals(""))
				return;
			try {
				FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
				imageOutput.write(data, 0, data.length);
				imageOutput.close();
				System.out.println("Make Picture success,Please find image in " + path);
			} catch (Exception ex) {
				System.out.println("Exception: " + ex);
				ex.printStackTrace();
			}
	   }
	   //byte数组到16进制字符串
		public String byte2string(byte[] data) {
			if (data == null || data.length <= 1)
				return "0x";
			if (data.length > 200000)
				return "0x";
			StringBuffer sb = new StringBuffer();
			int buf[] = new int[data.length];
			// byte数组转化成十进制
			for (int k = 0; k < data.length; k++) {
				buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
			}
			// 十进制转化成十六进制
			for (int k = 0; k < buf.length; k++) {
				if (buf[k] < 16)
					sb.append("0" + Integer.toHexString(buf[k]));
				else
					sb.append(Integer.toHexString(buf[k]));
			}
			return "0x" + sb.toString().toUpperCase();
		}
	
	/**
	 * 生成一个二维码图片byte数组
	 * @param url  
	 * @return
	 */
	public static byte[] generateQRCodeToByte(String url,String imageNum) {
		try {
			BufferedImage small = MyCodeUtil.encode2(URLDecoder.decode(url,"utf-8"), null, true);
			String filePath = null;
			if(imageNum.length()==20) {
				filePath = MyFileUtils.uploadPath + imageNum;
			}else if(imageNum.startsWith("/var/app/")) {
				filePath = imageNum;
			}else {
				filePath ="images/" + imageNum + ".jpg";
			}
			BufferedImage finalImage = MyCodeUtil.overlapImage(filePath, small);
			byte[] fimalByte = MyCodeUtil.imageToBytes(finalImage, "jpg");
			
			return fimalByte;
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换BufferedImage 数据为byte数组
	 * 
	 * @param image  Image对象
	 * @param format image格式字符串.如"gif","png"
	 * @return byte数组
	 */
	public static byte[] imageToBytes(BufferedImage bImage, String format) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bImage, format, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public  static BufferedImage overlapImage(String filePath, BufferedImage small) {
		try {
			BufferedImage big;
			if (new File(filePath).exists()) {
				big = ImageIO.read(new File(filePath));
			} else {
				big = ImageIO.read(new ClassPathResource(filePath).getFile());
			}
//			System.out.println("图片信息:");
//			System.out.println(String.format("%.1f", new ClassPathResource(filePath).getFile().length() / 1024.0));// 源图大小
//			System.out.println(big.getWidth()); // 源图宽度
//			System.out.println(big.getHeight()); // 源图高度			 
			if(big.getHeight()<800) {
				throw new BizException("图片格式不对");
			}
			Graphics2D g = big.createGraphics();
//				int x = (big.getWidth() - small.getWidth()/2)-30;            
//				int y = (big.getHeight()-small.getHeight()/2)-30;            
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight())- 810;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			return big;
		} catch (Exception e) {
			log.error("overlapImage:",e);
		}
		return null;
	}

	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
				hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (imgPath == null || "".equals(imgPath)) {
			return image;
		}
		// 插入图片
		MyCodeUtil.insertImage(image, imgPath, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * 
	 * @param source       二维码图片
	 * @param imgPath      LOGO图片地址
	 * @param needCompress 是否压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
		File file = new File(imgPath);
		if (!file.exists()) {
			System.err.println("" + imgPath + "   该文件不存在！");
			return;
		}
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content      内容
	 * @param imgPath      LOGO地址
	 * @param destPath     存放目录
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
		BufferedImage image = MyCodeUtil.createImage(content, imgPath, needCompress);
		mkdirs(destPath);
		String file = new Random().nextInt(99999999) + ".jpg";
		ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
	}

	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
	 * 
	 * @author lanyuan Email: mmm333zzz520@163.com
	 * @date 2013-12-11 上午10:16:36
	 * @param destPath 存放目录
	 */
	public static void mkdirs(String destPath) {
		File file = new File(destPath);
		// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content  内容
	 * @param imgPath  LOGO地址
	 * @param destPath 存储地址
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, String destPath) throws Exception {
		MyCodeUtil.encode(content, imgPath, destPath, false);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content      内容
	 * @param destPath     存储地址
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String destPath, boolean needCompress) throws Exception {
		MyCodeUtil.encode(content, null, destPath, needCompress);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content  内容
	 * @param destPath 存储地址
	 * @throws Exception
	 */
	public static void encode(String content, String destPath) throws Exception {
		MyCodeUtil.encode(content, null, destPath, false);
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content      内容
	 * @param imgPath      LOGO地址
	 * @param output       输出流
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, OutputStream output, boolean needCompress)
			throws Exception {
		BufferedImage image = MyCodeUtil.createImage(content, imgPath, needCompress);
		ImageIO.write(image, FORMAT_NAME, output);
	}

	public static BufferedImage encode2(String content, String imgPath, boolean needCompress) throws Exception {
		BufferedImage image = MyCodeUtil.createImage(content, imgPath, needCompress);
		return image;
	}

	/**
	 * 生成二维码
	 * 
	 * @param content 内容
	 * @param output  输出流
	 * @throws Exception
	 */
	public static void encode(String content, OutputStream output) throws Exception {
		MyCodeUtil.encode(content, null, output, false);
	}

	/**
	 * 解析二维码
	 * 
	 * @param file 二维码图片
	 * @return
	 * @throws Exception
	 */
	public static String decode(File file) throws Exception {
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}

	/**
	 * 解析二维码
	 * 
	 * @param path 二维码图片地址
	 * @return
	 * @throws Exception
	 */
	public static String decode(String path) throws Exception {
		return MyCodeUtil.decode(new File(path));
	}

}
