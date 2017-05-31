package tt.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import tt.entity.AiChangeLinkVo;
import tt.util.HttpUtil;
import tt.util.JsonUtil;
import tt.util.PropertyUtil;

@Component
public class Actionyyw {
	private static WebDriver driver;
	public static final Logger logger = LoggerFactory.getLogger(Actionyyw.class);
	private static String URL;
	private static WebElement ELEMENT;
	private static String DATAURL;
	private static String CHANGEURL;
	private List<AiChangeLinkVo> list;
	private AiChangeLinkVo aiChangeLinkVo;
	private JavascriptExecutor javascriptExecutor;

	private SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

	public Actionyyw() {
		DATAURL = PropertyUtil.getValueByKey("robot.properties", "noticestateData");
		DATAURL = DATAURL.replace("{shopName}", "1");
	}

	private void init() {
		System.setProperty("webdriver.chrome.driver", PropertyUtil.getValueByKey("robot.properties", "driver"));
		URL = PropertyUtil.getValueByKey("robot.properties", "qunaerurl");
		URL = URL.replace("{domain}", "yyw");
		CHANGEURL = PropertyUtil.getValueByKey("robot.properties", "noticestateNew");
		ChromeOptions options = new ChromeOptions();
		String chromeConf = PropertyUtil.getValueByKey("robot.properties", "chromconf");
		chromeConf = chromeConf.replace("{user}", "guo");
		options.addArguments(chromeConf);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.get(URL);
		javascriptExecutor = (JavascriptExecutor) driver;
		logger.info("加载浏览器配置文件成功。。。。。。。。。。");
		logger.info("打开了{}订单管理。", URL);
		driver.findElement(By.id("nav_FN_ORDER")).click();
		try {
			Thread.sleep(2000);
			if (URL.indexOf("tyx") > 0) {
				driver.findElement(
						By.xpath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[1]/td/div/a"))
						.click();
				javascriptExecutor.executeScript("$('#toolbar').remove()");
			}
			driver = driver.switchTo().frame(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Actionyyw actionyyw = new Actionyyw();
		actionyyw.run();
	}

	public void execRobot(AiChangeLinkVo aiChangeLinkVo) {

		logger.info("进入订单管理操作...");

		ELEMENT = driver.findElement(By.name("orderNo"));
		ELEMENT.clear();
		ELEMENT.sendKeys(aiChangeLinkVo.getOrderNo());
		ELEMENT = driver.findElement(By.name("orderStartDate"));
		ELEMENT.clear();
		ELEMENT.sendKeys("2016-01-01");
		ELEMENT = driver.findElement(By.id("J_Form"));
		ELEMENT.submit();

		try {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id='j_g_b1']/table/tbody/tr/td[12]/a[1]")).click();
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// 回到主窗口
		driver.switchTo().defaultContent();
		logger.info("回到主窗口.....");
		driver.switchTo().frame(2);
		// 订单状态 完成为2
		try {
			String orderStatus = driver.findElement(By.xpath("//*[@id='j-switch-form']/div[1]/div[1]/div/input"))
					.getAttribute("value");
			logger.info("订单的状态是{}", orderStatus);
			if (!"2".equals(orderStatus)) {
				logger.info("订单的状态是{}，不作操作。",
						driver.findElement(By.xpath("//*[@id='j-switch-form']/div[1]/div[1]/div/h1")).getText());
				repetitionRobot(aiChangeLinkVo.getOrderNo());
				return;
			}
			ELEMENT = driver.findElement(By.xpath("//*[@id='js_airways_con']/ul[1]/li[1]/div/a"));
			ELEMENT.click();
			logger.info("打开了消息窗口。。。。。。");
			Thread.sleep(1000);
			List<WebElement> elements = driver
					.findElements(By.xpath("//*[@id='j_dia_sendInfo']/div/div[1]/div[1]/div[2]/label"));
			StringBuffer buffer = new StringBuffer();
			for (WebElement ELEMENT : elements) {
				buffer.append(ELEMENT.getText() + ",");
			}
			logger.info("消息类型一共有{}个选择,{}", elements.size(), buffer.toString());
			ELEMENT = driver.findElement(By.xpath("//*[@id='j_dia_sendInfo']/div/div[1]/div[1]/div[2]/label[4]"));
			ELEMENT.click();
			logger.info("点击了航变。。。。");
			Thread.sleep(1000);
			elements = driver.findElements(By.xpath("//*[@id='j_dia_sendInfo']/div/div[1]/div[3]/div/div/div/div"));
			StringBuffer buffer1 = new StringBuffer();
			int index = 1;
			for (int i = 0; i < elements.size(); i++) {
				String text = elements.get(i).getText();
				logger.info("抓取数据{}。", text);
				// String startcity = text.substring(text.indexOf(" "),
				// text.indexOf("(")).trim();
				String startcity = text.substring(text.indexOf("(") - 4, text.indexOf("(")).trim();// TODO、
				String endcity = text.substring(text.indexOf(")") + 2, text.indexOf(")") + 2 + 3).trim();
				logger.info((i + 1) + "、出发{},到达{}", startcity, endcity);
				if (aiChangeLinkVo.getNewstarcity().equals(startcity)
						&& aiChangeLinkVo.getNewreachcity().equals(endcity)) {// TODO
					index = (i + 1);
					break;
				}
				// if ("JUZ".equals(startcity) && "SZX".equals(endcity)) {//
				// TODO
				// index = (i + 1);
				// }

			}
			logger.info("地址一共有{}个选择\n{}", elements.size(), buffer1.toString());
			ELEMENT = driver.findElement(
					By.xpath("//*[@id='j_dia_sendInfo']/div/div[1]/div[3]/div/div/div/div[" + index + "]/label"));
			ELEMENT.click();

			elements = driver.findElements(By.tagName("div"));// 找到被选中弄的div
			logger.info("页面上有{}个div", elements.size());
			for (int i = 0; i < elements.size(); i++) {
				WebElement webElement = elements.get(i);
				if (webElement.getAttribute("style").equals("display: block;")
						&& webElement.getAttribute("class").equals("j-switch hide")
						&& webElement.getAttribute("data-sel-val").equals("1")
						&& webElement.getAttribute("data-sel-type").equals("1")) {
					logger.info("找到了他在{}", (i + 1));
					ELEMENT = webElement;
					break;

				}
			}

			// 选择航变类型
			elements = ELEMENT.findElement(By.name("airChange.type")).findElements(By.tagName("option"));
			logger.info("一共有{}选项.", elements.size());
			String type = "";
			if (aiChangeLinkVo.getChangereson() == 2 || aiChangeLinkVo.getChangereson() == 3) {
				type = "变更";
			} else if (aiChangeLinkVo.getChangereson() == 1) {
				if (null == aiChangeLinkVo.getIsProtected() || aiChangeLinkVo.getIsProtected() == 2) {
					type = "取消,无保护航班";
				} else if (aiChangeLinkVo.getIsProtected() == 1) {
					type = "取消,有保护航班";
				}
			} else {
				return;
			}
			// type = "取消,无保护航班";

			for (int i = 0; i < elements.size(); i++) {
				String attribute = elements.get(i).getAttribute("value");
				if (type.equals(attribute)) {// TODO
					elements.get(i).click();
					break;
				}
			}
			logger.info("该订单航班原因是:{}", type);
			if (type.equals("变更") || type.equals("取消,有保护航班")) {
				logger.info("填写变更后起降时间。。。。");
				String[] newstarttimeArray = aiChangeLinkVo.getNewstarttime().split(":");// 航变后起飞时间
				String[] newendtimeArray = aiChangeLinkVo.getNewendtime().split(":");// 航变后到达时间
				// WebElement findElement =
				// ELEMENT.findElement(By.name("airChange.dptDate"));
				// logger.info("airChange.dptDate" +
				// findElement.getAttribute("outerHTML"));
				// findElement.sendKeys("2017-05-17");
				// javascriptExecutor = (JavascriptExecutor) driver;
				// //logger.info("airChange.dptDate" +
				// findElement.getAttribute("outerHTML"));
				// javascriptExecutor
				// .executeAsyncScript("$(\"input[name='airChange.dptDate']:visible\").attr(\"class\",\"\")");
				// ELEMENT.findElement(By.name("airChange.dptDate")).sendKeys(aiChangeLinkVo.getNewstartdate());

				ELEMENT.findElement(By.name("airChange.dptHour")).sendKeys(newstarttimeArray[0]);
				ELEMENT.findElement(By.name("airChange.dptMinute")).sendKeys(newstarttimeArray[1]);
				ELEMENT.findElement(By.name("airChange.arrHour")).sendKeys(newendtimeArray[0]);
				ELEMENT.findElement(By.name("airChange.arrMinute")).sendKeys(newendtimeArray[1]);
				// String ml =
				// "$(\"input[name='airChange.dptDate']\").val('2017-05-11')";
				// String t = (String) javascriptExecutor
				// .executeScript("return
				// $(\"input[name='airChange.dptDate']\").val()");
				// logger.info("本页面input有{}个。", t);
				// javascriptExecutor.executeAsyncScript(ml);
				elements = ELEMENT.findElement(By.name("airChange.ensureKey")).findElements(By.tagName("option"));
				// logger.info(ELEMENT.getAttribute("outerHTML"));
				String oldstartdate = aiChangeLinkVo.getOldstartdate();
				String date2 = dateFormat2.format(new Date());
				logger.info("今天的时间:{}.", date2);
				logger.info("起飞的时间:{}.", oldstartdate);
				int index1 = 0;
				if (oldstartdate.equals(date2)) {
					index1 = 2;
				} else {
					index1 = 1;
				}
				for (int i = 0; i < elements.size(); i++) {
					WebElement webElement = elements.get(i);
					if (Integer.valueOf(webElement.getAttribute("value")) == index1) {
						webElement.click();
						logger.info("座位选择:{}。", webElement.getAttribute("value"));
						break;
					}
				}
			}
			if (type.equals("取消,有保护航班")) {
				logger.info("填写保护航班号{}", aiChangeLinkVo.getNewflightno());
				ELEMENT.findElement(By.name("airChange.flightNumProtect")).sendKeys(aiChangeLinkVo.getNewflightno());
			}

			// 提交表单
			driver.findElement(By.id("j_dia_sendInfo")).submit();
			Thread.sleep(1000);
			String text = driver.findElement(By.id("d-content-Alert")).getText();
			if ("提交成功".equals(text)) {
				String url = CHANGEURL + "?likeid=" + aiChangeLinkVo.getLikeid() + "&orderNo="
						+ aiChangeLinkVo.getOrderNo() + "&orderid=" + aiChangeLinkVo.getOrderid();
				logger.info("完成回去修改状态：{}", url);
				String sendGet = HttpUtil.sendGet(url, "utf-8", 60);
				if (Integer.valueOf(sendGet) == 1) {
					logger.info("回填成功！");
					logger.info("订单号{}提交成功。。。", aiChangeLinkVo.getOrderNo());
				} else {
					logger.info("回填失败！");
				}
			} else {
				logger.info("订单号{}提交失败。。。", aiChangeLinkVo.getOrderNo());
			}
			logger.info("回到主窗口。。。。");
			repetitionRobot(aiChangeLinkVo.getOrderNo());
		} catch (Exception e) {
			e.printStackTrace();
			repetitionRobot(aiChangeLinkVo.getOrderNo());
		}
	}

	private void repetitionRobot(String orderNo) {
		// 回到主窗口
		driver.switchTo().defaultContent();
		logger.info("======================" + orderNo + "处理完毕！===============================");
		driver.findElement(By.id("tabitem_nav_FN_ORDER")).click();
		driver.findElement(By.id("tabclose_orderlist" + orderNo + "view")).click();
		logger.info("关闭了详情，打开了订单管理！");
		driver.switchTo().frame(1);
		logger.info("选中了订单管理.........");
	}

	public String getTimeHHss(String time) {

		if (null == time || time.equals("")) {
			return "";
		}
		long lt = new Long(time);
		time = format.format(new Date(lt));
		return time;
	}

	public String getTimeyyyyHHss(String time) {

		if (null == time || time.equals("")) {
			return "";
		}
		long lt = new Long(time);
		time = dateFormat2.format(new Date(lt));
		return time;
	}

	public void run() {
		getNewList();
		if (list.size() > 0) {
			init();
			for (AiChangeLinkVo aiChangeLinkVo : list) {
				aiChangeLinkVo.setNewstarttime(getTimeHHss(aiChangeLinkVo.getNewstarttime()));
				aiChangeLinkVo.setNewendtime(getTimeHHss(aiChangeLinkVo.getNewendtime()));

				aiChangeLinkVo.setOldstartdate(getTimeyyyyHHss(aiChangeLinkVo.getOldstartdate()));
				aiChangeLinkVo.setOldenddate(getTimeyyyyHHss(aiChangeLinkVo.getOldenddate()));
				aiChangeLinkVo.setNewstartdate(getTimeyyyyHHss(aiChangeLinkVo.getNewstartdate()));
				// 航变理由为提前和延误的时候判断一下
				if (aiChangeLinkVo.getChangereson() == 2 || aiChangeLinkVo.getChangereson() == 3) {
					// 如果原来的出发日期、出发时间等于航变后的出发日期则不做处理
					if (aiChangeLinkVo.getOldstartdate().equals(aiChangeLinkVo.getNewstartdate())
							&& aiChangeLinkVo.getNewstarttime().equals(aiChangeLinkVo.getOldstarttime())) {
						logger.info("订单号{}原时间与新时间相同不予处理。", aiChangeLinkVo.getOrderNo());
						continue;
					}

				}
				// 如果航变后出发的城市为空且原时间不为空的话把原时间赋值给航变后出发的城市统一处理
				if (aiChangeLinkVo.getNewstarcity() == null && aiChangeLinkVo.getOldstarcity() != null) {
					aiChangeLinkVo.setNewstarcity(aiChangeLinkVo.getOldstarcity());
				}
				// 如果航变后到达的城市为空且原时间不为空的话把原时间赋值给航变后到达的城市统一处理
				if (aiChangeLinkVo.getNewreachcity() == null && aiChangeLinkVo.getOldreachcity() != null) {
					aiChangeLinkVo.setNewreachcity(aiChangeLinkVo.getOldreachcity());
				}
				logger.info(aiChangeLinkVo.toString());
				logger.info("======================{}开始处理===============================", aiChangeLinkVo.getOrderNo());
				logger.info("原出发日期：{},新出发日期：{}", aiChangeLinkVo.getOldstartdate(), aiChangeLinkVo.getNewstartdate());
				logger.info("原出发时间：{},新出发时间：{}", aiChangeLinkVo.getOldstarttime(), aiChangeLinkVo.getNewstarttime());
				logger.info("原到达日期：{}", aiChangeLinkVo.getOldenddate());
				logger.info("原到达时间：{}", aiChangeLinkVo.getOldendtime(), aiChangeLinkVo.getNewendtime());
				execRobot(aiChangeLinkVo);
			}
			driver.quit();
		}

	}

	private void getNewList() {
		String json = HttpUtil.sendGet(DATAURL, "UTF-8", 60);
		JSONObject policyJsonObj = JSONObject.parseObject(json);
		try {
			list = JsonUtil.jsonToJavaObject(policyJsonObj.getJSONArray("list"), AiChangeLinkVo.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		logger.info("本次即将处理{}个航变...", list.size());
	}

	// public static void main(String[] args) {
	// Actionyyw action = new Actionyyw();
	// action.run();
	// // SimpleDateFormat datFormat = new SimpleDateFormat("MM:dd");
	// // SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy:MM:dd");
	// // action.getNewList();
	// // for (int i = 0; i < action.list.size(); i++) {
	// // AiChangeLinkVo aiChangeLinkVo2 = action.list.get(i);
	// //
	// System.out.println(dateFormat1.format(aiChangeLinkVo2.getOldstartdate()));
	// //
	// System.out.println(dateFormat.format(aiChangeLinkVo2.getOldstartdate()));
	// // System.out.println(aiChangeLinkVo2.getTakeoffTime());
	// // String format = dateFormat.format(aiChangeLinkVo2.getOldstartdate());
	// // format += ":" + aiChangeLinkVo2.getTakeoffTime();
	// // System.out.println(format);
	// // System.out.println(format.split(":").length);
	// // }
	//
	// }

}
