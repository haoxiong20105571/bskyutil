package brother_sky.po;

import brother_sky.util.BrotherSkyQueryUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BrotherSkyQueryPo {

	private Map<String,String> tableNameMap = new HashMap<String,String>();//表名带别名
	
	private Map<String,String> selectColoumnMap = new HashMap<String,String>();//字段名带别名
	
	private List<BrotherSkyWhereInfo> whereInfoList = new ArrayList<BrotherSkyWhereInfo>();//查询条件list
	
	private Map<String,Object> paramMap = new HashMap<String,Object>();

	private String fromTable="";//查询表
	
	private String selectColoumn="";//赛选字段名
	
	private String whereValue="";//查询条件

	private String whereIn = "";//in条件

	private String whereOr = "";//or条件
	
	private String groupValue="";//分组内容
	
	private String havingValue="";//分组条件内容
	
	private String orderValue="";//排序内容
	
	private String limitValue="";//分页内容
	
	private String sql;//最终sql
	
	public String getFromTable() {
		return fromTable;
	}

	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}

	public String getSelectColoumn() {
		return selectColoumn;
	}

	public void setSelectColoumn(String selectColoumn) {
		this.selectColoumn = selectColoumn;
	}

	public String getWhereValue() {
		return whereValue;
	}

	public void setWhereValue(String whereValue) {
		this.whereValue = whereValue;
	}

	public String getWhereIn() {
		return whereIn;
	}

	public void setWhereIn(String whereIn) {
		this.whereIn = whereIn;
	}

	public String getWhereOr() {
		return whereOr;
	}

	public void setWhereOr(String whereOr) {
		this.whereOr = whereOr;
	}

	public String getGroupValue() {
		return groupValue;
	}

	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}

	public String getHavingValue() {
		return havingValue;
	}

	public void setHavingValue(String havingValue) {
		this.havingValue = havingValue;
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public String getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, String> getTableNameMap() {
		return tableNameMap;
	}

	public void setTableNameMap(Map<String, String> tableNameMap) {
		this.tableNameMap = tableNameMap;
	}

	public Map<String, String> getSelectColoumnMap() {
		return selectColoumnMap;
	}

	public void setSelectColoumnMap(Map<String, String> selectColoumnMap) {
		this.selectColoumnMap = selectColoumnMap;
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
	
	/**
	 * 追加选择字段
	 * @param addColumn
	 */
	public void selectColoumnAdd(String addColumn){
		this.selectColoumn = this.selectColoumn +" , "+ addColumn;
	}
	
	/**
	 * 表左连接
	 * @param joinValue
	 */
	public void leftJoin(String joinValue){
		//如果fromTable还没有添加，则不以join起头，但是不能带on
		if(StringUtils.isBlank(this.fromTable)){
			this.fromTable = joinValue;
			return;
		}
		this.fromTable = this.fromTable + " left join " + joinValue;
	}
	
	/**
	 * 表右连接
	 * @param joinValue
	 */
	public void rightJoin(String joinValue){
		//如果fromTable还没有添加，则不以join起头，但是不能带on
		if(StringUtils.isBlank(this.fromTable)){
			this.fromTable = joinValue;
			return;
		}
		this.fromTable = this.fromTable + " right join " + joinValue;
	}
	
	/**
	 * 内连接
	 * @param joinValue
	 */
	public void innerJoin(String joinValue){
		//如果fromTable还没有添加，则不以join起头，但是不能带on
		if(StringUtils.isBlank(this.fromTable)){
			this.fromTable = joinValue;
			return;
		}
		this.fromTable = this.fromTable + " inner join " + joinValue;
	}
	
	/**
	 * 添加where条件和参数
	 * @param where 单个条件
	 * @param params 参数名
	 * @param obj	参数对象
	 */
	public void addWhereValue(String where,String[] params,Object[] obj){
		this.whereInfoList.add(new BrotherSkyWhereInfo(where,params,obj));
	}

	/**
	 * 不带查询对象
	 * @param where
	 */
	public void addWhereValue(String where){
		this.whereInfoList.add(new BrotherSkyWhereInfo(where,new String[]{},new Object[]{}));
	}

	/**
	 * IN、OR类型的查询条件
	 * @param where
	 * @param params
	 * @param obj
	 * @param type
	 */
	public void addWhereValue(String where,String[] params,Object[] obj,String type){
		this.whereInfoList.add(new BrotherSkyWhereInfo(where,params,obj,type));
	}
	
	/**
	 * 设置分页信息
	 * @param index
	 * @param size
	 */
	public void setPageIndexAndSize(int index,int size){
		this.limitValue = (index-1)*size + "," + size;
	}
	
	/**
	 * 设置排序信息
	 * @param column
	 * @param type
	 */
	public void setOrderColumnAndType(String column,String type){
		if(StringUtils.isBlank(column)||StringUtils.isBlank(type)){
			return;
		}
		this.orderValue = column + " " + type;
	}

	/**
	 * 获取最终查询sql
	 * @return
	 */
	public String getFinalSQL(){
		BrotherSkyQueryUtil.makeSelect(this.selectColoumn, this);
		BrotherSkyQueryUtil.makeFrom(this.fromTable, this);
		BrotherSkyQueryUtil.makeWhereParam(this.paramMap, this);
		BrotherSkyQueryUtil.makeOrderParam(this);
		BrotherSkyQueryUtil.replaceLimitParam(this);
		String sql = this.selectColoumn + this.fromTable + this.whereValue
				+ this.groupValue + this.havingValue + this.orderValue
				+ this.limitValue;
		System.out.println("*******执行sql********："+sql);
		this.sql = sql;
		return sql;
	}
	
	/**
	 * 获取总数查询sql
	 * @return
	 */
	public String getTotalSQL(){
		BrotherSkyQueryUtil.makeFrom(this.fromTable, this);
		BrotherSkyQueryUtil.makeWhereParam(this.paramMap, this);
		String sql = " select count(*) " + this.fromTable + this.whereValue
				+ this.groupValue + this.havingValue;
		System.out.println("*******执行sql********："+sql);
		this.sql = sql;
		return sql;
	}
	
	/**
	 * 获取总数查询sql
	 * @return
	 */
	public String getTotalSQLBySelf(String cloumn){
		BrotherSkyQueryUtil.makeFrom(this.fromTable, this);
		BrotherSkyQueryUtil.makeWhereParam(this.paramMap, this);
		String sql = " select count("+cloumn+") " + this.fromTable + this.whereValue
				+ this.groupValue + this.havingValue;
		System.out.println("*******执行sql********："+sql);
		this.sql = sql;
		return sql;
	}
	
	public String getTotalSqlWithGourp(String cloumn){
		BrotherSkyQueryUtil.makeFrom(this.fromTable, this);
		BrotherSkyQueryUtil.makeWhereParam(this.paramMap, this);
		String sql = " select count(*) from ( select count(*)" + this.fromTable + this.whereValue
				+ this.groupValue + this.havingValue +" ) tiange";
		System.out.println("*******执行sql********："+sql);
		this.sql = sql;
		return sql;
	}
	
	/**
	 * 获取sql查询参数
	 * @return
	 */
	public Map<String,Object> getSQlParamMap(){
		Map<String,Object> sqlParamMap = this.paramMap;
		sqlParamMap.put("sql", getFinalSQL());
		return sqlParamMap;
	}
	
	/**
	 * 根据id获取对象
	 * @param idName id字段名
	 * @param tableName 表名
	 * @return
	 */
	public Map<String,Object> getSingleByCloumn(String idName,Object id,String tableName){
		this.selectColoumn = " * ";
		this.fromTable = tableName;
		this.addWhereValue(idName+" = ? ", new String[]{idName},new Object[]{id});
		return getSQlParamMap();
	}
	
	/**
	 * 获取总数sql查询参数
	 * @return
	 */
	public Map<String,Object> getTotalSQLParamMap(){
		Map<String,Object> sqlParamMap = this.paramMap;
		sqlParamMap.put("sql", getTotalSQL());
		return sqlParamMap;
	}
	
	/**
	 * 根据value获取参数
	 * @param value 如果vlaue==group,则获取分组后的总数
	 * @return
	 */
	public Map<String,Object> getTotalSQLParamMap(String value){
		Map<String,Object> sqlParamMap = this.paramMap;
		if("group".equals(value)){
			sqlParamMap.put("sql", getTotalSqlWithGourp(value));
		}else{
			sqlParamMap.put("sql", getTotalSQLBySelf(value));
		}
		return sqlParamMap;
	}
	
}
