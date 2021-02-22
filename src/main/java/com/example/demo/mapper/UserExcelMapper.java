package com.example.demo.mapper;

import com.example.demo.pojo.UserExcelModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 21072
 */
@Mapper
public interface UserExcelMapper {
    List<UserExcelModel> quary();
}
