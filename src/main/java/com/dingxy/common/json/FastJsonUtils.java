package com.dingxy.common.json;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author dingxy
 * @date 2016年4月25日16:56:57
 * @description fastjson工具类
 */
public class FastJsonUtils {

	/**
	 * @author dingxy
	 * @date 2016年4月25日16:56:57
	 * @param jsonStr   json串对象
     * @param beanClass 指定的bean
     * @param <T> 任意bean的类型
     * @return 转换后的java bean对象
	 * @description 将json字符串，转换成指定java bean
     */
    public static <T> T toBean(String jsonStr, Class<T> beanClass) {
    	if(StringUtils.isEmpty(jsonStr)){
    		return null;
    	}
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        jsonObj.put(JSON.DEFAULT_TYPE_KEY, beanClass.getName());
        return JSON.parseObject(jsonObj.toJSONString(), beanClass);
    }
}
