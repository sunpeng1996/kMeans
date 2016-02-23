package cn.hit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/*******************************************************************************
 * Excel文件读取类，读取Excel表格记录为JAVA对像
 * 
 * 
 * @param <E>
 *            读取后转换的目标对像类型
 */
public class ExcelReader<E> {
	/***************************************************************************
	 * 实体对像
	 */
	private E entity;
	private POIFSFileSystem fs;
	private HSSFRow row;
	private HSSFSheet sheet;
	private Excel2EntityConfig excel2EntityConfig;
	/***************************************************************************
	 * 创建文件输入流
	 */
	private BufferedReader reader = null;
	/***************************************************************************
	 * 文件类型
	 */
	private String fileType ="xls";
	/***************************************************************************
	 * 文件二进制输入流
	 */
	private InputStream is = null;
	/***************************************************************************
	 * 当前工作表 sheet
	 */
	private int currSheet;
	/***************************************************************************
	 * 当前位置
	 */
	private int currPosittion;
	/***************************************************************************
	 * 工作表sheet的数量 *
	 */
	private int numOfSheets;
	/***************************************************************************
	 * HSSFWordbook
	 */
	private HSSFWorkbook workbook = null;

	/***************************************************************************
	 * 设置Cell之间用空格分开
	 */
	private static String EXCEL_LINE_DELIMITER = " ";
	/***************************************************************************
	 * 设置最大列数
	 */
	private int MAX_EXCEL_COLUMNS = 64;
	
	/***************************************************************************
	 * 由文件输入流创建初始化一个ExcelReader
	 * 
	 * @param inputfile
	 *            文件输入流
	 * @throws IOException
	 * @throws Exception
	 */
	public void InitExcelReader(InputStream inputfile) throws IOException,
			Exception {
		if (inputfile == null) {
			throw new IOException("文件输入流为空");
		}
		/***********************************************************************
		 * 设置开始行
		 */
		this.currPosittion = this.excel2EntityConfig.getCurrPosittion();
		/***********************************************************************
		 * 设置当前位置为0
		 */
		this.currSheet = 0;
		/***********************************************************************
		 * 创建文件输入流 *
		 */
		this.is = inputfile;
		/***********************************************************************
		 * 判断文件格式
		 */
		if (this.fileType.equalsIgnoreCase("xls")) {
			/*******************************************************************
			 * 如果是Excel文件则创建BufferedReader读取
			 */
			this.workbook = new HSSFWorkbook(this.is);
			/*******************************************************************
			 * 设置工作表Sheet数
			 */
			this.numOfSheets = this.workbook.getNumberOfSheets();
		} else {
			throw new Exception("文件格式不正确!");
		}
	}

	/***************************************************************************
	 * 读到文件的一行
	 * 
	 * @return
	 * @throws IOException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ParseException
	 */
	public E readLine() throws IOException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, ParseException {
		/***********************************************************************
		 * 如果是Excel文件则通过POI提供的API读取文件 *
		 */
		if (fileType.equalsIgnoreCase("xls")) {
			/*******************************************************************
			 * 根据currSheet值获得当前的工作表Sheet
			 */
			HSSFSheet sheet = this.workbook.getSheetAt(this.currSheet);
			/*******************************************************************
			 * 判断当前行是否到当前工作表sheet的结尾
			 */
			if (currPosittion - 1 > sheet.getLastRowNum()) {
				/***************************************************************
				 * 当前行位置清0
				 */
				this.currPosittion = 1;
				/***************************************************************
				 * 判断是否还有工作表sheet
				 */
				while (currSheet != this.numOfSheets - 1) {
					/***********************************************************
					 * 得到下一张工作表sheet
					 */
					sheet = this.workbook.getSheetAt(currSheet + 1);
					/***********************************************************
					 * 当前行数是否已到达文件末尾
					 */
					if (this.currPosittion - 1 == sheet.getLastRowNum()) {
						/*******************************************************
						 * 不前工作表sheet指向一下张sheet
						 */
						currSheet++;
						continue;
					} else {
						/**
						 * **8 获到当前行数
						 */
						int row = currPosittion;
						currPosittion++;
						return getLine(sheet, row);
					}
				}
				return null;
			}
			int row = currPosittion;
			currPosittion++;
			return getLine(sheet, row);
		}
		return null;
	}

