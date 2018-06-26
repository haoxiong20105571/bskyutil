package brother_sky.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;

import brother_sky.common.BrotherSkyCommon;
import brother_sky.po.BrotherSkyInsertPo;

public class BrotherSkyInsertUtil {
	
	/**
	 * 设置插入表名
	 * @param table
	 * @param skyPo
	 */
	public static void makeInsertTable(String table,BrotherSkyInsertPo skyPo){
		if(table != null && table.startsWith(" insert ")){
			return;
		}
		table = " insert into "+table;
		skyPo.setInsertTable(table);;
	}
	
	/**
	 * 设置插入字段
	 * @param insertColumn
	 * @param skyPo
	 */
	private static void makeInsertColumn(String insertColumn,BrotherSkyInsertPo skyPo){
		if(insertColumn != null && insertColumn.startsWith(" ( ")){
			return;
		}
		insertColumn = " ( " + insertColumn+" )";
		skyPo.setInsertColoumn(insertColumn);
	}
	
	/**
	 * 根据插入对象map值进行添加
	 * @param paramMap
	 * @param skyPo
	 */
	private static void makeInsertColumnByMap(Map<String,Object> paramMap,BrotherSkyInsertPo skyPo){
		String insertColumn = BrotherSkyCommon.getMapAllKey(paramMap);
		makeInsertColumn(insertColumn,skyPo);
	}
	
	private static void makeInsertValueByMap(Map<String,Object> paramMap,BrotherSkyInsertPo skyPo){
		
	}
	
	/**
	 * 设置插入值
	 * @param paramMap
	 * @param skyPo
	 */
	public static void makeInsertValue(Map<String,Object> paramMap,BrotherSkyInsertPo skyPo){
		String cloumns = " ( ";
		String values = " values ( ";
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			cloumns = cloumns + entry.getKey() + " ,";//末尾不要加空格，加了的话后面的截取位数也要跟着变
			values = values + " #{" + entry.getKey().trim() + "}" + " ,";
		}
		cloumns = cloumns.substring(0, cloumns.length() - 1) + " ) ";
		values = values.substring(0, values.length() - 1) + " ) ";
		skyPo.setInsertColoumn(cloumns);
		skyPo.setInsertValue(values);
	}
	
	
	
}
