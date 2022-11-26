package com.shanananana.spider.code.platform.baidu;

import com.alibaba.fastjson.JSON;
import com.shanananana.spider.util.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shana
 * @description 用selenium的方式写百度热搜的爬虫
 * @date 2022/11/26 20:41
 */
public class BaiduSpider {

    public static void main(String[] args) {
        boardBySelenium();
    }

    public static void boardBySelenium() {
        System.setProperty("webdriver.chrome.driver", Constant.CHROMEDIRVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("-headless");
        chromeOptions.addArguments("-window-size=1920,1080");
        chromeOptions.addArguments("-start-maximized");
        //创建Drive实例
        WebDriver driver = new ChromeDriver(chromeOptions);
        try {
            driver.get("https://top.baidu.com/board?tab=realtime");
            Html html = new Html(driver.getPageSource());
            List<String> titleList = html.xpath("//div[@class='c-single-text-ellipsis']/text()").nodes().stream().map(String::valueOf).collect(Collectors.toList());
            System.out.println(String.format("百度热搜榜为\n\n %s", JSON.toJSON(titleList)));
        } catch (Exception e) {
            System.out.printf("selenium exception %s %n", e.getMessage());
        } finally {
            driver.close();
            driver.quit();
        }

    }
}
