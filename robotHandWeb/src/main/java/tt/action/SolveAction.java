package tt.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import tt.entity.solve.Operation;
import tt.entity.solve.Orderlist;
import tt.entity.solve.SolveRoot;
import tt.util.HttpUtil;
import tt.util.JsonUtil;
import tt.util.PropertyUtil;

//@Component
public class SolveAction {
	private static WebDriver driver;
	public static final Logger logger = LoggerFactory.getLogger(SolveAction.class);
	private static String URL;
	private static String QUNAERURL;

	public SolveAction() {
		System.setProperty("webdriver.chrome.driver", PropertyUtil.getValueByKey("robot.properties", "driver"));
		ChromeOptions options = new ChromeOptions();
		URL = PropertyUtil.getValueByKey("robot.properties", "paidanurl");
		String chromeConf = PropertyUtil.getValueByKey("robot.properties", "chromconf");
		chromeConf = chromeConf.replace("{user}", "xiao");
		options.addArguments(chromeConf);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		QUNAERURL = "http://fuwu.qunar.com";
		driver.get(QUNAERURL);
	}

	public static void main(String[] args) {
	}

	public void run() {
		String response = HttpUtil.sendGet(URL, PropertyUtil.getValueByKey("robot.properties", "cookie"));
		JSONObject policyJsonObj = JSONObject.parseObject(response);
		SolveRoot solveRoot = JsonUtil.jsonToJavaObject(policyJsonObj, SolveRoot.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		List<Orderlist> orderLists = solveRoot.getData().getOrderList();
		System.out.println("本次处理订单：" + orderLists.size());
		System.out.println("====开始===");
		for (int i = 0; i < orderLists.size(); i++) {
			logger.info("=======正在处理{}=======", (i + 1));
			try {
				List<Operation> operations = orderLists.get(i).getOperation();
				for (Operation operation : operations) {
					if (operation.getName().equals("查看")) {
						driver.get(QUNAERURL + operation.getUrl());
						Thread.sleep(2000);
						List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='j-switch-form']/div"));
						System.out.println("有这么多div:" + findElements.size());
						if (findElements.size() == 0) {
							Thread.sleep(1000);
							findElements = driver.findElements(By.xpath("//*[@id='j-switch-form']/div"));
						}
						String text = driver.findElement(By.xpath("//*[@id='j-switch-form']/div[" + findElements.size()
								+ "]/table[1]/tbody/tr[1]/td[7]/p[1]")).getText();
						String text2 = driver.findElement(By.xpath(
								"//*[@id='j-switch-form']/div[" + findElements.size() + "]/table[1]/tbody/tr[1]/td[3]"))
								.getText();
						logger.info("处理:{}", text2);
						if (text2.equals("往返")) {
							logger.info("不处理");
							continue;
						}
						if (dateFormat.parse(text).getTime() < dateFormat.parse(date).getTime()) {
							logger.info("订单号：{},起飞日期：{}，可以点击。",
									driver.findElement(By.xpath("//*[@id='j-switch-form']/div[1]/div[2]/ul/li[1]/div"))
											.getText(),
									text);
							driver.findElement(
									By.xpath("//*[@id='js-mod-dispatchMsg']/div[2]/form/div/div[4]/div/button"))
									.click();
						} else {
							logger.info("起飞日期：{}，先不点击。", text);
						}

					}
				}
			} catch (Exception e) {
				System.out.println("发生异常，处理下一个单子。。。");
				continue;
			}
		}
		System.out.println("====ENd===");

	}

}
