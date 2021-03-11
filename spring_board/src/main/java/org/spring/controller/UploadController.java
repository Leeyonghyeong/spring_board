package org.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.utils.MediaUtils;
import org.spring.utils.UploadFileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/files")
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> upload(MultipartFile[] files) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> list = new ArrayList<Object>();
		
		for(MultipartFile file : files) {
			// 전달 받은 파일에 이미지 파일이 아닌 파일이 있을 경우 파일을 저장하지 않고 return 해준다.
			String formatName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			
			if(MediaUtils.getMediaType(formatName) == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		for(MultipartFile file : files) {
			logger.info("upload file : " + file.getOriginalFilename());
			Map<String, Object> mapList = new HashMap<String, Object>();
			
			mapList.put("file", UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()));
			mapList.put("size", file.getSize());
			
			list.add(mapList);
		}
		
		map.put("files", list);
		
		
		return  new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		
		InputStream input = null;
		ResponseEntity<byte[]> entity = null;
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType media = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			
			input = new FileInputStream(uploadPath + fileName);
			
			if(media != null) {
				headers.setContentType(media);
			}else { // 이미지 타입이 아닌 경우에는 다운로드 용으로 만드는 코드(파일을 이미지만 받기 때문에 현재는 필요하지 않음)
				fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859") + "\"");
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(input), headers, HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			input.close();
		}
		
		return entity;
	}
	
	@RequestMapping(value="/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFiles(@RequestBody String fileName) {
		logger.info("delete file : " + fileName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		MediaType media = MediaUtils.getMediaType(formatName);
		
		if(media != null) {
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			
			new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
		}
	
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		
		return new ResponseEntity<String>("DELETE", HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteAllFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteAllFiles(@RequestBody String files) {
		logger.info("delete all file : " + files);
		
		String[] AllFiles = files.split(",");
		
		if(AllFiles == null || AllFiles.length == 0) {
			return new ResponseEntity<String>("DELETED NULL", HttpStatus.OK);
		}
		
		for(String fileName : AllFiles) {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			MediaType media = MediaUtils.getMediaType(formatName);
			
			if(media != null) {
				String front = fileName.substring(0, 12);
				String end = fileName.substring(14);
				
				new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
			}
		
			new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		}
		
		return new ResponseEntity<String>("DELETED", HttpStatus.OK);
	}
}
