package com.pantehon.webmagic.spider.downloader;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.pantehon.webmagic.spider.pool.WebClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by pantheon on 2017/3/10.
 */
public class HtmlUnitDownLoader implements Downloader, Closeable {
    private volatile WebClientPool webClientPool;
    private Logger logger = LoggerFactory.getLogger(HtmlUnitDownLoader.class);
    private int sleepTime = 0;
    private int poolSize = 1;

    public HtmlUnitDownLoader() {
    }

    public HtmlUnitDownLoader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public Page download(Request request, Task task) {
        this.checkInit();

        WebClient webClient = null;
        HtmlPage htmlPage = null;
        try {
            webClient = this.webClientPool.get();

            this.logger.info("downloading page " + request.getUrl());
            htmlPage = webClient.getPage(request.getUrl());

        } catch (InterruptedException var10) {
            this.logger.warn("interrupted", var10);
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep((long)this.sleepTime);
        } catch (InterruptedException var9) {
            var9.printStackTrace();
        }

        Site site = task.getSite();
        if(site.getCookies() != null) {
            Iterator webElement = site.getCookies().entrySet().iterator();
            CookieManager manager= webClient.getCookieManager();
            while(webElement.hasNext()) {
                Map.Entry content = (Map.Entry)webElement.next();
                Cookie cookie = new Cookie(task.getSite().getDomain(),(String)content.getKey(), (String)content.getValue());
                manager.addCookie(cookie);
            }
        }

        String content1 = htmlPage.asXml();
        Page page1 = new Page();
        page1.setRawText(content1);
        page1.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content1, request.getUrl())));
        page1.setUrl(new PlainText(request.getUrl()));
        page1.setRequest(request);
        this.webClientPool.returnToPool(webClient);
        return page1;
    }

    private void checkInit() {
        if(this.webClientPool == null) {
            synchronized(this) {
                this.webClientPool = new WebClientPool(this.poolSize);
            }
        }

    }

    public void setThread(int thread) {
        this.poolSize = thread;
    }

    public void close() throws IOException {
        this.webClientPool.closeAll();
    }
}
