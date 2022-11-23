package com.shanananana.spider.code.platform.sina;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shanananana.spider.code.util.HttpUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shana
 * @description TODO
 * @date 2022/11/22 23:22
 */
@Component
@Slf4j
public class SinaSpider {

    private static final Long WEIBO_UID = 6593199887L;

    public static void main(String[] args) {
        System.out.println(userInfo());
    }

    /**
     * 微博热搜榜单
     */
    public static String hotBand() {
        String result = HttpUtil.doGetWithOutHeader("https://weibo.com/ajax/statuses/hot_band");
        List<String> titles = JSONObject.parseObject(result).getJSONObject("data").getJSONArray("band_list").stream().map(o -> JSONObject.parseObject(o.toString()).getString("word")).collect(Collectors.toList());
        return JSON.toJSONString(titles);
    }

    /**
     * 微博用户信息
     * 粉丝数,关注数,认证信息,地址,头像等
     * (网页端需要cookie。走m站接口获取)
     */
    public static String userInfo() {
        return HttpUtil.doGetWithOutHeader(String.format("https://m.weibo.cn/api/container/getIndex?jumpfrom=weibocom&type=uid&value=%s", WEIBO_UID));
    }

    /**
     * 用户博文信息
     *
     * @see com/shanananana/spider/doc/微博数据获取说明.md
     */
    public static String useBlog() {
        //翻页从当前页取到最小since_id后更新即可
        String since_id = "";
        String result = HttpUtil.doGetWithOutHeader(String.format("https://m.weibo.cn/api/container/getIndex?jumpfrom=weibocom&type=uid&value=%s", WEIBO_UID));
        UserContainerid userContainerid = JSONObject.parseObject(result, UserContainerid.class);
        assert userContainerid != null;
        Optional<String> containeridOpt = userContainerid.getData().tabsInfo.tabs.stream().filter(o -> "微博".equals(o.getTitle())).map(UserContainerid.DataBean.TabsInfoBean.TabsBean::getContainerid).findFirst();
        if (!containeridOpt.isPresent()) {
            return null;
        }
        String containerid = containeridOpt.get();
       return HttpUtil.doGetWithOutHeader(String.format("https://m.weibo.cn/api/container/getIndex?jumpfrom=weibocom&type=uid&value=%s&containerid=%s&since_id=%s",WEIBO_UID,containerid,since_id));
    }


    @NoArgsConstructor
    @Data
    public static class UserContainerid {

