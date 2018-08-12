package top.duanyd.lottery.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;
import top.duanyd.lottery.annotation.Table;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/8/10.
 */
public class EntityClassInfoManager {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Map<Class<?>, EntityClassInfo> classInfoMap = new HashMap();

    public static EntityClassInfo getEntityClassInfo(Class<?> cls) throws Exception {
        if(classInfoMap.get(cls) == null){
            EntityClassInfo entityClassInfo = new EntityClassInfo();
            entityClassInfo.setTableName(getTableName(cls));
            entityClassInfo.setIdFieldMap(getIdFieldMap(cls));
            entityClassInfo.setAutoIncrementField(getAutoIncrementField(cls));
            entityClassInfo.setInsertAbleFieldMap(getInsertAbleFieldMap(cls));
            entityClassInfo.setUpdateAbleFieldMap(getUpdateAbleFieldMap(cls));
            entityClassInfo.setSetMethodMap(getSetMethodMap(cls));
            entityClassInfo.setGetMethodMap(getGetMethodMap(cls));
            classInfoMap.put(cls, entityClassInfo);
            return entityClassInfo;
        }
        return classInfoMap.get(cls);
    }

    public static String getTableName(Class<?> cls){
        Assert.notNull(cls, "class not be null!");
        boolean hasAnnotation = cls.isAnnotationPresent(Table.class);
        if(!hasAnnotation){
            return null;
        }
        Table table = cls.getAnnotation(Table.class);
        return table.value();
    }

