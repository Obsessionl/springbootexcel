package com.example.demo.service.imp;

import com.example.demo.mapper.UserExcelMapper;
import com.example.demo.pojo.UserExcelModel;
import com.example.demo.service.UserExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 21072
 */
@Service
public class UserExcelServiceImpi implements UserExcelService {
    @Autowired
    private UserExcelMapper userExcelMapper;
    @Override
    public List<UserExcelModel> select() {
        return userExcelMapper.quary();
    }
}
