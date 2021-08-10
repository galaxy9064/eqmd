package com.jcsa.eqmd.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩工具类
 */
public class ZipFileUtils {

    /**
     * 压缩文件
     * @param zipFileName 压缩文件保存路径
     * @param sourceFile 待压缩文件路径
     * @return
     */
    public static boolean zip(String zipFileName, String sourceFile){
        return zip(zipFileName, new File(sourceFile));
    }
    /**
     * 压缩文件
     * @param zipFileName 压缩文件保存路径
     * @param files 待压缩文件列表
     * @return
     */
    public static boolean zip(String zipFileName, File... files) {
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            createDir(zipFileName);
            out = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (int i = 0; i < files.length; i++) {
                if (null != files[i]) {
                    zip(out, files[i], files[i].getName());
                }
            }
            out.close(); // 输出流关闭
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 直接压缩下载
     * @param response
     * @param zipFileName
     * @param files
     */
    public static void downLoad(HttpServletResponse response, String zipFileName, File... files){
        try {
            // 清空response
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFileName,"UTF-8"));

            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

            for (int i = 0; i < files.length; i++) {
                if (null != files[i]) {
                    zip(out, files[i], files[i].getName());
                }
            }

            out.close(); // 输出流关闭
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 清空目录
     * @param path
     * @return
     */
    public static boolean cleanDir(String path){
        File file = new File(path);
        if(!file.exists()){//判断是否待删除目录是否存在
            System.err.println("The dir are not exists!");
            return false;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for(String name : content){
            File temp = new File(path, name);
            if(temp.isDirectory()){//判断是否是目录
                cleanDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            }else{
                if(!temp.delete()){//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }
    public static HttpServletResponse downloadZip(HttpServletResponse response, String sourceFile){
        return downloadZip(response, new File(sourceFile));
    }
    /**
     * 文件下载
     * @param response
     * @param file
     * @return
     */
    public static HttpServletResponse downloadZip(HttpServletResponse response, File file){
        try {
            // 清空response
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(),"UTF-8"));
            // 以流的形式下载文件
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int r = 0;
            while ((r = fis.read(buffer)) != -1) {
                toClient.write(buffer, 0, r);
            }

            fis.close();
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                File f = new File(file.getPath());
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     *
     * @param out 压缩文件输出流
     * @param file 当前压缩文件
     * @param base 文件路经
     */
    private static void zip(ZipOutputStream out, File file, String base) {
        try {
            if (file.isDirectory()) {//压缩目录
                try {
                    File[] fl = file.listFiles();
                    if (fl.length == 0) {
                        out.putNextEntry(new ZipEntry(base + "/"));  // 创建zip实体
                    }
                    for (int i = 0; i < fl.length; i++) {
                        zip(out, fl[i], base + "/" + fl[i].getName()); // 递归遍历子文件夹
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{ //压缩单个文件
                out.putNextEntry(new ZipEntry(base)); // 创建zip实体
                FileInputStream in = new FileInputStream(file);
                BufferedInputStream bi = new BufferedInputStream(in);
                int b;
                while ((b = bi.read()) != -1) {
                    out.write(b); // 将字节流写入当前zip目录
                }
                out.closeEntry(); //关闭zip实体
                in.close(); // 输入流关闭
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 目录不存在时，先创建目录
     * @param zipFileName
     */
    private static void createDir(String zipFileName){
        String filePath = zipFileName.substring(0, zipFileName.lastIndexOf("/"));
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {//目录不存在时，先创建目录
            targetFile.mkdirs();
        }
    }
}
