package brother_sky.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface BrotherSkyMapper {
	
	/**
	 * 通用查询
	 * @param map
	 * @return
	 */
	 public List<LinkedHashMap<String,Object>> brotherSkySearch(Map<String, Object> map);
	 
	 /**
	  * 通用查询二
	  * @param map
	  * @return
	  */
	 public LinkedHashMap<String,Object> brotherSkySingleSearch(Map<String, Object> map);
	 
	 /**
	  * 获取查询总数
	  * @param map
	  * @return
	  */
	 public Long getTotalNum(Map<String, Object> map);
	 
	 /**
	  * 获取单个字符串集合
	  * @param map
	  * @return
	  */
	 public List<String> getSingleStringList(Map<String, Object> map);
	 
	 /**
	  * 获取单个Long集合
	  * @param map
	  * @return
	  */
	 public List<Long> getSingleLongList(Map<String, Object> map);
	 
	 /**
	  * 通用插入
	  * @param map
	  */
	 public void britherSkyInsert(Map<String, Object> map);
	 
	 /**
	  * 通用修改
	  * @param map
	  */
	 public Integer britherSkyUpdate(Map<String, Object> map);
}
