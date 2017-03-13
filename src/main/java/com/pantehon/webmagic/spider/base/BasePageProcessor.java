package com.pantehon.webmagic.spider.base;

import org.apache.http.protocol.HTTP;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Random;

/**
 * Created by pantheon on 2017/3/10.
 */
public abstract class BasePageProcessor implements PageProcessor {

    public static final String[] USER_AGENTS = {
//===========PC 端=================
//            safari 5.1 – MAC
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
//            safari 5.1 – Windows
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
//            IE 9.0
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0",
//            IE 8.0
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
//            IE 7.0
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
//            IE 6.0
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
//            Firefox 4.0.1 – MAC
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
//            Firefox 4.0.1 – Windows
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
//            Opera 11.11 – MAC
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
//            Opera 11.11 – Windows
            "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
//            Chrome 17.0 – MAC
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
//            傲游（Maxthon）
            " Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",
//            世界之窗（The World） 3.x
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",
//            搜狗浏览器
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
//            360浏览器
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
//===========移动端=================
//            safari iOS 4.33 – iPhone
//            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
//            safari iOS 4.33 – iPad
//            "Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
//            Android QQ浏览器 For android
//            "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
//            BlackBerry
//            "Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; en) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.337 Mobile Safari/534.1+",
//            Nokia N97
//            "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18124"
    };

    protected Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(5000)
            .setTimeOut(5000)
            .setCharset(HTTP.UTF_8)
            .setCycleRetryTimes(3)
            .setUseGzip(true)
//            .setDomain(domain)
            .setUserAgent(USER_AGENTS[new Random().nextInt(USER_AGENTS.length)]);

    /**
     * 钩子方法
     * @param page
     */
    protected abstract void initProcess(Page page);

    @Override
    public void process(Page page) {
        initProcess(page);
    }

    @Override
    public Site getSite() {
        return site;
    }

}
