package tt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	public static String executeGet(String url) throws Exception {
		HttpMethod method = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			HttpClient client = new HttpClient();
			method = new GetMethod(url);
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			br = new BufferedReader(new InputStreamReader(is));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
		}
	}

	public static String executeGet(String url, int connectionTimeout, int soTimeout) throws Exception {
		HttpMethod method = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			HttpClient client = new HttpClient();
			// 链接超时
			client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
			// 读取超时
			client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
			method = new GetMethod(url);
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			br = new BufferedReader(new InputStreamReader(is));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
		}
	}

	/**
	 * 根据编码
	 * 
	 * @param url
	 * @param cs
	 *            "utf-8"
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 * @throws Exception
	 */
	public static String executeGetCharSet(String url, String cs, int connectionTimeout, int soTimeout)
			throws Exception {
		HttpMethod method = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			HttpClient client = new HttpClient();
			// 链接超时
			client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
			// 读取超时
			client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
			method = new GetMethod(url);
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			br = new BufferedReader(new InputStreamReader(is, cs));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
		}
	}

	/**
	 * @Description: GET请求 验证往返程出票一些信息验证
	 * @author wsd
	 * @date 2014年9月26日 下午4:22:39
	 * @param url
	 * @param SOAPAction
	 * @param msg
	 * @param connTimeout
	 *            连接超时：秒
	 * @param readTimeout
	 *            读取超时：
	 * @return
	 */
	public static String get_WangFan_http(String url, int connTimeout, int readTimeout, String recvEncoding) {
		String res = "";
		HttpURLConnection connection = null;
		InputStream in = null;
		BufferedReader br = null;
		try {
			URL urlObj = new URL(url);
			connection = (HttpURLConnection) urlObj.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setConnectTimeout(1000 * connTimeout);// 超时时间
			connection.setReadTimeout(1000 * readTimeout);// 超时时间
			connection.connect();
			in = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, recvEncoding));
			StringBuffer sb = new StringBuffer();
			int charCount = -1;
			while ((charCount = br.read()) != -1) {
				sb.append((char) charCount);
			}
			res = sb.toString();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				if (null != br) {
					br.close();
				}
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
					connection = null;
				}
			} catch (Exception e2) {
			}
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(sendGet("http://localhost:8080/dtom/airChangeLinkNew/getLinkNewList.do", "utf-8", 60));
	}

	public static String sendGet(String url, String cookie) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Cookie", cookie);
			connection.setReadTimeout(60 * 1000);// 设置超时时间
			connection.setConnectTimeout(60 * 1000);
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendGet(String url, String ecound, int times) {

		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Cookie",
					"QN1=ezu0oljTQCRq48sxDPmSAg==; QN99=4493; QN29=31b8a106d4fb4c1c9d07e3b2c33e1ba5; _i=RBTKSlwUuVi7ZanwsXyHJcvYW1-x; QN43=3; QN42=bzbg2162; _q=U.jjojofe3404; _t=25007672; csrfToken=ql0lbjQocnmn9CkMol2dxnNmvoqxSHmz; _s=s_MJCLUVAF474NCIVYSEJDY3Q7AI; _mdp=66B5BB0EDA49353AB9A65424F1972F89; _uf=jjojofe3404; QN238=zh_cn; Hm_lvt_1e0dbf0beefb08ca0c09587ffacf3da1=1494495893,1494809862,1494849153,1495073515; Hm_lpvt_1e0dbf0beefb08ca0c09587ffacf3da1=1495076020");
			connection.setReadTimeout(times * 1000);// 设置超时时间
			connection.setConnectTimeout(times * 1000);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// //System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), ecound));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param times
	 *            超时时间
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param, String ecound) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + param;

			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setReadTimeout(60 * 1000);// 设置超时时间
			connection.setConnectTimeout(60 * 1000);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// //System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), ecound));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param times
	 *            超时时间
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param, String ecound, int times) {

		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setReadTimeout(times * 1000);// 设置超时时间
			connection.setConnectTimeout(times * 1000);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// //System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), ecound));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param times
	 *            超时时间
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, String ecound, int times) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setReadTimeout(times * 1000);// 设置超时时间
			conn.setConnectTimeout(times * 1000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 建立实际的连接
			conn.connect();
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), ecound));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
