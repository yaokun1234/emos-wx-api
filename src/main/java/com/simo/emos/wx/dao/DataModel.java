package com.simo.emos.wx.dao;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description
 * @Author simo
 * @Date 2023/3/7 23:28
 * @Version 1.0
 **/
@Data
public class DataModel {


    @ExcelProperty("表英文名")
    private String tableEnglish;

    @ExcelProperty("表中文名")
    private String tableChinese;

    @ExcelProperty("字段英文名")
    private String fileEnglish;

    @ExcelProperty("字段中文名")
    private String fileChinese;

    @ExcelProperty("字段类型")
    private String fileType;
}
