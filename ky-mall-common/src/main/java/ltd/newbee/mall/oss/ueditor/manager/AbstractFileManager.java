package ltd.newbee.mall.oss.ueditor.manager;


import ltd.newbee.mall.oss.ueditor.define.State;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractFileManager {
	
	/**
	 * 文件列表
	 * @param conf 配置
	 * @param start 开始
	 * @return state 状态接口
	 */
	public abstract State list(Map<String, Object> conf, int start);
	
	/**
	 * 保存二进制文件
	 * @param data 图片二进制信息
	 * @param rootPath 跟路径
	 * @param savePath 保存路径
	 * @return state 状态接口
	 */
	public abstract State saveFile(byte[] data, String rootPath, String savePath);
	
	/**
	 * 保存流文件
	 * @param is 流
	 * @param rootPath 跟路径
	 * @param savePath 保存路径
	 * @param maxSize 文件最大字节
	 * @return state 状态接口
	 */
	public abstract State saveFile(InputStream is, String rootPath, String savePath, long maxSize);
	
	/**
	 * 保存流文件
	 * @param is 流
	 * @param rootPath 跟路径
	 * @param savePath 保存路径
	 * @return state 状态接口
	 */
	public abstract State saveFile(InputStream is, String rootPath, String savePath);

	protected List<String> getAllowFiles(Object fileExt) {
		List<String> list = new ArrayList<String>();
		if (fileExt == null) {
			return list;
		}

		String[] exts = (String[]) fileExt;

		for (int i = 0, len = exts.length; i < len; i++) {
			String ext = exts[i];
			list.add(ext.replace(".", ""));
		}
		return list;
	}
}
