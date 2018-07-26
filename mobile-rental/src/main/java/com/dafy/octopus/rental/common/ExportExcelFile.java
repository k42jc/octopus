package com.dafy.octopus.rental.common;

import com.dafy.common.exception.ServerException;

import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hedy02 on 2017/10/24.
 */
public class ExportExcelFile {
    /**
     * 生成excel文件
     * @param data
     * @param fileName   文件名
     * @throws IOException
     * @throws WriteException
     */
    public static void exportEntry(ArrayList<ArrayList<ExportEntry>> data, String fileName) throws IOException, WriteException {
        //创建xls文件
        WritableWorkbook wwb = Workbook.createWorkbook( getXlsFile(fileName+".xls"));
        WritableSheet ws = wwb.createSheet(fileName,0);

        if (data == null || data.isEmpty()) throw new ServerException("导出数据为空","导出数据时，全部数据为空");

        ArrayList<ExportEntry> index = data.get(0);

        if (index==null || index.isEmpty())throw new ServerException("导出数据为空","导出数据时，列的数据为空");

        int columnNum = index.size();

        for (int j = 0; j < index.size(); j++){
            ws.addCell(new Label(j,0,index.get(j).getKey()));
            ws.addCell(new Label(j,1,index.get(j).getValue()));
        }

        for (int i = 1; i < data.size(); i++){

            ArrayList<ExportEntry> row = data.get(i);
            if (row.size() != columnNum) throw new ServerException("数据有误","导出数据时，数据的列数不匹配");

            for (int j = 0; j < columnNum; j++){
                ws.addCell(new Label(j,i+1,row.get(j).getValue()));
            }
        }
        //写入文件并，关闭
        wwb.write();
        wwb.close();
    }

    private static File getXlsFile(String fileName) throws IOException {
        File result = new File(ApplicationContextProvider.getBean(Environment.class).getProperty("config.xls.path") + fileName);
        result.deleteOnExit();
        result.createNewFile();
        return result;
    }
}