        /**
         * ok : 1
         * data : {"isVideoCoverStyle":1,"isStarStyle":0,"userInfo":{"id":6593199887,"screen_name":"原神","profile_image_url":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ","profile_url":"https://m.weibo.cn/u/6593199887?uid=6593199887&luicode=10000011&lfid=1005056593199887","statuses_count":2805,"verified":true,"verified_type":7,"verified_type_ext":50,"verified_reason":"原神官方微博","close_blue_v":false,"description":"","gender":"f","mbtype":12,"svip":1,"urank":4,"mbrank":6,"follow_me":false,"following":false,"follow_count":62,"followers_count":"693.2万","followers_count_str":"693.2万","cover_image_phone":"https://wx3.sinaimg.cn/crop.0.0.640.640.640/007ccotFly1h7qhc4alsjj30wi0widla.jpg","avatar_hd":"https://wx2.sinaimg.cn/orj480/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg","like":false,"like_me":false,"toolbar_menus":[{"type":"link","name":"聊天","pic":"http://h5.sinaimg.cn/upload/2015/06/12/2/toolbar_icon_discuss_default.png","params":{"scheme":"sinaweibo://messagelist?uid=6593199887&nick=原神"},"scheme":"https://passport.weibo.cn/signin/welcome?entry=mweibo&r=https%3A%2F%2Fm.weibo.cn%2Fapi%2Fcontainer%2FgetIndex%3Fjumpfrom%3Dweibocom%26type%3Duid%26value%3D6593199887"},{"type":"profile_follow","name":"关注","pic":"","params":{"uid":6593199887,"extparams":{"followcardid":"0001980001_6593199887_16796b64"}},"userInfo":{"id":6593199887,"idstr":"6593199887","screen_name":"原神","profile_image_url":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.50/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=ku%2BdwrIkUw","following":false,"verified":true,"verified_type":7,"remark":"","avatar_large":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ","avatar_hd":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.1024/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=71c2HgCyFH","verified_type_ext":50,"follow_me":false,"mbtype":12,"mbrank":6,"level":2,"type":1,"story_read_state":-1,"allow_msg":1,"friendships_relation":0,"close_friends_type":0,"special_follow":false}}]},"fans_scheme":"https://m.weibo.cn/c/attention/visit?showmenu=0&sign=6593199887&source=MQ%3D%3D&role=NA%3D%3D&luicode=10000011&lfid=1005056593199887","follow_scheme":"https://m.weibo.cn/c/attention/visit?showmenu=0&sign=6593199887&source=MA%3D%3D&role=NA%3D%3D&luicode=10000011&lfid=1005056593199887","tabsInfo":{"selectedTab":1,"tabs":[{"id":1,"tabKey":"profile","must_show":1,"hidden":0,"title":"精选","tab_type":"profile","containerid":"2302836593199887"},{"id":2,"tabKey":"weibo","must_show":1,"hidden":0,"title":"微博","tab_type":"weibo","containerid":"1076036593199887","apipath":"/profile/statuses","new_select_menu":1,"gender":"f","params":{"new_select_menu":1,"gender":"f"},"tab_icon":"https://h5.sinaimg.cn/upload/1059/799/2021/04/01/weibotab.png","tab_icon_dark":"https://h5.sinaimg.cn/upload/1059/799/2021/04/07/weibotab_dark.png","url":"/index/my"},{"id":7,"tabKey":"super_topic","must_show":0,"hidden":0,"title":"超话","tab_type":"cardlist","containerid":"2314756593199887"},{"id":10,"tabKey":"album","must_show":0,"hidden":0,"title":"相册","tab_type":"album","containerid":"1078036593199887"}]},"profile_ext":"touid:6593199887","scheme":"sinaweibo://userinfo?uid=6593199887&jumpfrom=weibocom&type=uid&value=6593199887&luicode=10000011&lfid=1078036593199887&v_p=42&uid=6593199887","showAppTips":0}
         */

        private int ok;
        private DataBean data;

