package com.shanananana.spider.code.platform.sina;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author shana
 * @description TODO
 * @date 2022/11/23 21:50
 */
class SinaSpiderTest {

    @Test
    void hotBand() {
        System.out.println(SinaSpider.hotBand());
    }

    @Test
    void userInfo() {
        System.out.println(SinaSpider.userInfo());
    }

    @Test
    void useBlog() {
        System.out.println(SinaSpider.useBlog());
    }
}