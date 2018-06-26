package brother_sky.util;

import java.util.Map;

import brother_sky.po.BrotherSkySetInfo;
import brother_sky.po.BrotherSkyUpdatePo;
import brother_sky.po.BrotherSkyWhereInfo;

public class BrotherSkyUpdateUtil {

	/**
	 * 设置插入表名
	 * @param table
	 * @param skyPo
	 */
	public static void makeUpdateTable(String table,BrotherSkyUpdatePo skyPo){
		if(table !=null && table.startsWith(" update ")){
			return;
		}
		table = " update "+table;
		skyPo.setUpdateTable(table);
	}
	
	/**
	 * 组装set的值
	 * @param skyPo
	 */
	public static void makeSetValueForObject(BrotherSkyUpdatePo skyPo){
		Map<String,Object> updateSetMap = skyPo.getUpdateObjectMap();
		for (Map.Entry<String, Object> entry : updateSetMap.entrySet()) {
			//id主键过滤
			if(entry.getKey().equals(skyPo.getId())){
				continue;
			}
			skyPo.addSetValue(entry.getKey()+" = ? ", new String[]{entry.getKey().trim()}, new Object[]{entry.getValue()});
		}
		makeSetValue(skyPo);
	}

	
	/**
	 * 设置set值
	 * @param skyPo
	 */
	private static void makeSetValue(BrotherSkyUpdatePo skyPo){
		String set = skyPo.getUpdateSet();
		if(set!=null && set.startsWith(" set ")){
			return;
		}
		set = " set ";
		for(BrotherSkySetInfo setInfo : skyPo.getUpdateSetList()){
			//取键值各自数组的的小长度
			int length = Math.min(setInfo.getKey().length, setInfo.getObj().length);
			String setValue = setInfo.getSetValue();
			for(int i=0;i<length;i++){
				setValue = setValue.replaceFirst("\\?", " #{"+setInfo.getKey()[i].trim()+"}");
				set = set + setValue + " ,";//末尾不要加空格，加了的话后面的截取位数也要跟�?�?
			}
		}
		set = set.substring(0, set.length() - 1);
		skyPo.setUpdateSet(set);
	}
	
	
	/**
	 * 设置查询条件
	 * @param paramMap
	 * @param skyPo
	 */
	public static void makeWhereParam(Map<String,Object> paramMap,BrotherSkyUpdatePo skyPo){
		String where  = " where 1=1 ";
		for(BrotherSkyWhereInfo whereParam: skyPo.getWhereInfoList()){
			//取键值各自数组的最小长度
			int length = Math.min(whereParam.getKey().length, whereParam.getObj().length);
			String whereValue = whereParam.getWhereValue();
			for(int i = 0; i<length;i++){
				whereValue = whereValue.replaceFirst("\\?", " #{"+whereParam.getKey()[i].trim()+"}");
				paramMap.put(whereParam.getKey()[i], whereParam.getObj()[i]);//这个map是外部传来的，外部的map已添加好值，不需要重新对skyPo进行set
			}
			where = where + " and " + whereValue + " ";
		}
		skyPo.setUpdateWhere(where);
	}
	
}