        @NoArgsConstructor
        @Data
        public static class DataBean {
            /**
             * isVideoCoverStyle : 1
             * isStarStyle : 0
             * userInfo : {"id":6593199887,"screen_name":"原神","profile_image_url":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ","profile_url":"https://m.weibo.cn/u/6593199887?uid=6593199887&luicode=10000011&lfid=1005056593199887","statuses_count":2805,"verified":true,"verified_type":7,"verified_type_ext":50,"verified_reason":"原神官方微博","close_blue_v":false,"description":"","gender":"f","mbtype":12,"svip":1,"urank":4,"mbrank":6,"follow_me":false,"following":false,"follow_count":62,"followers_count":"693.2万","followers_count_str":"693.2万","cover_image_phone":"https://wx3.sinaimg.cn/crop.0.0.640.640.640/007ccotFly1h7qhc4alsjj30wi0widla.jpg","avatar_hd":"https://wx2.sinaimg.cn/orj480/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg","like":false,"like_me":false,"toolbar_menus":[{"type":"link","name":"聊天","pic":"http://h5.sinaimg.cn/upload/2015/06/12/2/toolbar_icon_discuss_default.png","params":{"scheme":"sinaweibo://messagelist?uid=6593199887&nick=原神"},"scheme":"https://passport.weibo.cn/signin/welcome?entry=mweibo&r=https%3A%2F%2Fm.weibo.cn%2Fapi%2Fcontainer%2FgetIndex%3Fjumpfrom%3Dweibocom%26type%3Duid%26value%3D6593199887"},{"type":"profile_follow","name":"关注","pic":"","params":{"uid":6593199887,"extparams":{"followcardid":"0001980001_6593199887_16796b64"}},"userInfo":{"id":6593199887,"idstr":"6593199887","screen_name":"原神","profile_image_url":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.50/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=ku%2BdwrIkUw","following":false,"verified":true,"verified_type":7,"remark":"","avatar_large":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ","avatar_hd":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.1024/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=71c2HgCyFH","verified_type_ext":50,"follow_me":false,"mbtype":12,"mbrank":6,"level":2,"type":1,"story_read_state":-1,"allow_msg":1,"friendships_relation":0,"close_friends_type":0,"special_follow":false}}]}
             * fans_scheme : https://m.weibo.cn/c/attention/visit?showmenu=0&sign=6593199887&source=MQ%3D%3D&role=NA%3D%3D&luicode=10000011&lfid=1005056593199887
             * follow_scheme : https://m.weibo.cn/c/attention/visit?showmenu=0&sign=6593199887&source=MA%3D%3D&role=NA%3D%3D&luicode=10000011&lfid=1005056593199887
             * tabsInfo : {"selectedTab":1,"tabs":[{"id":1,"tabKey":"profile","must_show":1,"hidden":0,"title":"精选","tab_type":"profile","containerid":"2302836593199887"},{"id":2,"tabKey":"weibo","must_show":1,"hidden":0,"title":"微博","tab_type":"weibo","containerid":"1076036593199887","apipath":"/profile/statuses","new_select_menu":1,"gender":"f","params":{"new_select_menu":1,"gender":"f"},"tab_icon":"https://h5.sinaimg.cn/upload/1059/799/2021/04/01/weibotab.png","tab_icon_dark":"https://h5.sinaimg.cn/upload/1059/799/2021/04/07/weibotab_dark.png","url":"/index/my"},{"id":7,"tabKey":"super_topic","must_show":0,"hidden":0,"title":"超话","tab_type":"cardlist","containerid":"2314756593199887"},{"id":10,"tabKey":"album","must_show":0,"hidden":0,"title":"相册","tab_type":"album","containerid":"1078036593199887"}]}
             * profile_ext : touid:6593199887
             * scheme : sinaweibo://userinfo?uid=6593199887&jumpfrom=weibocom&type=uid&value=6593199887&luicode=10000011&lfid=1078036593199887&v_p=42&uid=6593199887
             * showAppTips : 0
             */

            private int isVideoCoverStyle;
            private int isStarStyle;
            private UserInfoBeanX userInfo;
            private String fans_scheme;
            private String follow_scheme;
            private TabsInfoBean tabsInfo;
            private String profile_ext;
            private String scheme;
            private int showAppTips;

