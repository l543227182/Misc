package main.cn.lc.usualTest;

import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by luochao.byron on 2017/7/7.
 */
public class MainClass {
    public static void main(String[] args){
        //unzipDirWithPassword("D:\\download\\demo.zip","D:\\download\\demo","123asd");

        StringBuffer sb= new StringBuffer(12);
        String task_id = "123321";
        String sql = String.format("select * from t_notifytasks where task_id like% %s$1 %",task_id);
        System.out.println(sql);

        Map<String ,String > targetMap = new HashMap<String,String>();

    }

    public static void unzipDirWithPassword( final String sourceZipFile ,
                                      final String destinationDir , final String password ){
        RandomAccessFile randomAccessFile = null;
        ISevenZipInArchive inArchive = null;
        try{
            randomAccessFile = new RandomAccessFile(sourceZipFile, "r");
            inArchive = SevenZip.openInArchive(null, // autodetect archive type
                    new RandomAccessFileInStream(randomAccessFile));

            // Getting simple interface of the archive inArchive
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

            for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()){
                final int[] hash = new int[] { 0 };
                if (!item.isFolder()){
                    ExtractOperationResult result;
                    result = item.extractSlow(new ISequentialOutStream(){
                        public int write(final byte[] data) throws SevenZipException {
                            try{
                                if(item.getPath().indexOf(File.separator)>0){
                                    String path = destinationDir+File.separator+item.getPath(). substring(0,item.getPath().lastIndexOf(File.separator));
                                    File folderExisting = new File(path);
                                    if (!folderExisting.exists())
                                        new File(path).mkdirs();
                                }
                                if(!new File(destinationDir + File.separator+item.getPath()).exists()){
                                    new File(destinationDir).createNewFile();
                                }
                                OutputStream out = new FileOutputStream(destinationDir+ File.separator+item.getPath());
                                out.write(data);
                                out.close();
                            }catch( Exception e ){
                                e.printStackTrace();
                            }
                            hash[0] |= Arrays.hashCode(data);
                            return data.length; // Return amount of proceed data
                        }
                    },password); /// password.
                    if (result == ExtractOperationResult.OK){
                        System.out.println(String.format("%9X | %s",
                                hash[0], item.getPath()));
                    }else{
                        System.err.println("Error extracting item: " + result);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (inArchive != null){
                try {
                    inArchive.close();
                } catch (SevenZipException e){
                    System.err.println("Error closing archive: " + e);
                    e.printStackTrace();
                }
            }
            if (randomAccessFile != null) {
                try{
                    randomAccessFile.close();
                } catch (IOException e){
                    System.err.println("Error closing file: " + e);
                    e.printStackTrace();
                }
            }
        }
    }
}
