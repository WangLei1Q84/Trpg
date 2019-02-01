package com.brett.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.brett.Entity.ImageUtil;

@Controller
public class RoomController {
	
	private static final Logger log = LoggerFactory.getLogger(RoomController.class);


	@RequestMapping("/room")
	public String room() {
		return "coc/room";
	}
	
	@PutMapping("/article/img/upload") 
	public JSON uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile) { 
		if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) { 
			
			} 
		String contentType = multipartFile.getContentType(); 
		if (!contentType.contains("")) { 
			
			} 
		String root_fileName = multipartFile.getOriginalFilename(); 
		log.info("上传图片:name={},type={}", root_fileName, contentType); 
		String location="";
		//处理图片 User 
		String currentUser = ""; 
		//获取路径 String 
		String return_path = ImageUtil.getFilePath(currentUser); 
		String filePath = location + return_path; 
		log.info("图片保存路径={}", filePath); 
		String file_name = null; 
		JSON json=new JSON() {};
		try { 
			file_name = ImageUtil.saveImg(multipartFile, filePath);
			if(!StringUtils.isEmpty(file_name)){ 

				} 
			log.info("返回值：{}",json); 
			} 
		catch (IOException e) 
		{ 
		}
		return json; 
	}	
}
