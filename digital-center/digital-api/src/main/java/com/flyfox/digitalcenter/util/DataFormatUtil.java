package com.flyfox.digitalcenter.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.flyfox.digitalcenter.util.DataFormatUtil;
import com.flyfox.digitalcenter.util.DateUtil;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
 
/**
 * 
 * @author xinhongfeng
 *
 */
public class DataFormatUtil {
	private static Logger log = LoggerFactory.getLogger(DataFormatUtil.class);

	private static final ConcurrentHashMap<Class<?>, ObjectReader> objectReaderMap = new ConcurrentHashMap<Class<?>, ObjectReader>();
	private static final Object objectReaderMapLock = new Object();
	private static final ObjectMapper m = new ObjectMapper();
	static {
		m.setSerializationInclusion(Include.NON_NULL);
		m.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 根据java对象生成json string
	 * 
	 * @param obj
	 * @return 如果参数为null则返回"";<br/>
	 *         如果序列化错误则返回null;<br/>
	 *         正常返回json字符串
	 */
	public static String toJsonString(final Object obj) {
		if (obj == null) {
			return "";
		}
		try {
			return m.writeValueAsString(obj);
//			 return JSONObject.toJSONString(obj);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 根据json字符串生成jsonNode对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static JsonNode toJsonNode(final String jsonString) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}

		try {
			return m.readTree(jsonString);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> T toBean(Class<T> clazz, String jsonStr) {
		if (clazz == null || StringUtils.isBlank(jsonStr)) {
			log.error("class and jsonStr params can't be empty;class=" + clazz
					+ ";jsonStr=" + jsonStr);
			return null;
		}
		ObjectReader reader = objectReaderMap.get(clazz);
		if (reader == null) {
			synchronized (objectReaderMapLock) {
				if ((reader = objectReaderMap.get(clazz)) == null) {
					reader = objectReaderMap
							.putIfAbsent(clazz, m.reader(clazz));
					if (reader == null) {
						reader = objectReaderMap.get(clazz);
					}
				}
			}
		}
		try {
			return reader.readValue(jsonStr);
		} catch (Exception e) {
			log.error(
					e.getMessage() + "class=" + clazz + ";jsonStr=" + jsonStr,
					e);
		}
		return null;
	}

	public static Boolean isEmpty(String data) {
		if ("{}".equals(data) || StringUtils.isBlank(data)) {
			return true;
		}
		return false;
	}

	/**  
     * 将一个 JavaBean 对象转化为一个  Map  
     * @param bean 要转化的JavaBean 对象  
     * @return 转化出来的  Map 对象  
     * @throws IntrospectionException 如果分析类属性失败  
     * @throws IllegalAccessException 如果实例化 JavaBean 失败  
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败  
     */  
	@SuppressWarnings({ "rawtypes", "unchecked" })    
    public static Map convertBean(Object bean, String ignoreName)    
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {    
        Class type = bean.getClass();
        Map returnMap = new HashMap();    
        BeanInfo beanInfo = Introspector.getBeanInfo(type);    
    
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
        for (int i = 0; i< propertyDescriptors.length; i++) {    
            PropertyDescriptor descriptor = propertyDescriptors[i];    
            String propertyName = descriptor.getName();    
            if (!propertyName.equals("class")&&!propertyName.equalsIgnoreCase(ignoreName)) {    
                Method readMethod = descriptor.getReadMethod();    
                Object result = readMethod.invoke(bean, new Object[0]);    
                if (result != null) {    
                    returnMap.put(propertyName, result);    
                }  
            }    
        }    
        return returnMap;    
    }  
  
  
  
/**  
     * 将一个 Map 对象转化为一个 JavaBean  
     * @param type 要转化的类型  
     * @param map 包含属性值的 map  
     * @return 转化出来的 JavaBean 对象  
     * @throws IntrospectionException 如果分析类属性失败  
     * @throws IllegalAccessException 如果实例化 JavaBean 失败  
     * @throws InstantiationException 如果实例化 JavaBean 失败  
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败  
     */    
    @SuppressWarnings("rawtypes")    
    public static Object convertMap(Class type, Map map)    
            throws IntrospectionException, IllegalAccessException,    
            InstantiationException, InvocationTargetException {    
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性    
        Object obj = type.newInstance(); // 创建 JavaBean 对象    
    
        // 给 JavaBean 对象的属性赋值    
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
        for (int i = 0; i< propertyDescriptors.length; i++) {    
            PropertyDescriptor descriptor = propertyDescriptors[i];    
            String propertyName = descriptor.getName();    
    
            if (map.containsKey(propertyName)) {    
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。    
                Object value = map.get(propertyName);    
    
                Object[] args = new Object[1];    
                args[0] = value;    
    
                descriptor.getWriteMethod().invoke(obj, args);    
            }    
        }    
        return obj;    
    }  
    
    
    public static Map<String, String> objToMapModle(Object obj) {
		
		Class<?> clazz = obj.getClass();  
		
		Field[] fs = clazz.getDeclaredFields();
		Map<String, String> params = new HashMap<String, String>();
		
		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			field.setAccessible(true);
			Object val;
			try {
				val = field.get(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
//			System. out .println( "name:" +field.getName()+ "      value = " +val);
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			if(field!=null&&val!=null&&!val.equals("null")&&!val.equals("")){
				params.put(field.getName(),val.toString());
			}
			
		}
		return params;
    }
    
    
    public static<T> T mapToObjModle(T entity, Map<String, String> params) {
		Class<?> clazz = entity.getClass();  
        // 得到对象的字段  
        List<Field> fields = getAccessibleFields(clazz);  
        // 迭代字段  
        for (Field f : fields) {  
            String name = f.getName();  
            Object objVal = params.get(name);  
            // 找到对应值，进行转化设置  
            if (objVal != null) {  
                try {  
                    if (f.getType().equals(String.class)) {  
                        objVal = String.valueOf(objVal);  
                    } else if (!f.getType().isAssignableFrom(objVal.getClass())) {  
                        if (!Strings.isNullOrEmpty(String.valueOf(objVal))) {  
                            //转换依赖方法：org.apache.commons.beanutils.ConvertUtils.convert(Object, Class<?>)  
                        	if (f.getType().equals(Date.class)) {
                        		objVal = DateUtil.parseDate(String.valueOf(objVal), DateUtil.DATE_FORMAT_ORIGIN_DATE);
							} else{
								objVal = ConvertUtils.convert(objVal, f.getType());  
							}
                        } else {  
                            objVal = null;  
                        }  
                    }  
                    f.set(entity, objVal);  
                } catch (Exception e) {  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return entity;  
	}
	
	 /** 
     * 循环向上转型, 获取对象所有的DeclaredField 
     *  
     * 如向上转型到Object仍无法找到, 返回null. 
     */  
    public static List<Field> getAccessibleFields(final Class<?> clazz) {  
        List<Field> fields = new ArrayList<>();  
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {  
            for (Field f : superClass.getDeclaredFields()) {  
                boolean hasInSubClass = false;  
                for (Field f2 : fields) {
                    if (f2.getName().equals(f.getName())) {  
                        hasInSubClass = true;  
                        break;  
                    }  
                }  
                if (!hasInSubClass) {
                    makeAccessible(f);  
                    fields.add(f);  
                }  
            }  
        }  
        return fields;  
    }  
    
    /** 
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。 
     */ 
    public static void makeAccessible(Field field) {  
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())  
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {  
            field.setAccessible(true);  
        }  
    }  
}