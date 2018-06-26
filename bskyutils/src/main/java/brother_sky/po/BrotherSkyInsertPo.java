package brother_sky.po;

import java.util.HashMap;
import java.util.Map;

import brother_sky.util.BrotherSkyInsertUtil;

public class BrotherSkyInsertPo {

	private String insertTable;
	
	private String insertColoumn;
	
	private String insertValue;
	
	private Map<String,Object> insertObjectMap = new HashMap<String,Object>();
	
	private String sql;//最终终sql

	public String getInsertTable() {
		return insertTable;
	}

	public void setInsertTable(String insertTable) {
		this.insertTable = insertTable;
	}

	public String getInsertColoumn() {
		return insertColoumn;
	}

	public void setInsertColoumn(String insertColoumn) {
		this.insertColoumn = insertColoumn;
	}

	public String getInsertValue() {
		return insertValue;
	}

	public void setInsertValue(String insertValue) {
		this.insertValue = insertValue;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, Object> getInsertObjectMap() {
		return insertObjectMap;
	}

	public void setInsertObjectMap(Map<String, Object> insertObjectMap) {
		this.insertObjectMap = insertObjectMap;
	}
	
	/**
	 * 获取最终插入sql
	 * @return
	 */
	public String getFinalSQL(){
		BrotherSkyInsertUtil.makeInsertTable(this.insertTable, this);
		BrotherSkyInsertUtil.makeInsertValue(insertObjectMap, this);
		String sql = this.insertTable + this.insertColoumn + this.insertValue;
		System.out.println("*******执行sql********:"+sql);
		this.sql = sql;
		return sql;
	}
	
	/**
	 * 获取sql查询参数
	 * @return
	 */
	public Map<String,Object> getSQlParamMap(){
		Map<String,Object> sqlParamMap = this.insertObjectMap;
		sqlParamMap.put("sql", getFinalSQL());
		return sqlParamMap;
	}
}
