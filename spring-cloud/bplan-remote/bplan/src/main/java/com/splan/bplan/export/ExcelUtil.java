package com.splan.bplan.export;
import com.splan.bplan.annotation.ExcelAnnotation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 * excel工具类
 * @author wangkecheng
 * @param <T>
 */
public class ExcelUtil<T> {

    public static final int EXPORT_07_LEAST_SIZE = 50000;
    /**
     * 功能 :获取表单导出数据
     * 开发：wangkecheng
     * @param list        数据列表
     * @param title     首行标题
     * @param className 实体对象class
     * @param exportType         模板标号
     * @return
     * @throws Exception
     */
    public HSSFWorkbook exportExcel(List<T> list, String title, Class className,Integer exportType) throws Exception {
        // 获取属性
        Field[] fields = className.getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>();
        for (Field fie : fields) {
            if (fie.isAnnotationPresent(ExcelAnnotation.class)) {
                fieldList.add(fie);
            }
        }
        // 按照id进行排序
        Collections.sort(fieldList, new Comparator<Field>() {
            public int compare(Field f1, Field f2) {
                return f1.getAnnotation(ExcelAnnotation.class).id() - f2.getAnnotation(ExcelAnnotation.class).id();
            }
        });
        int columnsize = fieldList.size(), rowindex = 0;
        // 创建一个HSSFWorbook对象（excel的文档对象）
        HSSFWorkbook hWorkbook = new HSSFWorkbook();
        // 创建一个HSSFSheet对象（excll的表单）
        HSSFSheet hSheet = hWorkbook.createSheet();
        // 创建行（excel的行）
        HSSFRow hRow = hSheet.createRow(rowindex++);
        //设置行高度
        hRow.setHeight((short)380);
        // 创建单元格（从0开始）
        HSSFCell hCell = hRow.createCell((short) 0);
        //样式对象
        HSSFCellStyle cellStyle = getCellStyle(hWorkbook, (short) 300, (short) 500);
        // 将上面获得的样式对象给对应单元格
        hCell.setCellStyle(cellStyle);
        //设置标题行
        hCell.setCellValue(title);

        if (getHuoResult(fieldList.isEmpty(),list == null,list.isEmpty())) {
            return hWorkbook;
        }

        //创建第二行，代表列名
        hRow = hSheet.createRow(rowindex++);
        cellStyle = getCellStyle(hWorkbook, (short) 270, (short) 500);
        generateTitle(exportType, fieldList, columnsize, hSheet, hRow, cellStyle);

        //组装excel的数据
        cellStyle = getCellStyle(hWorkbook, (short) 220, (short) 500);// 设置单元格格式
        generateData(list, fieldList, columnsize, rowindex, hSheet, cellStyle);

        /**
         * 第1个参数：从哪一行开始
         * 第2个参数：到哪一行结束
         * 第3个参数：从哪一列开始
         * 第4个参数：到哪一列结束
         */
        hSheet.addMergedRegion(new CellRangeAddress(0,0,0,columnsize-1));

        // 固定表头（前一个参数代表列，后一个参数单表行）
        hSheet.createFreezePane(0, 1);
        return hWorkbook;
    }

