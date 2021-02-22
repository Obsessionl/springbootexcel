package com.example.demo.controller;

import com.example.demo.pojo.UserExcelModel;
import com.example.demo.service.UserExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 21072
 */
@RestController
@RequestMapping("/user")
public class ExcelController {
    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);
    @Autowired
    private UserExcelService userExcelService;
    /**
     * @Description 下载模板
     * @param response
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) {
        try {
            ClassPathResource pathResource = new ClassPathResource("excel/user.xlsx");
            InputStream inputStream = pathResource.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("user.xlsx", "utf-8"));
            response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            log.debug("下载成功");
        } catch (Exception e) {
            log.debug("下载失败");
            e.printStackTrace();
        }
    }

    /**
     * @Description 导出数据
     * @param response
     */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            String []columnNames = {"用户名", "年龄", "手机号", "性别"};

            Sheet sheet = xssfWorkbook.createSheet();
            Font titleFont = xssfWorkbook.createFont();
            titleFont.setFontName("用户表");
            titleFont.setBold(true);
            titleFont.setColor(IndexedColors.BLACK.index);

            XSSFCellStyle titleStyle = xssfWorkbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setFillForegroundColor(IndexedColors.WHITE.index);
             titleStyle.setFont(titleFont);

            Row row = sheet.createRow(0);
            for (int i = 0; i < columnNames.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(columnNames[i]);
                cell.setCellStyle(titleStyle);
            }

            List<UserExcelModel> arrayList = userExcelService.select();

            for (int i = 0; i < arrayList.size(); i++) {
                UserExcelModel userExcelModel = arrayList.get(i);
                int lastRowNum = sheet.getLastRowNum();
                Row dataRow = sheet.createRow(lastRowNum + 1);
                dataRow.createCell(0).setCellValue(userExcelModel.getName());
                dataRow.createCell(1).setCellValue(userExcelModel.getAge());
                dataRow.createCell(2).setCellValue(userExcelModel.getPhone());
                dataRow.createCell(3).setCellValue(userExcelModel.getSex());
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("user.xlsx", "utf-8"));
            response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
            OutputStream outputStream = response.getOutputStream();
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            log.info("下载成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("导出数据失败");
        }
    }

    @GetMapping("/quary")
    public List<UserExcelModel> select() {
        try {
            List<UserExcelModel> list  = userExcelService.select();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("查询出错");
            return null;
        }
    }
}
