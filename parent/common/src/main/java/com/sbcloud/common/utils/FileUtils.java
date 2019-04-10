package com.sbcloud.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文件帮助类
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    

    public static void exportFile(File file, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String ua = request.getHeader("User-Agent");
        String fileName = file.getName();
        if (ua != null && ua.indexOf("MSIE") >= 0) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        response.addHeader("Content-Type", "application/vnd.ms-excel;");
        response.addHeader("Content-Disposition", "attachment;fileName=\"" + fileName + "\"");
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");    
        response.setContentType("application/octet-stream");
        osw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));    
        osw.write(new  String(buffer));
        osw.flush();
        osw.close();
        file.delete();
    }

}
