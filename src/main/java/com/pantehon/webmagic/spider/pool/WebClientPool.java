package com.pantehon.webmagic.spider.pool;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pantheon on 2017/3/10.
 */
public class WebClientPool {

    private Logger logger = LoggerFactory.getLogger(WebClientPool.class);

    private final static int DEFAULT_CAPACITY = 5;

    private final int capacity;

    private final static int STAT_RUNNING = 1;

    private final static int STAT_CLODED = 2;

    private AtomicInteger stat = new AtomicInteger(STAT_RUNNING);

    private WebClient mWebClient = null;
    private boolean mAutoQuitDriver = true;

    private static final String DRIVER_FIREFOX = "firefox";
    private static final String DRIVER_CHROME = "chrome";
    private static final String DRIVER_PHANTOMJS = "phantomjs";

    /**
     * Configure the GhostDriver, and initialize a WebDriver instance. This part
     * of code comes from GhostDriver.
     * https://github.com/detro/ghostdriver/tree/master/test/java/src/test/java/ghostdriver
     *
     * @author bob.li.0718@gmail.com
     * @throws IOException
     */
    public void configure() throws IOException {


        mWebClient = new WebClient(BrowserVersion.getDefault());

        // 1 启动JS
        mWebClient.getOptions().setJavaScriptEnabled(true);
        // 2 禁用Css，可避免自动二次请求CSS进行渲染
        mWebClient.setCssErrorHandler(new SilentCssErrorHandler());
        mWebClient.setAjaxController(new NicelyResynchronizingAjaxController());
        mWebClient.getOptions().setCssEnabled(true);
        // 3 启动客户端重定向
        mWebClient.getOptions().setRedirectEnabled(true);

        mWebClient.getOptions().setAppletEnabled(false);

        mWebClient.getOptions().setPopupBlockerEnabled(true);

        // 4 js运行错误时，是否抛出异常
        //mWebClient.getOptions().setThrowExceptionOnScriptError(false);
        // 5 设置超时
        mWebClient.getOptions().setTimeout(20000);

        //HtmlPage htmlPage = mWebClient.getPage();
        // 等待JS驱动dom完成获得还原后的网页
        mWebClient.waitForBackgroundJavaScript(50000);

    }

    /**
     * check whether input is a valid URL
     *
     * @author bob.li.0718@gmail.com
     * @param urlString urlString
     * @return true means yes, otherwise no.
     */
//    private boolean isUrl(String urlString) {
//        try {
//            new URL(urlString);
//            return true;
//        } catch (MalformedURLException mue) {
//            return false;
//        }
//    }

    /**
     * store webDrivers created
     */
    private List<WebClient> webClientList = Collections
            .synchronizedList(new ArrayList<WebClient>());

    /**
     * store webDrivers available
     */
    private BlockingDeque<WebClient> innerQueue = new LinkedBlockingDeque<WebClient>();

    public WebClientPool(int capacity) {
        this.capacity = capacity;
    }

    public WebClientPool() {
        this(DEFAULT_CAPACITY);
    }

    /**
     *
     * @return
     * @throws InterruptedException
     */
    public WebClient get() throws InterruptedException {
        checkRunning();
        WebClient poll = innerQueue.poll();
        if (poll != null) {
            return poll;
        }
        if (webClientList.size() < capacity) {
            synchronized (webClientList) {
                if (webClientList.size() < capacity) {

                    // add new WebDriver instance into pool
                    try {
                        configure();
                        innerQueue.add(mWebClient);
                        webClientList.add(mWebClient);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
        return innerQueue.take();
    }

    public void returnToPool(WebClient webClient) {
        checkRunning();
        innerQueue.add(webClient);
    }

    protected void checkRunning() {
        if (!stat.compareAndSet(STAT_RUNNING, STAT_RUNNING)) {
            throw new IllegalStateException("Already closed!");
        }
    }

    public void closeAll() {
        boolean b = stat.compareAndSet(STAT_RUNNING, STAT_CLODED);
        if (!b) {
            throw new IllegalStateException("Already closed!");
        }
        for (WebClient webClient : webClientList) {
            logger.info("Quit webClient" + webClient);
            webClient.close();
            webClient = null;
        }
    }
}
