package ltd.newbee.mall.oss.ueditor;

import ltd.newbee.mall.oss.ueditor.manager.AbstractFileManager;
import ltd.newbee.mall.oss.ueditor.manager.DefaultFileManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * Ueditor 配置工具类，非线程安全，请全局配置
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2016年1月20日 下午10:57:22
 */
public class UeditorConfigKit {
	
	protected static AbstractFileManager fileManager = new DefaultFileManager();
	
	public static void setFileManager(AbstractFileManager fileManager) {
		UeditorConfigKit.fileManager = fileManager;
	}
	
	public static AbstractFileManager getFileManager() {
		return UeditorConfigKit.fileManager;
	}

	private static String type;
	private static String domain;
	private static String accessKey;
	private static String secretKey;
	private static String bucketName;
	private static String endPoint;

	static {
		try(InputStream in1 = UeditorConfigKit.class.getClassLoader().getResourceAsStream("application.properties")){
			Properties props = new Properties();
			if(in1 != null){
				props.load(in1);
				type = props.getProperty("oss.type","1");
				domain = props.getProperty("oss.domain","1");
				accessKey = props.getProperty("oss.accessKey","1");
				secretKey = props.getProperty("oss.secretKey","1");
				bucketName = props.getProperty("oss.bucketName","1");
				endPoint = props.getProperty("oss.end-point","1");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static String getType() {
		return type;
	}

	public static String getDomain() {
		return domain;
	}

	public static String getAccessKey() {
		return accessKey;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public static String getBucketName() {
		return bucketName;
	}

	public static String getEndPoint() {
		return endPoint;
	}
}
