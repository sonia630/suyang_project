package com.o2o.massage.web.client.controllers;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/tools")
@Controller
public class ToolsController {

    @Value("${upload.root}")
    private String uploadPath;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"upload"}, method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletResponse response) {

        String fileName = saveFile(file);
        if (fileName != null) {
            return fileName;
        } else {
            return getErrorRenderJson("upload file " + file.getOriginalFilename() + " failure.", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        }
    }

    @RequestMapping(value = {"uploadMutil"}, method = RequestMethod.POST)
    @ResponseBody
    public String uploadMutil(@RequestParam(value = "files") List<MultipartFile> list, HttpServletResponse response) {

        List<String> files = Lists.newArrayList();
        List<String> fails = Lists.newArrayList();
        list.forEach(file -> {
            String filename = saveFile(file);
            if (filename != null) {
                files.add(filename);
            } else {
                fails.add(file.getOriginalFilename());
            }
        });
        if (fails.size() != 0) {
            return getErrorRenderJson(JSON.toJSONString(fails), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        } else {
            return files.stream().collect(Collectors.joining(","));
        }
    }


    private String saveFile(MultipartFile file) {
        String originFileName = file.getOriginalFilename();
        int index = originFileName.lastIndexOf(".");
        if (index > 0) {
            originFileName = originFileName.substring(index);
        }
        String newFileName = UUID.randomUUID() + originFileName;
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
        return null;
    }

    @RequestMapping(value = {"download"}, method = RequestMethod.GET)
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

    public String getErrorRenderJson(String message, int statusCode, HttpServletResponse response) {
        response.setStatus(statusCode);
        return "{\"code\":\"" + "error" + "\",\"message\":\"" + message + "\"}";
    }
}