    /**
     * 功能：組裝列明
     * @param exportType 模板编号
     * @param fieldList 列名
     * @param columnsize 列数
     * @param hSheet sheet页
     * @param hRow  行
     * @param cellStyle 样式
     */
    private void generateTitle(Integer exportType, List<Field> fieldList, int columnsize, HSSFSheet hSheet, HSSFRow hRow,
                               HSSFCellStyle cellStyle) {
        HSSFCell hCell;
        for (int i = 0; i < columnsize; i++) {
            Field field = fieldList.get(i);
            if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                // 获取该字段的注解对象
                ExcelAnnotation anno = field.getAnnotation(ExcelAnnotation.class);
                hCell = hRow.createCell((short) i);
                String colName = field.getAnnotation(ExcelAnnotation.class).name().length>exportType
                        ?field.getAnnotation(ExcelAnnotation.class).name()[exportType]
                        :field.getAnnotation(ExcelAnnotation.class).name()[0];
                hCell.setCellValue(colName);
                hCell.setCellStyle(cellStyle);
                hSheet.setColumnWidth((short) i, (short) anno.width());
            }
        }
    }

    /**
     * 组装excel的数据
     * @param list 具体数据
     * @param fieldList 列名
     * @param columnsize 列数
     * @param rowindex 行数计数
     * @param hSheet sheet页
     * @param cellStyle 样式
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private int generateData(List<T> list, List<Field> fieldList, int columnsize, int rowindex, HSSFSheet hSheet,
                             HSSFCellStyle cellStyle) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HSSFRow hRow;
        HSSFCell hCell;
        for (Object model : list) {
            hRow = hSheet.createRow(rowindex++);
            //获取该类
            Class clazz = model.getClass();
            for (int i = 0; i < columnsize; i++) {
                Field field =fieldList.get(i);
                //获取方法名
                String methodName = "get" + field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
                Method method = clazz.getMethod(methodName);
                try {
                    // 获取该字段的注解对象
                    Object result = method.invoke(model);
                    hCell = hRow.createCell((short) i);
                    if (result != null) {
                        if (result.getClass().isAssignableFrom(Date.class)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                            result = format.format(result);
                        }
                        hCell.setCellValue(new HSSFRichTextString(result.toString()));
                    } else {
                        hCell.setCellValue(new HSSFRichTextString("-"));
                    }
                    hCell.setCellStyle(cellStyle);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowindex;
    }
    /**
     * 生成07格式的excel对象 使用流方式防止内存溢出
     * @param list
     * @param title
     * @param className
     * @param exportType
     * @return
     * @throws Exception
     */
    public SXSSFWorkbook exportExcel07S(List<T> list, String title, Class className,Integer exportType) throws Exception {
        // 获取属性
        Field[] fields = className.getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>();
        for (Field fie : fields) {
            if (fie.isAnnotationPresent(ExcelAnnotation.class)){
                fieldList.add(fie);
            }
        }
        // 按照id进行排序
        Collections.sort(fieldList, new Comparator<Field>() {
            public int compare(Field f1, Field f2) {
                return f1.getAnnotation(ExcelAnnotation.class).id() - f2.getAnnotation(ExcelAnnotation.class).id();
            }
        });

        int columnsize = fieldList.size(), rowindex = 0;
        // 创建一个HSSFWorbook对象s
        SXSSFWorkbook hWorkbook = new SXSSFWorkbook();
        // 创建一个HSSFSheet对象（sheet页）
        Sheet hSheet =  hWorkbook.createSheet();
        // 创建第一行(此行作为头)
        Row hRow = hSheet.createRow(rowindex++);
        hRow.setHeight((short)380);
        // 创建单元格（第一（0）个）
        Cell hCell = hRow.createCell((short) 0);
        // 设置样式
        CellStyle cellStyle = getCellStyle07S(hWorkbook, (short) 300, (short) 500);
        // 将上面获得的样式对象给对应单元格
        hCell.setCellStyle(cellStyle);
        //设置标题行
        hCell.setCellValue(title);

        if (getHuoResult(fieldList.isEmpty(),list == null,list.isEmpty())) {
            return hWorkbook;
        }

        // 创建第二列，列名
        hRow = hSheet.createRow(rowindex++);
        cellStyle = getCellStyle07S(hWorkbook, (short) 270, (short) 500);
        createTitle07S(exportType, fieldList, columnsize, hSheet, hRow, cellStyle);

        //生成数据
        cellStyle = getCellStyle07S(hWorkbook, (short) 220, (short) 500);// 设置单元格格式
        dealCreateRow07S(list, fieldList, columnsize, rowindex, hSheet, cellStyle);

        /**
         * 第1个参数：从哪一行开始
         * 第2个参数：到哪一行结束
         * 第3个参数：从哪一列开始
         * 第4个参数：到哪一列结束
         */
        hSheet.addMergedRegion(new CellRangeAddress(0,0,0,columnsize-1));

        // 固定表头（前一个参数代表列，后一个参数单表行）
        hSheet.createFreezePane(0, 1);
        return hWorkbook;
    }

    private int dealCreateRow07S(List<T> list, List<Field> fieldList, int columnsize, int rowindex, Sheet hSheet,
                                 CellStyle cellStyle) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Row hRow;
        Cell hCell;
        for (Object model : list) {
            hRow = hSheet.createRow(rowindex++);
            // 获取该类 并获取自身方法
            Class clazz = model.getClass();
            for (int i = 0; i < columnsize; i++) {
                Field field = fieldList.get(i);
                String methodName = "get" + field.getName().substring(0, 1).toUpperCase()
                        + field.getName().substring(1);
                Method method = clazz.getMethod(methodName);
                try {
                    // 获取该字段的注解对象
                    Object result = method.invoke(model);
                    hCell = hRow.createCell((short) i);
                    if (result != null) {
                        if (result.getClass().isAssignableFrom(Date.class)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                            result = format.format(result);
                        }
                        hCell.setCellValue(new XSSFRichTextString(result.toString()));
                    } else {
                        hCell.setCellValue(new XSSFRichTextString("-"));
                    }
                    hCell.setCellStyle(cellStyle);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return rowindex;
    }

    /**
     * 生成列名
     * @param exportType 模板编号
     * @param fieldList 列名
     * @param columnsize 列数
     * @param hSheet
     * @param hRow
     * @param cellStyle
     */
    private void createTitle07S(Integer exportType, List<Field> fieldList, int columnsize, Sheet hSheet, Row hRow,
                                CellStyle cellStyle) {
        Cell hCell;
        for (int i = 0; i < columnsize; i++) {
            Field field = (Field) fieldList.get(i);
            if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                // 获取该字段的注解对象
                ExcelAnnotation anno = field.getAnnotation(ExcelAnnotation.class);
                hCell = hRow.createCell((short) i);
                String colName = field.getAnnotation(ExcelAnnotation.class).name().length>exportType
                        ?field.getAnnotation(ExcelAnnotation.class).name()[exportType]
                        :field.getAnnotation(ExcelAnnotation.class).name()[0];
                hCell.setCellValue(colName);
                hCell.setCellStyle(cellStyle);
                hSheet.setColumnWidth((short) i, (short) anno.width());
            }
        }
    }

    /**
     * 功能 :设置excel表格默认样式
     * @param hWorkbook     需导出Excel数据
     * @param fontHeight     字体粗度
     * @param boldWeight    表格线的粗度
     * @return
     */
    public HSSFCellStyle getCellStyle(HSSFWorkbook hWorkbook, short fontHeight, short boldWeight) {
        HSSFCellStyle cellStyle;
        HSSFFont font;
        cellStyle = hWorkbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        font = hWorkbook.createFont();
        font.setFontHeight(fontHeight);
        font.setBoldweight(boldWeight);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    /**
     * 功能 :设置excel 07表格默认样式
     * @param hWorkbook     需导出Excel数据
     * @param fontHeight     字体粗度
     * @param boldWeight    表格线的粗度
     * @return
     */
    public CellStyle getCellStyle07S(SXSSFWorkbook hWorkbook, short fontHeight, short boldWeight) {
        CellStyle cellStyle;
        Font font;
        cellStyle = hWorkbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        font = hWorkbook.createFont();
        font.setFontHeight(fontHeight);
        font.setBoldweight(boldWeight);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    /*
     * 获取或运算结果
     */
    private static boolean getHuoResult(Boolean... bs){
        for(boolean b:bs){
            if(b){
                return b;
            }
        }
        return false;
    }
}