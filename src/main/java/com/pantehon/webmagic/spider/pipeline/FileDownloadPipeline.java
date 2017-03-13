package com.pantehon.webmagic.spider.pipeline;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by pantheon on 2017/3/13.
 */
public class FileDownloadPipeline extends FilePersistentBase implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public FileDownloadPipeline(String path){
        this.setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //确保文件夹存在
        String path = this.path + PATH_SEPERATOR;
        this.checkAndMakeParentDirecotry(path + PATH_SEPERATOR);

        logger.info("url:\t" + resultItems.getRequest().getUrl());
        Iterator iterator = resultItems.getAll().entrySet().iterator();

        StopWatch stopWatch = new StopWatch("文件下载耗时");
        stopWatch.start();

        while(true) {
            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                if(entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable)entry.getValue();

                    Iterator var8 = value.iterator();

                    while(var8.hasNext()) {
                        Object o = var8.next();
                        this.downloadFile(o.toString());
                    }
                } else {
                    logger.info((String)entry.getKey() + ":\t" + entry.getValue());
                }
            }
            break;
        }
        stopWatch.stop();

        logger.info(stopWatch.prettyPrint());

    }

    /**
     * 根据URL下载文件
     * @param urlString
     */
    private void downloadFile(String urlString){
        int index = urlString.lastIndexOf(".");
        String filePath = urlString.substring(index,urlString.length());

        //获取文件名称,格式： 时间戳.jpg
        filePath = this.getPath() + System.currentTimeMillis() + filePath;

        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(filePath);
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            IOUtils.copy(conn.getInputStream(),outputStream);

        } catch (MalformedURLException e) {
            logger.error("FileDownloadPipeline-downloadFile-MalformedURLException-error:",e);
        }catch (FileNotFoundException e) {
            logger.error("FileDownloadPipeline-downloadFile-FileNotFoundException-error:",e);
        } catch (IOException e) {
            logger.error("FileDownloadPipeline-downloadFile-IOException-error:",e);
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error("FileDownloadPipeline-downloadFile-IOException-error:",e);
            }
        }
    }
}
