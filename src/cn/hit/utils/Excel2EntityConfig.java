package cn.hit.utils;

import java.text.SimpleDateFormat;

public class Excel2EntityConfig {

	private String[] columns;

	private SimpleDateFormat formater = new SimpleDateFormat(
			"yyyy-MM-dd HH：mm：ss ");

	private int currPosittion = 0;
	
	private int colStartPosittion = 1;

	public SimpleDateFormat getFormater() {
		return formater;
	}

	/***************************************************************************
	 * 设置日期型字段的转换格式 默认值为 new SimpleDateFormat("yyyy-MM-dd HH：mm：ss ");
	 */
	public void setFormater(SimpleDateFormat formater) {
		this.formater = formater;
	}

	public String[] getColumns() {
		return columns;
	}

	/***************************************************************************
	 * 设置Excel列与实体字段的对应关系
	 * 
	 * @param columns
	 *            实体字段的 字符串数组表示 例如： String[] columns = {"字段一", "字段二",
	 *            "字段三","字段n..." };
	 *            Excel表格第一列对应实体对像字段一，Excel表格第二列对应实体对像字段二....依次类推
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public int getCurrPosittion() {
		return currPosittion;
	}

	/***************************************************************************
	 * 设置excel表格数据从第几行开始，默认值,跳过标题行，从第二行开始
	 * 该值大于0
	 */
	public void setCurrPosittion(int currPosittion) {
		this.currPosittion = currPosittion-1;
	}
	public int getColStartPosittion() {
		return colStartPosittion;
	}
	/***************************************************************************
	 * 设置excel表格数据从第几列开始，默认值,从第1列开始
	 * 该值大于0
	 */
	public void setColStartPosittion(int colStartPosittion) {
		this.colStartPosittion = colStartPosittion-1;
	}

}
