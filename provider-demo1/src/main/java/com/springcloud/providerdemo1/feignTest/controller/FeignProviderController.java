package com.springcloud.providerdemo1.feignTest.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.global.entity.DTO.StudentDTO;
import com.springcloud.global.entity.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.util.DocumentHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: springcloud-example
 * @description:
 * @author:
 * @create: 2018-06-15 16:12
 **/
@RestController
//@Profile({"dev","test"})
@Slf4j
public class FeignProviderController {

    @Value("${img.resource-path}")
    private String IMG_PATH;


    @GetMapping("/getUser")
    public String getUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("线程[{}]获取的自定义请求头为[{}]",Thread.currentThread().getName(),request.getHeader("TestHeader"));
        System.out.println("获取用户成功");
        return "{\"username\":\"张三\",\"age\":\"10\"}";
    }

    @PostMapping("/setUser")
    public String getUser(@RequestBody @Valid StudentDTO studentDTO) throws InterruptedException {
        System.out.println("保存用户成功");
        return JSON.toJSONString(studentDTO);
    }

    private final static String fileName = "C:\\Users\\Administrator\\Desktop\\服务号开发相关\\图片样例\\1440,蓝.png";

    @RequestMapping(value="/getFile",method = {RequestMethod.POST})
    public MultipartFile getFile(){
        //获取文件，转换为MuktipartFile形式
        File sourceFile = new File(fileName);
        //确认文件是否存在
        if(sourceFile.exists()){
            log.info("文件绝对路径为：[{}]",sourceFile.getAbsolutePath());
        }
        //文件转化
        MultipartFile targetFile = null;
        try {
            targetFile = new MockMultipartFile("file",sourceFile.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(sourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回文件
        return targetFile;
    }

    @RequestMapping(value = "/uploadFile")
    public ResultModel<List<String>> uploadFiles(@RequestParam("id") String id,@RequestParam("token") String token,@RequestPart(value="file") List<MultipartFile> files){
        log.info("上传者id为[{}]",JSON.toJSONString(id));
        log.info("上传者token为[{}]",JSON.toJSONString(token));
        //保存文件
        List<File> targetFiles =  saveMultiPartFiles(files);
        //返回文件名数组
        ResultModel<List<String>> resultModel = new ResultModel<>();
        List<String> fileNames = new ArrayList<>();
        for (File file : targetFiles) {
            log.info("文件名为[{}]",file.getName());
            fileNames.add(file.getName());
        }
        resultModel.setData(fileNames);
        return resultModel;
    }


    private List<File> saveMultiPartFiles(List<MultipartFile> multipartFile){
        List<File> files = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            files.add(multipartFile2File(file,true));
        }
        return files;
    }

    private File multipartFile2File(MultipartFile multipartFile,Boolean... useNewName){
        //生成文件路径
        File path = new File(IMG_PATH);

        //如果路径不存在则生成路径
        if(!path.exists()){
            path.mkdirs();
        }

        //生成随机文件名
        String fileName = multipartFile.getOriginalFilename();
        if(useNewName.length > 0 && useNewName[0]){
            String fileSuffix = fileName.substring(fileName.indexOf("."));
            try {
                fileName = getFileMD5(multipartFile.getBytes()) + fileSuffix;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //创建文件
        File file = new File(IMG_PATH,fileName);

        //保存字节流
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    //获取文件的MD5值
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[4096];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Hex.encodeHex(digest.digest()));
    }

    //获取byte[]的MD5值
    public static String getFileMD5(byte[] bytes) {
        if (bytes==null) {
            return null;
        }
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return new String(Hex.encodeHex(digest.digest()));
    }

    public static void main(String[] args) throws IOException {
//        File file = new File("D:\\images\\f130bc4685da46eba11fad47af9b15a5.png");
//        MultipartFile multipartFile = new MockMultipartFile("file",file.getName(),MediaType.MULTIPART_FORM_DATA_VALUE,new BufferedInputStream(new FileInputStream(file)));
//        log.info("md5计算结果一[{}]",getFileMD5(file));
//        log.info("md5计算结果二[{}]",getFileMD5(multipartFile.getBytes()));

        String str = "<p style=\"text-indent:2em;\" class=\"p\"></p><div class=\"media-wrap image-wrap\"><img class=\"media-wrap image-wrap\" src=\"/PictureServer/images/b67e210cdb4348d5b77499f9d910128e.png?access_token=289b0634-16a8-400d-890b-c31859c86373\"/></div><p></p><p><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">      梦网科技成立于2001年，十八年来一直专注于移动通信服务。于2015年成功登陆资本市场（股票代码：002123），目前主营中国最大规模之一的企业云通信平台，是中国领先的云通信服务商。梦网构建了以“IM云、视频云、物联云”三位一体的企业云通信服务生态，为企业提供全方位的云通信服务。</span></span></p><p style=\"text-indent:2em;\" class=\"p\"><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">     公司总部坐落于深圳市，在北京、上海、广州、香港等全国20多个大中城市设有区域中心，现有员工近1500人，其中研发人员600多人。办公场地约15000平方米，连续多年保持50%以上的业绩增长，先后荣获国家高新技术企业、中国互联网百强企业等数十项重量级荣誉称号及资质。</span></span></p><p style=\"text-indent:2em;\" class=\"p\"><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">     梦网携手中国移动、中国电信、中国联通及数千家大型知名企业，在云通信领域开展了深入的研发、建设、维护和服务，软硬件产品广泛应用于金融、互联网、商超等领域，为数十万家大中型企业与超十亿用户提供沟通便利，每年实现互动千亿次。</span></span></p><p style=\"text-indent:2em;\" class=\"p\"><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">     当前，梦网主营云通信业务；未来，将进一步扩展云计算、云商务版块。</span></span></p>\n<p style=\"text-indent:2em;\" class=\"p\"></p><div class=\"media-wrap image-wrap\"><img class=\"media-wrap image-wrap\" src=\"/PictureServer/images/b67e210cdb4348d5b77499f9d910128e.png?access_token=289b0634-16a8-400d-890b-c31859c86373\"/></div><p></p><p><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">      梦网科技成立于2001年，十八年来一直专注于移动通信服务。于2015年成功登陆资本市场（股票代码：002123），目前主营中国最大规模之一的企业云通信平台，是中国领先的云通信服务商。梦网构建了以“IM云、视频云、物联云”三位一体的企业云通信服务生态，为企业提供全方位的云通信服务。</span></span></p><p style=\"text-indent:2em;\" class=\"p\"><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">     公司总部坐落于深圳市，在北京、上海、广州、香港等全国20多个大中城市设有区域中心，现有员工近1500人，其中研发人员600多人。办公场地约15000平方米，连续多年保持50%以上的业绩增长，先后荣获国家高新技术企业、中国互联网百强企业等数十项重量级荣誉称号及资质。</span></span></p><p style=\"text-indent:2em;\" class=\"p\"><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">     梦网携手中国移动、中国电信、中国联通及数千家大型知名企业，在云通信领域开展了深入的研发、建设、维护和服务，软硬件产品广泛应用于金融、互联网、商超等领域，为数十万家大中型企业与超十亿用户提供沟通便利，每年实现互动千亿次。</span></span></p><p style=\"text-indent:2em;\" class=\"p\"><span style=\"color:#666666\"><span style=\"background-color:#ffffff\">     当前，梦网主营云通信业务；未来，将进一步扩展云计算、云商务版块。</span></span></p>\n";
        String regex = "/PictureServer/images/.*?\\?access_token=";
        System.out.println(addPreAndSuf(str,regex,"aaa","bbb"));
    }

    public static String addPreAndSuf(String sourceStr,String regex,String prefix,String suffix){
        // 生成 Pattern 对象并且编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 用 Pattern 类的 matcher() 方法生成一个 Matcher 对象
        Matcher m = pattern.matcher(sourceStr);
        StringBuffer sb = new StringBuffer(sourceStr);
        while(m.find()){
            //此时sb为fatdogfatdog，cat被替换为dog,并且将最后匹配到之前的子串都添加到sb对象中
            m.appendReplacement(sb,prefix+m.group()+suffix);
        }
        //此时sb为fatdogfatdogfat，将最后匹配到后面的子串添加到sb对象中
        m.appendTail(sb);
        //输出内容为fatdogfatdogfat
        return sb.substring(sourceStr.length());
    }
}