            @NoArgsConstructor
            @Data
            public static class UserInfoBeanX {
                /**
                 * id : 6593199887
                 * screen_name : 原神
                 * profile_image_url : https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ
                 * profile_url : https://m.weibo.cn/u/6593199887?uid=6593199887&luicode=10000011&lfid=1005056593199887
                 * statuses_count : 2805
                 * verified : true
                 * verified_type : 7
                 * verified_type_ext : 50
                 * verified_reason : 原神官方微博
                 * close_blue_v : false
                 * description :
                 * gender : f
                 * mbtype : 12
                 * svip : 1
                 * urank : 4
                 * mbrank : 6
                 * follow_me : false
                 * following : false
                 * follow_count : 62
                 * followers_count : 693.2万
                 * followers_count_str : 693.2万
                 * cover_image_phone : https://wx3.sinaimg.cn/crop.0.0.640.640.640/007ccotFly1h7qhc4alsjj30wi0widla.jpg
                 * avatar_hd : https://wx2.sinaimg.cn/orj480/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg
                 * like : false
                 * like_me : false
                 * toolbar_menus : [{"type":"link","name":"聊天","pic":"http://h5.sinaimg.cn/upload/2015/06/12/2/toolbar_icon_discuss_default.png","params":{"scheme":"sinaweibo://messagelist?uid=6593199887&nick=原神"},"scheme":"https://passport.weibo.cn/signin/welcome?entry=mweibo&r=https%3A%2F%2Fm.weibo.cn%2Fapi%2Fcontainer%2FgetIndex%3Fjumpfrom%3Dweibocom%26type%3Duid%26value%3D6593199887"},{"type":"profile_follow","name":"关注","pic":"","params":{"uid":6593199887,"extparams":{"followcardid":"0001980001_6593199887_16796b64"}},"userInfo":{"id":6593199887,"idstr":"6593199887","screen_name":"原神","profile_image_url":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.50/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=ku%2BdwrIkUw","following":false,"verified":true,"verified_type":7,"remark":"","avatar_large":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ","avatar_hd":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.1024/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=71c2HgCyFH","verified_type_ext":50,"follow_me":false,"mbtype":12,"mbrank":6,"level":2,"type":1,"story_read_state":-1,"allow_msg":1,"friendships_relation":0,"close_friends_type":0,"special_follow":false}}]
                 */

                private long id;
                private String screen_name;
                private String profile_image_url;
                private String profile_url;
                private int statuses_count;
                private boolean verified;
                private int verified_type;
                private int verified_type_ext;
                private String verified_reason;
                private boolean close_blue_v;
                private String description;
                private String gender;
                private int mbtype;
                private int svip;
                private int urank;
                private int mbrank;
                private boolean follow_me;
                private boolean following;
                private int follow_count;
                private String followers_count;
                private String followers_count_str;
                private String cover_image_phone;
                private String avatar_hd;
                private boolean like;
                private boolean like_me;
                private List<ToolbarMenusBean> toolbar_menus;

                @NoArgsConstructor
                @Data
                public static class ToolbarMenusBean {
                    /**
                     * type : link
                     * name : 聊天
                     * pic : http://h5.sinaimg.cn/upload/2015/06/12/2/toolbar_icon_discuss_default.png
                     * params : {"scheme":"sinaweibo://messagelist?uid=6593199887&nick=原神"}
                     * scheme : https://passport.weibo.cn/signin/welcome?entry=mweibo&r=https%3A%2F%2Fm.weibo.cn%2Fapi%2Fcontainer%2FgetIndex%3Fjumpfrom%3Dweibocom%26type%3Duid%26value%3D6593199887
                     * userInfo : {"id":6593199887,"idstr":"6593199887","screen_name":"原神","profile_image_url":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.50/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=ku%2BdwrIkUw","following":false,"verified":true,"verified_type":7,"remark":"","avatar_large":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ","avatar_hd":"https://tvax2.sinaimg.cn/crop.0.0.1024.1024.1024/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=71c2HgCyFH","verified_type_ext":50,"follow_me":false,"mbtype":12,"mbrank":6,"level":2,"type":1,"story_read_state":-1,"allow_msg":1,"friendships_relation":0,"close_friends_type":0,"special_follow":false}
                     */

                    private String type;
                    private String name;
                    private String pic;
                    private ParamsBean params;
                    private String scheme;
                    private UserInfoBean userInfo;

                    @NoArgsConstructor
                    @Data
                    public static class ParamsBean {
                        /**
                         * scheme : sinaweibo://messagelist?uid=6593199887&nick=原神
                         */

