package ltd.newbee.mall.oss.ueditor.upload;

import com.alibaba.fastjson.util.Base64;
import ltd.newbee.mall.oss.ueditor.PathFormat;
import ltd.newbee.mall.oss.ueditor.UeditorConfigKit;
import ltd.newbee.mall.oss.ueditor.define.AppInfo;
import ltd.newbee.mall.oss.ueditor.define.BaseState;
import ltd.newbee.mall.oss.ueditor.define.FileType;
import ltd.newbee.mall.oss.ueditor.define.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class Base64Uploader {
	private static final Logger logger = LoggerFactory.getLogger(Base64Uploader.class);

	public static State save(String content, Map<String, Object> conf) {
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));

		savePath = savePath + suffix;
		String rootPath = (String) conf.get("rootPath");
		String filePath = rootPath+savePath;
		State storageState = UeditorConfigKit.getFileManager().saveFile(data, rootPath, savePath);

//		//copy到共享目录，供nginx代理做图片服务器
//		if(Globals.getBooleanProperty("file.store")){
//			File floder = new File(Globals.getProperty("file.floder"));
//			if(!floder.exists()){
//				floder.mkdir();
//			}
//			File file = new File(filePath);
//			try {
//
//				FileUtils.copyFile(file, new File(Globals.getProperty("file.floder") + savePath));
//			} catch (IOException e) {
//				logger.error(e.getMessage(),e);
//				return new BaseState(false, AppInfo.IO_ERROR);
//			}
//		}
//		Response response = null;
//		try {
//			savePath= savePath.replace("/upload/","ucampus/");
//			response = QiniuKit.simpleUpload(filePath, savePath);
//			StringMap stringMap = response.jsonToMap();
//			savePath = QiniuKit.getUrl() + (String)stringMap.get("key");
//		} catch (QiniuException e) {
//
//			return new BaseState(false, AppInfo.IO_ERROR);
//		}


		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}
		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeFast(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}

}