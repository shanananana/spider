package com.shanananana.spider.code.platform.util;

import com.shanananana.spider.util.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import us.codecraft.webmagic.selector.Html;


import javax.swing.text.html.HTML;

/**
 * @author shana
 * @description 使用
 *
 * @date 2022/11/26 18:13
 */
public class SeleniumTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", Constant.CHROMEDIRVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("-headless");
        chromeOptions.addArguments("-window-size=1920,1080");
        chromeOptions.addArguments("-start-maximized");
        //创建Drive实例
        WebDriver driver = new ChromeDriver(chromeOptions);
        try {
            driver.get("https://www.baidu.com/");
            System.out.println(driver.getPageSource());
        } catch (Exception e) {
            System.out.printf("selenium exception %s %n", e.getMessage());
        } finally {
            driver.close();
            driver.quit();
        }


    }

}
