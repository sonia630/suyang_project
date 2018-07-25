package com.o2o.nm.sys.controller;

import com.o2o.nm.sys.util.ResConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.UUID;

@ResConfig(name = "工具控制", id = "tools")
@RequestMapping("/sys/tools")
@Controller
public class ToolsController extends BaseController {

    @Value("${upload.root}")
    private String uploadPath;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"upload"}, method = RequestMethod.POST)
    @ResConfig(name = "上传", id = "tools1")
    @ResponseBody
    public String update(@RequestParam("file") CommonsMultipartFile file, HttpServletResponse response) {
        String originFileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + originFileName.substring(originFileName.lastIndexOf("."));
        File path = new File(uploadPath);
        File target = null;
        if (path.isAbsolute()) {
            if (!path.exists()) {
                path.mkdirs();
            }
            target = new File(path, newFileName);
        } else {
            URL url = Thread.currentThread().getContextClassLoader().getResource("/");
            if (url != null) {
                target = new File(new File(url.getFile()).getParentFile().getParent() + File.separator + uploadPath, newFileName);
            }
        }

        try {
            file.transferTo(target);
            return newFileName;
        } catch (IllegalStateException | IOException e) {
            logger.error("{}", e);
        }
        return getErrorRenderJson("upload file " + file.getOriginalFilename() + " failure.", HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                , response);
    }

    @RequestMapping(value = {"download"}, method = RequestMethod.GET)
    @ResConfig(name = "下载", id = "tools2")
    public void download(String name, HttpServletResponse response) {
        File path = new File(uploadPath, name);
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            FileChannel fileChannel = fileInputStream.getChannel();
            OutputStream out = response.getOutputStream();
            WritableByteChannel outChannel = Channels.newChannel(out);
            fileChannel.transferTo(0, fileChannel.size(), outChannel);
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }
}
