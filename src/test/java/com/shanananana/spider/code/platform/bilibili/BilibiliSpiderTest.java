package com.shanananana.spider.code.platform.bilibili;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author shana
 * @description TODO
 * @date 2022/11/26 18:10
 */
class BilibiliSpiderTest {

    @Test
    void getUserNormalInfo() {
        System.out.println(BiliBiliSpider.getUserNormalInfo());
    }

    @Test
    void getStatInfo() {
        System.out.println(BiliBiliSpider.getStatInfo());
    }

    @Test
    void getUserVideo() {
        System.out.println(BiliBiliSpider.getUserVideo());
    }

    @Test
    void getUserDataResp() {
        System.out.println(BiliBiliSpider.getUserDataResp());
    }

    @Test
    void getUserFollowingList() {
        System.out.println(BiliBiliSpider.getUserFollowingList());
    }
}