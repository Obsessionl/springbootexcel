package com.example.demo.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 21072
 */
@Data
@AllArgsConstructor
public class UserExcelModel extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "用户名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private String age;

    @ExcelProperty(value = "手机号", index = 2)
    private String phone;

    @ExcelProperty(value = "性别", index = 3)
    private String sex;

}
