package brother_sky.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brother_sky.util.BrotherSkyUpdateUtil;

public class BrotherSkyUpdatePo {
	
	private String updateTable;
	
	private String updateSet;
	
	private String updateWhere;
	
	private Map<String,Object> updateObjectMap = new HashMap<String,Object>();
	
	private List<BrotherSkySetInfo> updateSetList = new ArrayList<BrotherSkySetInfo>();
	
	private List<BrotherSkyWhereInfo> whereInfoList = new ArrayList<BrotherSkyWhereInfo>();//查询条件list
	
	private Map<String,Object> paramMap = new HashMap<String,Object>();
	
	private String id;
	
	private String sql;

	public String getUpdateTable() {
		return updateTable;
	}

	public void setUpdateTable(String updateTable) {
		this.updateTable = updateTable;
	}

	public String getUpdateSet() {
		return updateSet;
	}

	public void setUpdateSet(String updateSet) {
		this.updateSet = updateSet;
	}

	public String getUpdateWhere() {
		return updateWhere;
	}

	public void setUpdateWhere(String updateWhere) {
		this.updateWhere = updateWhere;
	}

	public Map<String, Object> getUpdateObjectMap() {
		return updateObjectMap;
	}

	public void setUpdateObjectMap(Map<String, Object> updateObjectMap) {
		this.updateObjectMap = updateObjectMap;
	}
	
	public List<BrotherSkySetInfo> getUpdateSetList() {
		return updateSetList;
	}

	public void setUpdateSetList(List<BrotherSkySetInfo> updateSetList) {
		this.updateSetList = updateSetList;
	}

	public List<BrotherSkyWhereInfo> getWhereInfoList() {
		return whereInfoList;
	}

	public void setWhereInfoList(List<BrotherSkyWhereInfo> whereInfoList) {
		this.whereInfoList = whereInfoList;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 添加set条件和参数名
	 * @param set
	 * @param params
	 * @param obj
	 */
	public void addSetValue(String set,String[] params,Object[] obj){
		this.updateSetList.add(new BrotherSkySetInfo(set, params, obj));
	}
	

	/**
	 * 添加where条件和参数名
	 * @param where 单个条件
	 * @param params 参数名
	 * @param obj	参数对象
	 */
	public void addWhereValue(String where,String[] params,Object[] obj){
		this.whereInfoList.add(new BrotherSkyWhereInfo(where,params,obj));
	}
	
	/**
	 * 获取最终终查询sql
	 * @return
	 */
	public String getFinalSQL(){
		BrotherSkyUpdateUtil.makeUpdateTable(this.updateTable,this);
		BrotherSkyUpdateUtil.makeSetValueForObject(this);
		BrotherSkyUpdateUtil.makeWhereParam(paramMap, this);
		String sql = this.updateTable + this.updateSet + this.updateWhere;
		System.out.println("*******执行sql********:"+sql);
		this.sql = sql;
		return sql;
	}
	
	/**
	 * 获取sql查询参数
	 * @return
	 */
	public Map<String,Object> getSQlParamMap(){
		this.paramMap.putAll(this.updateObjectMap);
		String sql = getFinalSQL();
		Map<String,Object> sqlParamMap = this.paramMap;
		sqlParamMap.put("sql", sql);
		return sqlParamMap;
	}
}
