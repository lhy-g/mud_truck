package com.tc.core.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tc.core.exception.BizException;
 

import cn.hutool.core.util.IdUtil;
import cn.hutool.log.Log;

/**
 * 服务器 
 * /var/app/forThemOnly/upload/ 目录下 : 
 * 样式:2007029530402103.jpg 为用户注册商家信息上传图片    或者 用户自定义模板 
 * 
 * 样式:tmp-1594263427.jpg 供用户下载促销图片,临时文件可删除
 *  
 * 
 * 
 * 
 * 
 * @author Admin
 *
 */
public class MyFileUtils {
	
	
	public static final String downloadPath = "/var/app/mud/download/";
	
	public static final String uploadPath = "/var/app/mud/upload/";

	public static final String urlPath = "https://n.3p3.top/upload/";
	
	 

	public static String fileUpload(MultipartFile file) {
		if (file != null) {
			BufferedOutputStream bw = null;
			File outFile = null;
			try {
				String fileName = file.getOriginalFilename();
				String fn = DateUtils.getGuid() + getFileType(fileName);
				// 判断是否有文件
				if (StringUtils.isNotBlank(fileName)) {
					// 输出到本地路径
					outFile = new File(uploadPath + fn);
					file.transferTo(outFile);
				}
				return urlPath + fn;
				//System.out.println(uploadPath + fn);
				//return uploadPath + fn;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getFileType(String filename) {
		if (filename.endsWith(".jpg") || filename.endsWith(".jepg")) {
			return ".jpg";
		}else if (filename.endsWith(".png") || filename.endsWith(".PNG")) {
			
			return ".png";
		} else {
			return "application/octet-stream";
		}
//		else {
//			throw new BizException("只支持JPG格式");
//		}

	}
	
	public static void fileWriteByte(File file, byte[] fimalByte) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(fimalByte);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fos);
			close(bos);
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
}