	/***************************************************************************
	 * 返回工作表sheet的一行数据
	 * 
	 * @param sheet
	 *            工作表
	 * @param row
	 *            行
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	private E getLine(HSSFSheet sheet, int row) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, ParseException {
		E entity = (E) this.entity;
		/***********************************************************************
		 * 根据行数取得sheet的一行
		 */
		HSSFRow rowline = sheet.getRow(row);
		if (rowline != null) {
			/*******************************************************************
			 * 创建字符串缓冲区
			 */
			StringBuffer buffer = new StringBuffer();
			/*******************************************************************
			 * 获到当前行的列数
			 */
			int filledColumns = rowline.getLastCellNum();
			HSSFCell cell = null;
			/***
			 * 开始读取的列，从第几列开始读。
			 */
			int colStart = this.getExcel2EntityConfig().getColStartPosittion();
			/*******************************************************************
			 * 遍历所有列
			 */			
			for (int i = colStart; i < filledColumns; i++) {
				/***************************************************************
				 * 取得当前单元格
				 */
				cell = rowline.getCell(i);
				String cellvalue = null;
				Date celldatevalue = null;
				if (cell != null) {
					/***********************************************************
					 * 判断当前单元格的type
					 */
					switch (cell.getCellType()) {
					// 如果当前Cell的type为NUMERIC
					case HSSFCell.CELL_TYPE_NUMERIC: {
						// 判断当前的Cell是否为Date
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							// 如果是在Date类型，则取得该Cell的Date值
							Date date = cell.getDateCellValue();
							cellvalue = this.excel2EntityConfig.getFormater()
									.format(date);
							// 把Date转换成本地格式的的字符串
							// celldatevalue = cell.getDateCellValue();
						} else {
							// 如果是纯数字
							// 取得当前cell的数值
							Integer num = new Integer((int) cell
									.getNumericCellValue());
							cellvalue = String.valueOf(num);
						}
						break;
					}
					case HSSFCell.CELL_TYPE_STRING:
						// /取得当前Cell的字符串
						cellvalue = cell.getStringCellValue()
								.replace("'", "''");
						break;

					default:
						cellvalue = " ";
					}
				} else {
					cellvalue = "";
				}
				String column = this.excel2EntityConfig.getColumns()[i-colStart];

				Field[] field = entity.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
				for (int j = 0; j < field.length; j++) { // 遍历所有属性
					String name = field[j].getName(); // 获取属性的名字
					name = this.A2UpperCase(name);
					String type = field[j].getGenericType().toString(); // 获取属性的类型
					if (this.A2UpperCase(column).trim().equals(name)
							&& cellvalue != null
							&& cellvalue.trim().equals("") == false) {
						if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class//
							// "，后面跟类名
							Method sm = entity.getClass().getDeclaredMethod(
									"set" + name, String.class);
							sm.invoke(entity, cellvalue);
						}
						if (type.equals("class java.lang.Integer")) {
							Method sm = entity.getClass().getDeclaredMethod(
									"set" + name, Integer.class);
							sm.invoke(entity, Integer.parseInt(cellvalue));
						}
						if (type.equals("class java.lang.Short")) {
							Method sm = entity.getClass().getDeclaredMethod(
									"set" + name, Short.class);
							sm.invoke(entity, Short.parseShort(cellvalue));
						}
						if (type.equals("class java.lang.Double")) {
							Method sm = entity.getClass().getDeclaredMethod(
									"set" + name, Double.class);
							sm.invoke(entity, Double.parseDouble(cellvalue));
						}
						if (type.equals("class java.lang.Boolean")) {
							Method sm = entity.getClass().getDeclaredMethod(
									"set" + name, Boolean.class);
							sm.invoke(entity, Boolean.parseBoolean(cellvalue));
						}
						if (type.equals("class java.util.Date")) {
							Method sm = entity.getClass().getDeclaredMethod(
									"set" + name, Date.class);
							sm.invoke(entity, this.excel2EntityConfig
									.getFormater().parse(cellvalue));
						}
					}

				}

				// 在每个字段之间插入分割符
				// buffer.append(cellvalue).append(EXCEL_LINE_DELIMITER);
			}
			// 以字符串返回该行的数据
			return entity;
			// return buffer.toString();
		} else {
			return null;
		}
	}

	/***************************************************************************
	 * 关闭函数执行流的操作
	 */
	public void close() {
		// 如果is不为空，则关闭InputStream文件输入流
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				is = null;
				e.printStackTrace();
			}
		}
		// 如果reader不为空,则关闭BufferedReader文件输入流
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				reader = null;
				e.printStackTrace();
			}
		}
	}

	public E getEntity() {
		return entity;
	}

	/***************************************************************************
	 * 设置读取Excel记录行后转换的实体对像实例
	 * 
	 * @param entity
	 */
	public void setEntity(E entity) {
		this.entity = entity;
	}

	public Excel2EntityConfig getExcel2EntityConfig() {
		return excel2EntityConfig;
	}

	public void setExcel2EntityConfig(
			Excel2EntityConfig excel2EntityConfig) {
		this.excel2EntityConfig = excel2EntityConfig;
	}

	/***************************************************************************
	 * 将指定英文字符串首字母大写
	 * 
	 * @param filed
	 * @return 首字母大写后的字符串
	 */
	private String A2UpperCase(String filed) {
		return filed.substring(0, 1).toUpperCase()
				+ filed.substring(1, filed.length());
	}

	 /**
     * 读取Excel表格表头的内容
     * @param InputStream
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            workbook = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            workbook = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
                j++;
            }
            content.put(i, str);
            str = "";
        }
        return content;
    }
    
    
    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
    
    
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }
	
}