                        private String scheme;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class UserInfoBean {
                        /**
                         * id : 6593199887
                         * idstr : 6593199887
                         * screen_name : 原神
                         * profile_image_url : https://tvax2.sinaimg.cn/crop.0.0.1024.1024.50/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=ku%2BdwrIkUw
                         * following : false
                         * verified : true
                         * verified_type : 7
                         * remark :
                         * avatar_large : https://tvax2.sinaimg.cn/crop.0.0.1024.1024.180/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=wffNkcTVqJ
                         * avatar_hd : https://tvax2.sinaimg.cn/crop.0.0.1024.1024.1024/007ccotFly8h7qfw4s31ij30sg0sgdij.jpg?KID=imgbed,tva&Expires=1669225707&ssig=71c2HgCyFH
                         * verified_type_ext : 50
                         * follow_me : false
                         * mbtype : 12
                         * mbrank : 6
                         * level : 2
                         * type : 1
                         * story_read_state : -1
                         * allow_msg : 1
                         * friendships_relation : 0
                         * close_friends_type : 0
                         * special_follow : false
                         */

                        private long id;
                        private String idstr;
                        private String screen_name;
                        private String profile_image_url;
                        private boolean following;
                        private boolean verified;
                        private int verified_type;
                        private String remark;
                        private String avatar_large;
                        private String avatar_hd;
                        private int verified_type_ext;
                        private boolean follow_me;
                        private int mbtype;
                        private int mbrank;
                        private int level;
                        private int type;
                        private int story_read_state;
                        private int allow_msg;
                        private int friendships_relation;
                        private int close_friends_type;
                        private boolean special_follow;
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class TabsInfoBean {
                /**
                 * selectedTab : 1
                 * tabs : [{"id":1,"tabKey":"profile","must_show":1,"hidden":0,"title":"精选","tab_type":"profile","containerid":"2302836593199887"},{"id":2,"tabKey":"weibo","must_show":1,"hidden":0,"title":"微博","tab_type":"weibo","containerid":"1076036593199887","apipath":"/profile/statuses","new_select_menu":1,"gender":"f","params":{"new_select_menu":1,"gender":"f"},"tab_icon":"https://h5.sinaimg.cn/upload/1059/799/2021/04/01/weibotab.png","tab_icon_dark":"https://h5.sinaimg.cn/upload/1059/799/2021/04/07/weibotab_dark.png","url":"/index/my"},{"id":7,"tabKey":"super_topic","must_show":0,"hidden":0,"title":"超话","tab_type":"cardlist","containerid":"2314756593199887"},{"id":10,"tabKey":"album","must_show":0,"hidden":0,"title":"相册","tab_type":"album","containerid":"1078036593199887"}]
                 */

                private int selectedTab;
                private List<TabsBean> tabs;

                @NoArgsConstructor
                @Data
                public static class TabsBean {
                    /**
                     * id : 1
                     * tabKey : profile
                     * must_show : 1
                     * hidden : 0
                     * title : 精选
                     * tab_type : profile
                     * containerid : 2302836593199887
                     * apipath : /profile/statuses
                     * new_select_menu : 1
                     * gender : f
                     * params : {"new_select_menu":1,"gender":"f"}
                     * tab_icon : https://h5.sinaimg.cn/upload/1059/799/2021/04/01/weibotab.png
                     * tab_icon_dark : https://h5.sinaimg.cn/upload/1059/799/2021/04/07/weibotab_dark.png
                     * url : /index/my
                     */

                    private int id;
                    private String tabKey;
                    private int must_show;
                    private int hidden;
                    private String title;
                    private String tab_type;
                    private String containerid;
                    private String apipath;
                    private int new_select_menu;
                    private String gender;
                    private ParamsBeanX params;
                    private String tab_icon;
                    private String tab_icon_dark;
                    private String url;

                    @NoArgsConstructor
                    @Data
                    public static class ParamsBeanX {
                        /**
                         * new_select_menu : 1
                         * gender : f
                         */

                        private int new_select_menu;
                        private String gender;
                    }
                }
            }
        }
    }
}
