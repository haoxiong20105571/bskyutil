package brother_sky.common;

import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrotherSkyCommon {




	/** 
	 * 将map装换为javabean对象 
	 * @param map 
	 * @param bean 
	 * @return 
	 */  
	public static <T> T mapToBean(Map<String, Object> map,T bean) {
		if(map==null){
			return null;
		}
	    BeanMap beanMap = BeanMap.create(bean);  
	    beanMap.putAll(map);  
	    return bean;  
	}

	/**
	 * 将map装换为javabean对象
	 * 根据参数类型，2是驼峰转下划线，1是下划线转驼峰，其他是原值不动
	 * @param map
	 * @param bean
	 * @param type
	 * @return
	 */
	public static <T> T mapToBean(Map<String, Object> map,T bean,int type){
		Map<String, Object> tempMap  = new HashMap<>();
		if(type==BrotherSkyContents.TYPE_LINE_TO_HUMP){//1是下划线转驼峰
			tempMap = changeMapKeyToHump(map);
		}else if(type == BrotherSkyContents.TYPE_HUMP_TO_LINE){//2是驼峰转下划线
			tempMap = changeMapKeyToLine(map);
		}else{
			tempMap = map;
		}
		//其他是原值不动
		return mapToBean(tempMap,bean);
	}

	/************************************************/
	
	/**
	 * mybatis结果批量转换目标对象
	 * @param mapList
	 * @param classBean
	 * @return
	 */
	public static <T> List<T> getResultList(List<LinkedHashMap<String,Object>> mapList,Class<T> classBean) {
		List<T> list = new ArrayList<T>();
		for(LinkedHashMap<String,Object> map : mapList){
			try {
				T bean = classBean.newInstance();
				list.add(mapToBean(map,bean));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 *  mybatis结果批量转换目标对象
	 *  根据参数类型，2是驼峰转下划线，1是下划线转驼峰，其他是原值不动
	 * @param mapList
	 * @param classBean
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getResultList(List<LinkedHashMap<String,Object>> mapList,Class<T> classBean,int type){
		List<T> list = new ArrayList<T>();
		for(LinkedHashMap<String,Object> map : mapList){
			Map<String, Object> tempMap  = new HashMap<>();
			if(type==BrotherSkyContents.TYPE_LINE_TO_HUMP){//1是下划线转驼峰
				tempMap = changeMapKeyToHump(map);
			}else if(type == BrotherSkyContents.TYPE_HUMP_TO_LINE){//2是驼峰转下划线
				tempMap = changeMapKeyToLine(map);
			}else{
				tempMap = map;
			}
			try {
				T bean = classBean.newInstance();
				list.add(mapToBean(tempMap,bean));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}


	/************************************************/
	
	/**
	 * 对象转map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> BeanToMap(Object obj) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for(PropertyDescriptor property : descriptors){
				String name =property.getName();
				//过滤属性名类似class的属性
				if(name.compareToIgnoreCase("class") == 0){
					continue;
				}
				Object objtemp= propertyUtilsBean.getNestedProperty(obj, name);
				if(objtemp!=null){
					params.put(name,objtemp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 对象转map
	 * 根据参数类型，2是驼峰转下划线，1是下划线转驼峰，其他是原值不动
	 * @param obj
	 * @param type
	 * @return
	 */
	public static Map<String, Object> BeanToMap(Object obj,int type){
		//1是下划线转驼峰
		if(type==BrotherSkyContents.TYPE_LINE_TO_HUMP){
			return changeMapKeyToHump(BeanToMap(obj));
		}
		//2是驼峰转下划线
		if(type == BrotherSkyContents.TYPE_HUMP_TO_LINE){
			return changeMapKeyToLine(BeanToMap(obj));
		}
		//其他是原值不动
		return BeanToMap(obj);
	}

	/************************************************/
	
	/**
	 * 对象转map，只转有值属性
	 * @param obj
	 * @return
	 */
	public static Map<String,Object> BeanToMapWithOutNIL(Object obj){
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for(PropertyDescriptor property : descriptors){
				String name = property.getName();
				//过滤属性名类似class的属性
				if(name.compareToIgnoreCase("class") == 0){
					continue;
				}
				Method getMethod = property.getReadMethod();//从属性描述器中获取 get 方法
				Object value = getMethod.invoke(obj, new Object[]{});//调用方法获取方法的返回值
				//属性不为空的进行添加
				if(value!=null){
					params.put(name, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 对象转map，只转有值属性;
	 * 根据参数类型，2是驼峰转下划线，1是下划线转驼峰，其他是原值不动
	 * @param obj
	 * @param type
	 * @return
	 */
	public static Map<String, Object> BeanToMapWithOutNIL(Object obj,int type){
		//1是下划线转驼峰
		if(type==BrotherSkyContents.TYPE_LINE_TO_HUMP){
			return changeMapKeyToHump(BeanToMapWithOutNIL(obj));
		}
		//2是驼峰转下划线
		if(type == BrotherSkyContents.TYPE_HUMP_TO_LINE){
			return changeMapKeyToLine(BeanToMapWithOutNIL(obj));
		}
		//其他是原值不动
		return BeanToMapWithOutNIL(obj);
	}

	/************************************************/

	/**
	 * 获取map全部key字符串，以逗号分隔
	 * @param paramMap
	 * @return
	 */
	public static String getMapAllKey(Map<String,Object> paramMap){
		Set<String> keys = paramMap.keySet();
		return keys.toString().substring(1, keys.toString().length()-1);
	}

	/************************************************/

	/**下划线转驼峰*/
	public static String lineToHump(String str){
		if(StringUtils.isBlank(str)){
			return str;
		}
		Pattern linePattern = Pattern.compile("_(\\w)");
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**驼峰转下划线*/
	public static String humpToLine(String str){
		Pattern humpPattern = Pattern.compile("[A-Z]");
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 把map的key下划式转为驼峰式
	 * @param map
	 * @return
	 */
	public static Map<String,Object> changeMapKeyToHump(Map<String,Object> map){
		if(map==null){
			return null;
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		for(Map.Entry<String, Object> entry : map.entrySet()){
			String name = entry.getKey();
			resultMap.put(lineToHump(name),entry.getValue());
		}
		return resultMap;
	}

	/**
	 * 把map的key驼峰式转化成下划式
	 * @param map
	 * @return
	 */
	public static Map<String,Object> changeMapKeyToLine(Map<String,Object> map){
		if(map==null){
			return null;
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		for(Map.Entry<String, Object> entry : map.entrySet()){
			String name = entry.getKey();
			resultMap.put(humpToLine(name),entry.getValue());
		}
		return resultMap;
	}

}
