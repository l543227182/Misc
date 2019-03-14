package com.lc.misc.usualTest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestExcel {

    public static void main(String[] args) throws IOException {
        System.out.println(transferExcel(new FileInputStream(new File("C:\\Users\\luochao.byron\\Desktop\\组导入.xlsx")),""));
    }

    public static List<Map<String,String>> transferExcel (InputStream inputStream, String fileName) throws IOException {
        ArrayList<Map<String,String>> result = Lists.newArrayList();
        // HSSFWorkbook 标识整个excel
        Workbook hssfWorkbook = WorkbookFactory.create(inputStream);
        // HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
        int size = hssfWorkbook.getNumberOfSheets();
        // 循环每一页，并处理当前循环页
        for (int numSheet = 0; numSheet < size; numSheet++) {
            // HSSFSheet 标识某一页
            Sheet sheetAt = hssfWorkbook.getSheetAt(numSheet);
            if (sheetAt == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            for (int rowNum = 0; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
                // HSSFRow表示行
                Row row = sheetAt.getRow(rowNum);
                int maxColIx = row.getLastCellNum();
                if (maxColIx > 2) {
                    throw new RuntimeException("Excel格式不对");
                }
                Map<String, String> rowData = Maps.newHashMap();
                // 遍历改行，获取处理每个cell元素
                String groupName = row.getCell(0).toString();
                String userName = row.getCell(1).toString();
                rowData.put("groupName", groupName);
                rowData.put("userName", userName);
                StringBuffer sb = new StringBuffer();
                rowData.put("remark",sb.toString());
                result.add(rowData);
            }
        }
        return result;
    }
}
