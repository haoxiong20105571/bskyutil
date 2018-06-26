package brother_sky.po;

/**
 * update set
 * @author xh
 *
 */
public class BrotherSkySetInfo {

	private String setValue;
	
	private String[] key;
	
	private Object[] obj;

	public BrotherSkySetInfo() {
		super();
	}

	public BrotherSkySetInfo(String setValue, String[] key, Object[] obj) {
		super();
		this.setValue = setValue;
		this.key = key;
		this.obj = obj;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
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
	
	public void addSetValue(String setValue,String key[],Object[] obj){
		this.setValue = setValue;
		this.key = key;
		this.obj = obj;
	}
}
