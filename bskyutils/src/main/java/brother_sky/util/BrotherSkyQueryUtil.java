package brother_sky.util;

import brother_sky.exception.BrotherSkyException;
import brother_sky.po.BrotherSkyQueryPo;
import brother_sky.po.BrotherSkyWhereInfo;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BrotherSkyQueryUtil {
	
	/**
	 * 设置查询头
	 * @param selectColoumn
	 * @param skyPo
	 */
	public static void makeSelect(String selectColoumn,BrotherSkyQueryPo skyPo){
		if(selectColoumn!=null && selectColoumn.startsWith(" select ")){
			return;
		}
		selectColoumn = " select "+selectColoumn;
		skyPo.setSelectColoumn(selectColoumn);
	}
	
	/**
	 * 设置查询表
	 * @param fromTable
	 * @param skyPo
	 */
	public static void makeFrom(String fromTable,BrotherSkyQueryPo skyPo){
		if(fromTable!=null && fromTable.startsWith(" from ")){
			return;
		}
		fromTable = " from " + fromTable;
		skyPo.setFromTable(fromTable);
	}

	/**
	 * 设置查询条件
	 * @param paramMap
	 * @param skyPo
	 */
	public static void makeWhereParam(Map<String,Object> paramMap,BrotherSkyQueryPo skyPo){
		String where  = " where 1=1 ";
		for(BrotherSkyWhereInfo whereParam: skyPo.getWhereInfoList()){
			//取键值各自数组的最小长度
			int length = Math.min(whereParam.getKey().length, whereParam.getObj().length);
			String whereValue = whereParam.getWhereValue();
			if(length==0){
				where = where + " and " + whereValue + " ";
				continue;
			}
			//in条件判断，特殊处理，现阶段一个whereParam只能处理一个in不能有其他传参条件拼接，可以有不带参数的条件拼接
			if("IN".equals(whereParam.getType())){
				String inValues = packageInvalue(whereParam);
				whereValue = whereValue.replaceFirst("\\?", inValues);
				where = where + " and " + whereValue + " ";
				continue;
			}
			for(int i = 0; i<length;i++){
				whereValue = whereValue.replaceFirst("\\?", " #{"+whereParam.getKey()[i].trim()+"}");
				paramMap.put(whereParam.getKey()[i].trim(), whereParam.getObj()[i]);//这个map是外部传来的，外部的map已添加好值，不需要重新对skyPo进行set
			}
			//or条件判断，特殊处理
			if("OR".equals(whereParam.getType())){
				where = where + " " + whereValue + "  ";
				continue;
			}
			where = where + " and " + whereValue + " ";
			
		}
		skyPo.setWhereValue(where);
	}

    /**
     * 组装IN条件
     * @param whereParam
     * @return
     */
	private static String packageInvalue(BrotherSkyWhereInfo whereParam) throws BrotherSkyException{
        StringBuffer inSb = new StringBuffer();
        Object obj = whereParam.getObj()[0];
        List list = null;
        if(obj instanceof List){
            list = (List)obj;
        }else if(obj.getClass().isArray()){
            Object[] x = (Object[]) obj;
            list = Arrays.asList(x);
        }else{
            throw new BrotherSkyException(BrotherSkyException.ALIPAY_FORM_EXCEPTION);//类型不对，不是集合或者数组型
        }
        try {
            //合并
            Consumer<List> consumer = (b) -> b.stream().forEach((p) ->inSb.append(p).append(",").toString());
            consumer.accept(list);
        } catch (Exception e) {
            throw new BrotherSkyException(BrotherSkyException.ALIPAY_FORM_EXCEPTION);//类型不对，不是集合或者数组型
        }
        //获取结果
        String inValues = inSb.toString().substring(0,inSb.toString().length()-1);
        return inValues;
    }
	
	/**
	 * 设置排序信息
	 * @param skyPo
	 */
	public static void makeOrderParam(BrotherSkyQueryPo skyPo){
		if(skyPo!=null && StringUtils.isNotBlank(skyPo.getOrderValue())){
			if(skyPo.getOrderValue().startsWith(" order by ")){
				return;
			}
			String order = " order by " + skyPo.getOrderValue();
			skyPo.setOrderValue(order);
		}
		
	}
	
	/**
	 * 设置分页信息
	 * @param skyPo
	 */
	public static void replaceLimitParam(BrotherSkyQueryPo skyPo){
		if(skyPo!=null && StringUtils.isNotBlank(skyPo.getLimitValue())){
			if(skyPo.getLimitValue().startsWith(" limit ")){
				return;
			}
			String limit  = " limit "+skyPo.getLimitValue();
			skyPo.setLimitValue(limit);
		}
	}
}
