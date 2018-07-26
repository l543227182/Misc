package main.cn.lc.usualTest;

import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by luochao.byron on 2017/12/19.
 */
public class sortTest {
    public static void main(String args[]){
        try (ZipFile zf = new ZipFile(new File("C:\\Users\\luochao.byron\\Desktop\\rc\\test.zip"))) {
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while(entries.hasMoreElements()){
                ZipEntry zipEntry = entries.nextElement();
                System.out.println(zipEntry.getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
