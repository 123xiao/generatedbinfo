package tt.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.util.OS;

public class OCRHelper {
	public static void main(String[] args) {
		// OCRHelper ocrHelper = new OCRHelper();
		// try {
		// System.out.println(ocrHelper.recognizeText(
		// "https://user.qunar.com/captcha/api/image?k={en7mni(z&p=ucenter_login&c=ef7d278eca6d25aa6aec7272d57f0a9a"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println();

		try {
			download(
					"https://gifshow-10011997.file.myqcloud.com/upic/2017/05/09/07/BMjAxNzA1MDkwNzU2NTVfNzI5MDA1M18yMTM2NzcxNjAyXzFfMw==_hd.mp4",
					"D:\\Tool\\kuaishou\\1.mp4");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void download(String urlString, String filename) throws Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 输入流
		InputStream is = con.getInputStream();
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		OutputStream os = new FileOutputStream(filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}

	public static void saveToFile(String destUrl) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			String path = new File("imgcode").getAbsolutePath();
			fos = new FileOutputStream(path + "test.jpg");
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (IOException e) {
		} catch (ClassCastException e) {
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文件位置我防止在，项目同一路径
	 */
	private String tessPath = new File("D://Tool//Tesseract-OCR").getAbsolutePath();

	private final String LANG_OPTION = "-l";

	private final String EOL = System.getProperty("line.separator");

	/**
	 * @param imageFile
	 *            传入的图像文件
	 * @param imageFormat
	 *            传入的图像格式
	 * @return 识别后的字符串
	 */
	public String recognizeText(String codeUrl) throws Exception {
		download(codeUrl, "code.gif");
		File imageFile = new File("code.gif");
		File outputFile = new File(imageFile.getParentFile(), "output");

		StringBuffer strB = new StringBuffer();
		List<String> cmd = new ArrayList<String>();
		if (OS.isWindowsXP()) {
			cmd.add(tessPath + "\\tesseract");
		} else if (OS.isLinux()) {
			cmd.add("tesseract");
		} else {
			cmd.add(tessPath + "\\tesseract");
		}
		cmd.add("");
		cmd.add(outputFile.getName());
		cmd.add(LANG_OPTION);
		cmd.add("eng");

		ProcessBuilder pb = new ProcessBuilder();
		/**
		 * Sets this process builder's working directory.
		 */
		pb.directory(imageFile.getParentFile());
		cmd.set(1, imageFile.getName());
		pb.command(cmd);
		pb.redirectErrorStream(true);
		Process process = pb.start();
		// tesseract.exe 1.jpg 1 -l chi_sim
		// Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
		/**
		 * the exit value of the process. By convention, 0 indicates normal
		 * termination.
		 */
		// System.out.println(cmd.toString());
		int w = process.waitFor();
		if (w == 0)// 0代表正常退出
		{
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"), "UTF-8"));
			String str;

			while ((str = in.readLine()) != null) {
				strB.append(str).append(EOL);
			}
			in.close();
		} else {
			String msg;
			switch (w) {
			case 1:
				msg = "Errors accessing files. There may be spaces in your image's filename.";
				break;
			case 29:
				msg = "Cannot recognize the image or its selected region.";
				break;
			case 31:
				msg = "Unsupported image format.";
				break;
			default:
				msg = "Errors occurred.";
			}
			throw new RuntimeException(msg);
		}
		new File(outputFile.getAbsolutePath() + ".txt").delete();
		return strB.toString().replaceAll("\\s*", "");
	}
}