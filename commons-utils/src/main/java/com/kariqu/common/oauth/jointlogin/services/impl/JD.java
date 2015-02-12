package com.kariqu.common.oauth.jointlogin.services.impl;

import com.google.common.collect.Maps;
import com.kariqu.common.oauth.jointlogin.OuterUserInfo;
import com.kariqu.common.oauth.jointlogin.services.PlatformUserInfo;
import com.kariqu.common.oauth.scribe.exceptions.OAuthException;
import com.kariqu.common.oauth.scribe.model.Token;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 京东用户信息读取实现
 */
class JD extends AnalyzeUserMap implements PlatformUserInfo {

    private static Log logger = LogFactory.getLog(JD.class);

    private static Map<String, String> parameter = new HashMap<String, String>();

    static {
        parameter.put("id", "uid");
        parameter.put("name", "user_nick");
        parameter.put("profile", "profile_image_url");
    }

    //access_token默认有效时间
    private String expireInOfString = "654282";

    @Override
    public OuterUserInfo getUserInfo(Token accessToken) {
        OuterUserInfo userinfo = getJointLoginInfo(getUserInfo_Map(accessToken), parameter);
        userinfo.setExpireIn(expireInOfString);
        return userinfo;
    }

    /**
     * 根据 accessToken 获取  access_token 和 uid 使用这个两个参数获取用户信息
     * userJson:{"id":2051094214,"idstr":"2051094214","screen_name":"小_枭","name":"小_枭","province":"44","city":"3","location":"广东 深圳","description":"","url":"","profile_image_url":"http://tp3.sinaimg.cn/2051094214/50/40021316437/1","profile_url":"u/2051094214","domain":"","weihao":"","gender":"m","followers_count":40,"friends_count":9,"statuses_count":25,"favourites_count":0,"created_at":"Fri Jul 06 11:12:59 +0800 2012","following":false,"allow_all_act_msg":false,"geo_enabled":true,"verified":false,"verified_type":-1,"remark":"","status":{"created_at":"Fri May 17 09:50:13 +0800 2013","id":3578881955074087,"mid":"3578881955074087","idstr":"3578881955074087","text":"ddddd   http://t.cn/zj2ptDU","source":"<a href=\"http://app.weibo.com/t/feed/2YcaiZ\" rel=\"nofollow\">喀日曲</a>","favorited":false,"truncated":false,"in_reply_to_status_id":"","in_reply_to_user_id":"","in_reply_to_screen_name":"","pic_urls":[],"geo":null,"reposts_count":0,"comments_count":0,"attitudes_count":0,"mlevel":0,"visible":{"type":0,"list_id":0}},"allow_all_comment":true,"avatar_large":"http://tp3.sinaimg.cn/2051094214/180/40021316437/1","verified_reason":"","follow_me":false,"online_status":0,"bi_followers_count":0,"lang":"zh-cn","star":0,"mbtype":0,"mbrank":0,"block_word":0}
     *
     * @param accessToken
     * @return
     * @throws java.io.IOException
     */
    private Map<String, Object> getUserInfo_Map(Token accessToken) {
        //{"access_token":"2.00kSLoOC23ciACdfb2b4f311e_qqvD","remind_in":"654282","expires_in":654282,"uid":"2051094214"}
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> access_map = null;
        try {
            access_map = mapper.readValue(accessToken.getRawResponse(), Map.class);
        } catch (IOException e) {
            logger.error("不能解析从京东返回的json:" + accessToken.getRawResponse());
            throw new OAuthException(e);
        }
        this.expireInOfString = access_map.get("expires_in").toString();

        Map<String, Object> userMap = Maps.newHashMap();
        userMap.put("uid",access_map.get("uid"));
        userMap.put("user_nick",access_map.get("user_nick"));
        userMap.put("profile_image_url","");
        userMap.put("accessToken", accessToken.getToken());
        userMap.put("flag", "JD");
        return userMap;
    }

}
