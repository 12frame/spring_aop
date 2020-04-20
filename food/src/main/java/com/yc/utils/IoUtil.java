package com.yc.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 流操作工具
 */
public class IoUtil {
	
	
	
	/**
	 * 文件读取的封装
	 * @param file : 要读的文件
	 * @return   byte[]   字节数组
	 * @throws Exception
	 */
	public static byte[] readFile(  File file) throws Exception{
		byte[] buffer=null;
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		BufferedInputStream bis=null;
		try {
			 bis=new BufferedInputStream(  new FileInputStream( file ));
			byte[] bs=new byte[1024];
			int length=-1;
			while(  (length=bis.read(bs, 0, bs.length))!=-1){
				baos.write(bs, 0, length);
			}
			baos.flush();
			buffer= baos.toByteArray();
		} catch (Exception e) {
			LogUtil.error(e);
			throw e;
		} finally{
			if(   bis!=null){
				bis.close();
			}
			if(  baos!=null){
				baos.close();
			}
		}
		return buffer;
	}
	
	public static String parseFileSize(   long size){
		if(  size/1024/1024/1024/1024/1024>0 ){
			return size/1024/1024/1024/1024/1024+"PB";
		}else if(  size/1024/1024/1024/1024>0 ){
			return size/1024/1024/1024/1024+"TB";
		}else if(  size/1024/1024/1024>0 ){
			return size/1024/1024/1024+"GB";
		}else if(  size/1024/1024>0 ){
			return size/1024/1024+"MB";
		}else if(  size/1024>0 ){
			return size/1024+"KB";
		}else{
			return size+"B";
		}
	}
	
	public static void main(String[] args) throws Exception{
//		byte[] bs=IoUtil.readFile(   new File("c:\\key.dat") );
//		String s=new String(    bs );
//		System.out.println(   s );
		
		System.out.println(   IoUtil.parseFileSize(   1000000000)   );
	}
}
