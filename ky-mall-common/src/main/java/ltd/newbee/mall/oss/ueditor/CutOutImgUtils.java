package ltd.newbee.mall.oss.ueditor;

import java.io.InputStream;
import java.util.List;

/**
 * Created by roztop1 on 2017/4/14.
 */
public class CutOutImgUtils {
    public static void cutImg(String inputVideoPath, String outImagePath, String cmd) {

        //方法一：调用批处理程序，调用批处理文件ffmpeg.bat转换视频格式
//          try {
//              //调用批处理文件
//              Runtime.getRuntime().exec("cmd /c start C:\\Users\\Administrator\\Desktop\\test\\ffmpeg.bat " + videoRealPath + " " + imageRealPath);
//          } catch (IOException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//          }


        //方法二：通过命令提示符来调用需要添加系统路径（Path），调用menconder转换视频各种
//              commendF
//          .add("cmd.exe /c mencoder E:\\Eclipse2\\test.flv -o e:\\Eclipse2\\test.avi
//          -oac mp3lame -lameopts cbr:br=32
//          -ovc x264 -x264encopts bitrate=440 -vf scale=448:-3");

        //方法三：调用系统中的可执行程序调用ffmpeg 提取视屏缩略图
        List<String> commend = new java.util.ArrayList<String>();
        // 命令地址 "D:\\Program Files (x86)\\ffmpeg\\bin\\ffmpeg.exe"
        commend.add(cmd);
        commend.add("-i");
        commend.add(inputVideoPath);
        commend.add("-y");
        commend.add("-f");
        commend.add("image2");
        commend.add("-ss");
        commend.add("8");
        commend.add("-t");
        commend.add("0.001");
        commend.add("-s");
        commend.add("600*440");
        commend.add(outImagePath);
        try {

            ProcessBuilder builder = new ProcessBuilder();

            builder.command(commend);

            builder.redirectErrorStream(true);

            System.out.println("视频截图开始...");

            // builder.start();

            Process process = builder.start();

            InputStream in = process.getInputStream();

            byte[] re = new byte[1024];

            System.out.print("正在进行截图，请稍候");

            while (in.read(re) != -1) {

                System.out.print(".");

            }

            System.out.println("");

            in.close();

            System.out.println("视频截图完成...");

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("视频截图失败！");

        }
    }
}
