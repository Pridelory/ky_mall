package com.wangmeng.mall.oss.ueditor.upload;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wangmeng.mall.oss.ueditor.PathFormat;
import com.wangmeng.mall.oss.ueditor.UeditorConfigKit;
import com.wangmeng.mall.oss.ueditor.define.AppInfo;
import com.wangmeng.mall.oss.ueditor.define.BaseState;
import com.wangmeng.mall.oss.ueditor.define.FileType;
import com.wangmeng.mall.oss.ueditor.define.State;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {
	private static final Logger logger = LoggerFactory.getLogger(BinaryUploader.class);
	private static Auth auth =  Auth.create(UeditorConfigKit.getAccessKey(),UeditorConfigKit.getSecretKey());
	private static UploadManager uploadManager = new UploadManager(new Configuration(Zone.autoZone()));

	public static final State save(HttpServletRequest request,
								   Map<String, Object> conf) {
//		FileItemStream fileStream = null;
//		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

//		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
//
//		if (isAjaxUpload) {
//			upload.setHeaderEncoding("UTF-8");
//		}

		InputStream is = null;
		try {
//			FileItemIterator iterator = upload.getItemIterator(request);
//
//			while (iterator.hasNext()) {
//				fileStream = iterator.next();
//
//				if (!fileStream.isFormField())
//					break;
//				fileStream = null;
//			}
//
//			if (fileStream == null) {
//				return new BaseState(false, 7);
//			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
			if(multipartFile==null){
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = multipartFile.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = (String) conf.get("rootPath") + savePath;

			//InputStream is = fileStream.openStream();
			is = multipartFile.getInputStream();
			State storageState = UeditorConfigKit.getFileManager().saveFile(is, (String) conf.get("rootPath") , savePath, maxSize);
//			is.close();
//			State storageState = QiniuFileManager.
			String token =	auth.uploadToken(UeditorConfigKit.getBucketName());
			try {
				Response response = uploadManager.put(new File(physicalPath),savePath,token);
				StringMap stringMap = response.jsonToMap();
				savePath =  UeditorConfigKit.getDomain() +"/"+ (String)stringMap.get("key");
			} catch (QiniuException e) {
				logger.error(e.getMessage(),e);
				return new BaseState(false, AppInfo.IO_ERROR);
			}
			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
//			String originFileName = fileStream.getName();
//			String suffix = FileType.getSuffixByFilename(originFileName);
//
//			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
//			savePath = savePath + suffix;
//
//			long maxSize = ((Long) conf.get("maxSize")).longValue();
//
//			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
//				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
//			}
//
//			savePath = PathFormat.parse(savePath, originFileName);
//
//			String rootPath = (String) conf.get("rootPath");
//			String filePath  = rootPath+savePath;
//			is = fileStream.openStream();
//			State storageState = UeditorConfigKit.getFileManager().saveFile(is, rootPath, savePath, maxSize);
//			if(!storageState.isSuccess()){
// 				return new BaseState(false, AppInfo.MAX_SIZE);
//			}
//			//copy到共享目录，供nginx代理做图片服务器
//			if(Globals.getBooleanProperty("file.store")){
//				File floder = new File(Globals.getProperty("file.floder"));
//				if(!floder.exists()){
//					floder.mkdir();
//				}
//				File file = new File(filePath);
//				FileUtils.copyFile(file, new File(Globals.getProperty("file.floder") + savePath));
//			}
//			Response response = null;
//			try {
////				savePath= savePath.replace("/upload/","ucampus/");
////				response = QiniuKit.simpleUpload(filePath, savePath);
////				StringMap stringMap = response.jsonToMap();
////				savePath = QiniuKit.getUrl() + (String)stringMap.get("key");
//			} catch (QiniuException e) {
//				logger.error(e.getMessage(),e);
//				return new BaseState(false, AppInfo.IO_ERROR);
//			}

//			if (storageState.isSuccess()) {
//				storageState.putInfo("url", PathFormat.format(savePath));
//				storageState.putInfo("type", suffix);
//				storageState.putInfo("original", originFileName + suffix);
//			}
//
//			return storageState;

		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		} finally {
			IOUtils.closeQuietly(is);
		}

	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
