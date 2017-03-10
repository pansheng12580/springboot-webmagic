package com.pantehon.webmagic.spider.pageprocessor;

import com.pantehon.webmagic.spider.base.BasePageProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;

/**
 * Created by pantheon on 2017/3/10.
 */
public class WxImagePageProcessor extends BasePageProcessor {
    Logger logger = LoggerFactory.getLogger(WxImagePageProcessor.class);

    @Override
    protected void initProcess(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
//        https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=2016%E6%9C%80%E7%81%AB%E5%BE%AE%E4%BF%A1%E5%A4%B4%E5%83%8F&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=1576805518,2372324562&os=2758918402,3864177144&simid=3398315908,141996361&pn=0&rn=1&di=26262972890&ln=1981&fr=&fmq=1489115377229_R&fm=rs4&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&oriquery=%E5%BE%AE%E4%BF%A1%E6%89%8B%E7%BB%98%E5%A4%B4%E5%83%8F%E7%AE%80%E7%BA%A6&objurl=http%3A%2F%2Fwww.qyqzj.com%2Fimg%2Fbd19460336.jpg&rpstart=0&rpnum=0&adpicid=0
//        page.addTargetRequests(page.getHtml().links().regex("https://image\\.baidu\\.com/search/[\\\\w\\\\-]").all());
        page.addTargetRequest(page.getUrl().toString());
        logger.info("page.addTargetRequest:"+ StringUtils.join(page.getTargetRequests(),"----"));

        //*[@id="imgid"]/div/ul/li[1]/div/a/img
//        page.putField("imagesUrl", page.getHtml().xpath("//idv[@class='imgbox']/a/img/@src").all());
        page.putField("imagesUrl", page.getHtml().xpath("//img/@src").all());
    }


}
