package com.jcsa.eqmd.controller;

import com.jcsa.eqmd.bean.Data;
import com.jcsa.eqmd.constant.Constants;
import com.jcsa.eqmd.util.ZipFileUtils;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class DataTransferController {

    @Value("${file.path}")
    private String filePath;

    @Value("${seed.file.command}")
    private String seedCommand;

    @Value("${sac.file.command}")
    private String sacCommand;

    private final Map<String, String > commands = new HashMap();

    @RequestMapping("/main")
    public String main(){
        return "main";
    }
    @RequestMapping("/transfer")
    public String transfer(Data data, HttpServletResponse response){

        String dataType = data.getDataType();
        List<File> files = new ArrayList<>();

        try {
            //只选seed
            if(Constants.SEED.equals(data.getDataType())){
                files.add(generateSeed(data));
            //只选sac
            }else if(Constants.SAC.equals(dataType)){
                //seed文件不需要下载
                generateSeed(data);
                files.add(generateSac(data));
            //全选seed,sac
            }else{
                files.add(generateSeed(data));
                files.add(generateSac(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        if(files.size() > 0) {
            ZipFileUtils.downLoad(response, data.getStartTime().replaceAll("-| |:", "") + ".zip", files.toArray(new File[files.size()]));
        }
        return "success";
    }

    protected File generateSeed(Data data) throws Exception{
        String seedFilePath = filePath + File.separator + Constants.SEED;
        //1、清空seed目录
        ZipFileUtils.cleanDir(seedFilePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.print("data-----------"+data);
        LocalDateTime start = LocalDateTime.parse(data.getStartTime(), formatter);
        LocalDateTime end = LocalDateTime.parse(data.getEndTime(), formatter);
        Long days = ChronoUnit.DAYS.between(start, end);
        //天数大于0，按天执行
        if(days > 0){
        	System.out.println("day----------"+MessageFormat.format(seedCommand, Constants.BATCH, data.getStartTime(), days));
            Process exec = Runtime.getRuntime().exec(new String[] {"/bin/sh","-c",MessageFormat.format(seedCommand, Constants.BATCH, data.getStartTime(), days)});
            exec.waitFor();
            //Thread.sleep(15000);
        //天数等于0，按秒执行
        }else {
            Long seconds = ChronoUnit.SECONDS.between(start, end);
        	System.out.println("SECONDS----------"+MessageFormat.format(seedCommand, Constants.TS, data.getStartTime(), seconds));
        	Process exec = Runtime.getRuntime().exec(new String[] {"/bin/sh","-c",MessageFormat.format(seedCommand, Constants.TS, data.getStartTime(), seconds)});
        	exec.waitFor();
            System.out.println("执行完毕");
            //Thread.sleep(15000);
            
        }
        File file = new File(seedFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    protected File generateSac(Data data) throws Exception{
        String sacFilePath = filePath + File.separator + Constants.SAC;
        //1、清空sac目录
        ZipFileUtils.cleanDir(sacFilePath);
        //Runtime.getRuntime().exec(MessageFormat.format(sacCommand, data.getStartTime().replaceAll("-| |:", "")));
        System.out.println("sac------"+MessageFormat.format(sacCommand, data.getStartTime().replaceAll("-| |:", "")));
        Process exec = Runtime.getRuntime().exec(new String[] {"/bin/sh","-c",MessageFormat.format(sacCommand, data.getStartTime().replaceAll("-| |:", ""))});
        exec.waitFor();
        //Thread.sleep(15000);
        File file = new File(sacFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
