package com.pantehon.webmagic.spider.downloader;

/**
 * Created by pantheon on 2017/3/10.
 */
public class HtmlUnitDownLoader/* implements Downloader, Closeable */{
//    private volatile WebDriverPool webDriverPool;
//    private Logger logger = LoggerFactory.getLogger(HtmlUnitDownLoader.class);
//    private int sleepTime = 0;
//    private int poolSize = 1;
//
//    public HtmlUnitDownLoader(String chromeDriverPath) {
//        System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);
//    }
//
//    public HtmlUnitDownLoader() {
//    }
//
//    public HtmlUnitDownLoader setSleepTime(int sleepTime) {
//        this.sleepTime = sleepTime;
//        return this;
//    }
//
//    public Page download(Request request, Task task) {
//        this.checkInit();
//
//        WebDriver webDriver;
//        try {
//            webDriver = this.webDriverPool.get();
//        } catch (InterruptedException var10) {
//            this.logger.warn("interrupted", var10);
//            return null;
//        }
//
//        this.logger.info("downloading page " + request.getUrl());
//        webDriver.get(request.getUrl());
//
//        try {
//            Thread.sleep((long)this.sleepTime);
//        } catch (InterruptedException var9) {
//            var9.printStackTrace();
//        }
//
//        Options manage = webDriver.manage();
//        Site site = task.getSite();
//        if(site.getCookies() != null) {
//            Iterator webElement = site.getCookies().entrySet().iterator();
//
//            while(webElement.hasNext()) {
//                Map.Entry content = (Map.Entry)webElement.next();
//                Cookie page = new Cookie((String)content.getKey(), (String)content.getValue());
//                manage.addCookie(page);
//            }
//        }
//
//        WebElement webElement1 = webDriver.findElement(By.xpath("/html"));
//        String content1 = webElement1.getAttribute("outerHTML");
//        Page page1 = new Page();
//        page1.setRawText(content1);
//        page1.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content1, request.getUrl())));
//        page1.setUrl(new PlainText(request.getUrl()));
//        page1.setRequest(request);
//        this.webDriverPool.returnToPool(webDriver);
//        return page1;
//    }
//
//    private void checkInit() {
//        if(this.webDriverPool == null) {
//            synchronized(this) {
//                this.webDriverPool = new WebDriverPool(this.poolSize);
//            }
//        }
//
//    }
//
//    public void setThread(int thread) {
//        this.poolSize = thread;
//    }
//
//    public void close() throws IOException {
//        this.webDriverPool.closeAll();
//    }
}
