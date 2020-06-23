package com.xxx.yyy.springbootguide.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片上传的几种方式
 *
 * @author: maoyan
 * @date: 2020/6/22 16:42:09
 * @description:
 */
@Log4j2
@Controller
@RequestMapping("/uploads")
public class FileUploadController {

    @Value("${file.path}")
    private String path = "upload/";

    @Value("${file.address}")
    private String address;

    @GetMapping
    public String index() {
        return "index";
    }


    @PostMapping("/upload1")
    @ResponseBody
    public Map<String, String> upload1(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());

        InputStream inputStream = file.getInputStream();
        // TODO 将文件写入到指定目录（具体开发中有可能是将文件写入到云存储/或者指定目录通过 Nginx 进行 gzip 压缩和反向代理，此处只是为了演示故将地址写成本地电脑指定目录）
        String fileName = file.getOriginalFilename();
        String fPath = new File(path).getAbsolutePath() + File.separator + fileName;
        File filePath = new File(fPath);

//        //判断文件是否已经存在
//        if (filePath.exists()) {
//            return "文件已经存在";
//        }

        // 判断父目录是否存在
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        /**
         * 方式一
         */
        file.transferTo(filePath);
        // 拼接上传文件路径
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + path + fileName)
                .toUriString();

        /**
         * 方式二 需打开配置文件中 ${spring.resources.static-locations} 选项
         */
//        // 文件后缀
//        String prefix = fileName.substring(fileName.lastIndexOf("."));
//        fileName = UUID.randomUUID() + prefix;
//        Files.copy(inputStream,new File(path + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
//        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/" + fileName)
//                .toUriString();

        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        result.put("url", fileUri + "");
        return result;
    }

    @PostMapping("/upload2")
    @ResponseBody
    public List<Map<String, String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            return null;
        }
        List<Map<String, String>> results = new ArrayList<>();
        for (MultipartFile file : files) {
            // TODO Spring Mvc 提供的写入方式
            String fileName = file.getOriginalFilename();
            String fPath = new File(path).getAbsolutePath() + File.separator + fileName;
            File filePath = new File(fPath);

            // 判断父目录是否存在
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }

            file.transferTo(filePath);
            // 拼接上传文件路径
            String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/" + path + fileName)
                    .toUriString();


            Map<String, String> map = new HashMap<>(16);
            map.put("contentType", file.getContentType());
            map.put("fileName", file.getOriginalFilename());
            map.put("fileSize", file.getSize() + "");
            map.put("url", fileUri + "");
            results.add(map);
        }
        return results;
    }

    @PostMapping("/upload3")
    @ResponseBody
    public void upload2(String base64) throws IOException {
        // TODO BASE64 方式的 格式和名字需要自己控制（如 png 图片编码后前缀就会是 data:image/png;base64,）
        String fPath = new File(path).getAbsolutePath() + File.separator + "test.jpg";

        final File tempFile = new File(fPath);
        // TODO 防止有的传了 data:image/png;base64, 有的没传的情况
        String[] d = base64.split("base64,");
        final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
        FileCopyUtils.copy(bytes, tempFile);
    }
}
