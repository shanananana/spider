package com.shanananana.spider.code.platform.bilibili;

import com.alibaba.fastjson.JSON;
import com.shanananana.spider.code.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shana
 * @description 根据指定用户id检索用户信息
 * @date 2022/11/22 21:06
 */
@Slf4j
@Component
public class BiliBiliUserInfo {

    public static Long DEMO_UID = 27653080L;

    public static Integer DEMO_PAGESIZE = 30;


    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(getUserFollowingList()));
    }

    /**
     * 个人信息页面所需参数很简单,仅uid即可
     * 该接口返回了uid,昵称,性别,头像,签名,银币数,生日,vip等信息
     */
    public static String getUserNormalInfo() {
        return HttpUtil.doGetWithOutHeader(String.format("https://api.bilibili.com/x/space/acc/info?mid=%s&token=&platform=web&jsonp=jsonp", DEMO_UID));
    }

    /**
     * 粉丝数,关注数等信息接口
     * */
    public static String getStatInfo(){
        return HttpUtil.doGetWithOutHeader(String.format("https://api.bilibili.com/x/relation/stat?vmid=%s", DEMO_UID));
    }

    /***
     * 用户下的视频(按照发布时间倒序)
     * 翻页可以通过修改pn的值去翻页;
     * 可以通过返回的page参数计算出一共又多少页
     */
    public static String getUserVideo(){
        return HttpUtil.doGetWithOutHeader(String.format("https://api.bilibili.com/x/space/arc/search?mid=%s&ps=%s&tid=0&pn=1&keyword=&order=pubdate&order_avoided=true&jsonp=jsonp",DEMO_UID,DEMO_PAGESIZE));
    }

    /**
     * 用户的视频数量,动态数量,专栏数量
     * */
    public static String getUserDataResp(){
        return HttpUtil.doGetWithOutHeader(String.format("https://api.bilibili.com/x/space/navnum?mid=%s",DEMO_UID));
    }

    /**
     * 用户的关注列表
     * 需要加header
     *
     * 注意:返回形式是__jp6(result)。需要手动去除掉__jp6()才能转json
     * */
    public static String getUserFollowingList(){
        String requestUrl = String.format("https://api.bilibili.com/x/relation/followings?vmid=%s&pn=1&ps=20&order=desc&jsonp=jsonp&callback=__jp6",DEMO_UID);
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("referer",String.format("https://space.bilibili.com/%s/fans/follow",DEMO_UID));
        return HttpUtil.doGetWithHeader(requestUrl,headerMap);
    }
}
