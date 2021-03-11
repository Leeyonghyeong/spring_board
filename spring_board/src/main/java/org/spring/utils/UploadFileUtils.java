package org.spring.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	// 파일 업로드를 위한 함수
	public static String uploadFile(String uploadPath, String orginalName, byte[] fileData) throws Exception {
		
		UUID uuid = UUID.randomUUID();
		
		String saveName = uuid.toString() + "_" + orginalName;
		String savePath = calcPath(uploadPath);
		
		File target = new File(uploadPath + savePath, saveName);
		
		FileCopyUtils.copy(fileData, target);
		
		String formatName = orginalName.substring(orginalName.lastIndexOf(".") + 1);
		String uploadFileName = null;
		
		// MediaUtils에 확장자명을 보낸 후 return 값이 null이 아닌 경우 썸네일을 생성해준다.
		if(MediaUtils.getMediaType(formatName) != null) {
			uploadFileName = makeThumbnail(uploadPath, savePath, saveName);
		} else {
			uploadFileName = "Not IMAGE";
		}
		
		return uploadFileName;
	}
	
	// 파일 업로드시 upload 폴더에 '년/월/일'에 맞는 폴더를 생성하기 위해 년/월/일 정보를 만들어 폴더 생성 함수를 호출 만들어진 폴더 path를 반환 하는 함수
	private static String calcPath(String uploadPath) {
		
		Calendar cal = Calendar.getInstance();
		
		String year = File.separator + cal.get(Calendar.YEAR);
		String month = year + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String date = month + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDir(uploadPath, year, month, date);
		
		return date;
	}
	
	// 년/월/일 에 맞는 폴더를 생성 하기 위한 함수
	private static void makeDir(String uploadPath, String... paths) {
		
		if(new File(paths[paths.length-1]).exists()) {
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			if(!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	
	// 이미지 파일의 썸네일 생성을 위한 함수
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
		
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 200); 
		
		String thumbnailName = uploadPath + path + File.separator + "t_" + fileName;
		
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
				
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

}
