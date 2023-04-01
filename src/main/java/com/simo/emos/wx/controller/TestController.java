package com.simo.emos.wx.controller;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.simo.emos.wx.dao.DataModel;
import com.simo.emos.wx.dao.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author simo
 * @Date 2022/12/29 01:06
 * @Version 1.0
 **/

@RestController
@Api("测试")
public class TestController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/test")
    @ApiOperation("测试")
    public String tset(){


//        System.out.println("1");
//        System.out.println("2");
//        System.out.println("3");
//        User user = userRepository.findById("4028818b86895f4f01868964de170000").get();
//        user.setCreateTime(new Date());
//        userRepository.save(user);
//        System.out.println("4");
//        System.out.println("5");
//        System.out.println("6");
//        System.out.println("7");

        ArrayList<List> dataModelList = new ArrayList<>();
        Set<Class<?>> classes = ClassUtil.scanPackage("com.simo.emos.wx.dao.entity");
        classes.forEach(classe ->{
            ArrayList<DataModel> list = new ArrayList<>();
            Field[] fields = ReflectUtil.getFields(classe);
            for (Field field : fields) {
                String fieldName = field.getName();
                ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                String annotationValue = "";
                if(annotation != null){
                    annotationValue = annotation.value();
                }
                DataModel dataModel = new DataModel();
                dataModel.setFileEnglish(fieldName);
                dataModel.setFileChinese(annotationValue);
                dataModel.setFileType(field.getType().getTypeName());
                list.add(dataModel);
            }
            Table annotationTable = classe.getAnnotation(Table.class);
            String tableEnglish = "";
            if (annotationTable != null){
                tableEnglish = annotationTable.name();
            }
            ApiModel annotationApiModel = classe.getAnnotation(ApiModel.class);
            String tableChinese = "";
            if(annotationApiModel!= null){
                tableChinese = annotationApiModel.value();

            }
            String finalTableChinese = tableChinese;
            String finalTableEnglish = tableEnglish;
            List<DataModel> dataModels = list.stream().map(dataModel -> {
                dataModel.setTableChinese(finalTableChinese);
                dataModel.setTableEnglish(finalTableEnglish);
                return dataModel;
            }).collect(Collectors.toList());
            dataModelList.add(dataModels);
        });

        try (ExcelWriter excelWriter = EasyExcel.write("/Users/simo/work/yili/a.xlsx", DataModel.class).build()){
            for (int i = 0; i < dataModelList.size(); i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "dataModel" + i).build();
                excelWriter.write(dataModelList.get(i),writeSheet);
            }
        }
        System.out.println(dataModelList.size());
        return " ";
    }







}