    public static Map<String, Field> getIdFieldMap(Class<?> cls){
        Assert.notNull(cls, "class not be null!");
        Map<String, Field> idFieldMap = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if(fields != null){
            fieldList.addAll(Arrays.asList(fields));
        }
        Class<?> parent = cls.getSuperclass();
        while(parent != null){
            fields = parent.getDeclaredFields();
            if(fields != null){
                fieldList.addAll(Arrays.asList(fields));
            }
            parent = parent.getSuperclass();
        }
        if(fieldList.isEmpty()){
            return idFieldMap;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Id.class)){
                idFieldMap.put(field.getName(), field);
            }
        }
        return idFieldMap;
    }

    public static Field getAutoIncrementField(Class<?> cls) throws Exception {
        Assert.notNull(cls, "class not be null!");
        Map<String, Field> idFieldMap = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if(fields != null){
            fieldList.addAll(Arrays.asList(fields));
        }
        Class<?> parent = cls.getSuperclass();
        while(parent != null){
            fields = parent.getDeclaredFields();
            if(fields != null){
                fieldList.addAll(Arrays.asList(fields));
            }
            parent = parent.getSuperclass();
        }
        if(fieldList.isEmpty()){
            return null;
        }
        Field autoIncrementField = null;
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Id.class) && field.getAnnotation(Id.class).autoIncrement()){
                if(autoIncrementField != null){
                    throw new Exception("There can only be one for the autoIncrement primary key");
                }
                autoIncrementField = field;
            }
        }
        return autoIncrementField;
    }

    public static Map<String, Field> getInsertAbleFieldMap(Class<?> cls){
        Assert.notNull(cls, "class not be null!");
        Map<String, Field> insertAbleFieldMap = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if(fields != null){
            fieldList.addAll(Arrays.asList(fields));
        }
        Class<?> parent = cls.getSuperclass();
        while(parent != null){
            fields = parent.getDeclaredFields();
            if(fields != null){
                fieldList.addAll(Arrays.asList(fields));
            }
            parent = parent.getSuperclass();
        }
        if(fieldList.isEmpty()){
            return insertAbleFieldMap;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).insertAlle()){
                insertAbleFieldMap.put(field.getName(), field);
            }
        }
        return insertAbleFieldMap;
    }

    public static Map<String, Field> getUpdateAbleFieldMap(Class<?> cls){
        Assert.notNull(cls, "class not be null!");
        Map<String, Field> updateAbleFieldMap = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if(fields != null){
            fieldList.addAll(Arrays.asList(fields));
        }
        Class<?> parent = cls.getSuperclass();
        while(parent != null){
            fields = parent.getDeclaredFields();
            if(fields != null){
                fieldList.addAll(Arrays.asList(fields));
            }
            parent = parent.getSuperclass();
        }
        if(fieldList.isEmpty()){
            return updateAbleFieldMap;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).updateAble()){
                updateAbleFieldMap.put(field.getName(), field);
            }
        }
        return updateAbleFieldMap;
    }

    public static Map<String, Method> getSetMethodMap(Class<?> cls) throws Exception {
        Assert.notNull(cls, "class not be null!");
        Map<String, Method> setMethodMap = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if(fields != null){
            fieldList.addAll(Arrays.asList(fields));
        }
        Class<?> parent = cls.getSuperclass();
        while(parent != null){
            fields = parent.getDeclaredFields();
            if(fields != null){
                fieldList.addAll(Arrays.asList(fields));
            }
            parent = parent.getSuperclass();
        }
        for(Field field : fieldList){
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
            Method setMethod = pd.getWriteMethod();
            if(setMethod != null){
                setMethodMap.put(field.getName(), setMethod);
            }
        }
        return setMethodMap;
    }

    public static Map<String, Method> getGetMethodMap(Class<?> cls) throws Exception {
        Assert.notNull(cls, "class not be null!");
        Map<String, Method> getMethodMap = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        if(fields != null){
            fieldList.addAll(Arrays.asList(fields));
        }
        Class<?> parent = cls.getSuperclass();
        while(parent != null){
            fields = parent.getDeclaredFields();
            if(fields != null){
                fieldList.addAll(Arrays.asList(fields));
            }
            parent = parent.getSuperclass();
        }
        for(Field field : fieldList){
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
            Method setMethod = pd.getReadMethod();
            if(setMethod != null){
                getMethodMap.put(field.getName(), setMethod);
            }
        }
        return getMethodMap;
    }

    public static void setPara(PreparedStatement ps, Object value, int columnIndex) throws Exception{
        if(value != null){
            Class<?> valueType = value.getClass();
            if (valueType.equals(String.class)) {
                ps.setString(columnIndex, value.toString());
            } else if(valueType.equals(int.class) || valueType.equals(Integer.class)) {
                ps.setInt(columnIndex, Integer.parseInt(value.toString(), 10));
            } else if(valueType.equals(long.class) || valueType.equals(Long.class)) {
                ps.setLong(columnIndex, Long.parseLong(value.toString()));
            } else if(valueType.equals(short.class) || valueType.equals(Short.class)) {
                ps.setShort(columnIndex, Short.parseShort(value.toString()));
            } else if(valueType.equals(java.util.Date.class)) {
                ps.setTimestamp(columnIndex, new java.sql.Timestamp(((java.util.Date)value).getTime()));
            } else if(valueType.equals(boolean.class) || valueType.equals(Boolean.class)) {
                ps.setBoolean(columnIndex, Boolean.parseBoolean(value.toString()));
            } else if(valueType.equals(double.class) || valueType.equals(Double.class)) {
                ps.setDouble(columnIndex, Double.parseDouble(value.toString()));
            } else if(valueType.equals(float.class) || valueType.equals(Float.class)) {
                ps.setFloat(columnIndex, Float.parseFloat(value.toString()));
            } else if(valueType.equals(byte.class) || valueType.equals(Byte.class)) {
                ps.setByte(columnIndex, Byte.parseByte(value.toString()));
            } else if(valueType.equals(byte[].class) || valueType.equals(Byte[].class)) {
                ps.setBytes(columnIndex, (byte[])value);
            } else if(valueType.equals(BigDecimal.class)) {
                ps.setBigDecimal(columnIndex, new BigDecimal(value.toString()));
            } else if(valueType.equals(Timestamp.class)) {
                ps.setTimestamp(columnIndex, (Timestamp)value);
            } else if(valueType.equals(java.sql.Date.class)) {
                ps.setTimestamp(columnIndex, new java.sql.Timestamp(((java.sql.Date)value).getTime()));
            } else{
                ps.setObject(columnIndex, value);
            }
        }
        else{
            ps.setObject(columnIndex, null);
        }
    }

    public static void setNumberValue(Object obj, Field field, Method setMethod, Number number) throws Exception{
        if(obj == null || field == null || setMethod == null || number == null){
            return;
        }
        Class<?> valueType = field.getType();
        if(valueType.equals(int.class) || valueType.equals(Integer.class)) {
            setMethod.invoke(obj, number.intValue());
        } else if(valueType.equals(long.class) || valueType.equals(Long.class)) {
            setMethod.invoke(obj, number.longValue());
        } else if(valueType.equals(short.class) || valueType.equals(Short.class)) {
            setMethod.invoke(obj, number.shortValue());
        } else if(valueType.equals(double.class) || valueType.equals(Double.class)) {
            setMethod.invoke(obj, number.doubleValue());
        } else if(valueType.equals(float.class) || valueType.equals(Float.class)) {
            setMethod.invoke(obj, number.floatValue());
        } else if(valueType.equals(byte.class) || valueType.equals(Byte.class)) {
            setMethod.invoke(obj, number.byteValue());
        }
    }

    public static String getValue(Object bean, Method method) throws Exception {

        String retValue = "";

        Object valueObj = method.invoke(bean, new Object[] {});
        Class<?> valueType = method.getReturnType();
        if (valueType.equals(String.class)) {
            if (valueObj == null) {
                retValue = "''";
            } else {
                retValue = "'" + valueObj.toString() + "'";
            }
        } else if(valueType.equals(java.sql.Timestamp.class)
                || valueType.equals(java.sql.Date.class)
                || valueType.equals(java.util.Date.class)){
            if (valueObj == null) {
                retValue = "''";
            } else {
                retValue = "'" + sdf.format(valueObj) + "'";
            }
        } else if(valueType.equals(boolean.class)
                || valueType.equals(Boolean.class)){
            if(valueObj != null && valueObj.toString().equalsIgnoreCase("true")){
                retValue = "1";
            } else {
                retValue = "0";
            }
        } else {
            if (valueObj == null) {
                retValue = "null";
            } else {
                retValue = valueObj.toString();
            }
        }

        return retValue;
    }

}
