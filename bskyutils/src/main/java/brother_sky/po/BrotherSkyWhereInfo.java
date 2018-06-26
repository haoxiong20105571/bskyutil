package brother_sky.po;


public class BrotherSkyWhereInfo {
	
	private String whereValue;
	
	private String[] key;
	
	private Object[] obj;

	private String type = "and";//默认and，还有 in 和 or 类型
	
	public BrotherSkyWhereInfo() {
		super();
	}

	public BrotherSkyWhereInfo(String whereValue, String[] key, Object[] obj) {
		super();
		this.whereValue = whereValue;
		this.key = key;
		this.obj = obj;
	}

	public BrotherSkyWhereInfo(String whereValue, String[] key, Object[] obj, String type) {
		this.whereValue = whereValue;
		this.key = key;
		this.obj = obj;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWhereValue() {
		return whereValue;
	}

	public void setWhereValue(String whereValue) {
		this.whereValue = whereValue;
	}
	
	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

	public Object[] getObj() {
		return obj;
	}

	public void setObj(Object[] obj) {
		this.obj = obj;
	}
	
	
	public void addWhereValue(String where,String key[],Object[] obj){
		this.whereValue = where;
		this.key = key;
		this.obj = obj;
	}
}
