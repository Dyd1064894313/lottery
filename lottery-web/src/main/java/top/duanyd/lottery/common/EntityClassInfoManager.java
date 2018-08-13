package top.duanyd.lottery.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;
import top.duanyd.lottery.annotation.Table;
import top.duanyd.lottery.exception.MyJdbcTempletAnnotationException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/8/10.
 */
public class EntityClassInfoManager {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Map<Class<?>, EntityClassInfo> classInfoMap = new HashMap();

    public static EntityClassInfo getEntityClassInfo(Class<?> cls){
        if(classInfoMap.get(cls) == null){
            EntityClassInfo entityClassInfo = new EntityClassInfo();
            try {
                entityClassInfo.setTableName(getTableName(cls));
                entityClassInfo.setAllDBFieldMap(getAllDBFieldMap(cls));
                entityClassInfo.setIdFieldMap(getIdFieldMap(entityClassInfo.getAllDBFieldMap()));
                entityClassInfo.setAutoIncrementField(getAutoIncrementField(entityClassInfo.getAllDBFieldMap()));
                entityClassInfo.setInsertableFieldMap(getInsertableFieldMap(entityClassInfo.getAllDBFieldMap()));
                entityClassInfo.setUpdateableFieldMap(getUpdateableFieldMap(entityClassInfo.getAllDBFieldMap()));
                entityClassInfo.setSetMethodMap(getSetMethodMap(entityClassInfo.getAllDBFieldMap()));
                entityClassInfo.setGetMethodMap(getGetMethodMap(entityClassInfo.getAllDBFieldMap()));
                entityClassInfo.setIdFieldList(getIdFieldList(entityClassInfo.getIdFieldMap()));
                entityClassInfo.setInsertableFieldList(getIndertableFieldList(entityClassInfo.getInsertableFieldMap()));
                entityClassInfo.setUpdateableFieldList(getUpdateableFieldList(entityClassInfo.getUpdateableFieldMap()));
                classInfoMap.put(cls, entityClassInfo);
            }catch (Exception e) {
                throw new MyJdbcTempletAnnotationException(e);
            }
            return entityClassInfo;
        }
        return classInfoMap.get(cls);
    }

    public static String getTableName(Class<?> cls) {
        Assert.notNull(cls, "Class not be null!");
        boolean hasAnnotation = cls.isAnnotationPresent(Table.class);
        if(!hasAnnotation){
            throw new MyJdbcTempletAnnotationException("Entity class " + cls.getName() + " must have Table annotation!");
        }
        Table table = cls.getAnnotation(Table.class);
        String tableName = table.value();
        if(StringUtils.isBlank(tableName)){
            throw new IllegalArgumentException("Entity class " + cls.getName() + " Table annotation value cannot be blank!");
        }
        return tableName;
    }

    public static Map<String, Field> getAllDBFieldMap(Class<?> cls){
        Assert.notNull(cls, "Class not be null!");
        Map<String, Field> allDBFieldMap = new HashMap<>();
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
            return allDBFieldMap;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Column.class)){
                allDBFieldMap.put(field.getName(), field);
            }
        }
        return allDBFieldMap;
    }

    public static Map<String, Field> getIdFieldMap(Map<String, Field> allDBFieldMap){
        Map<String, Field> idFieldMap = new HashMap<>();
        Collection<Field> fieldList = allDBFieldMap.values();
        if(fieldList == null || fieldList.isEmpty()){
            return idFieldMap;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Id.class)){
                if(!field.isAnnotationPresent(Column.class)){
                    throw new MyJdbcTempletAnnotationException("Entity class " + field.getDeclaringClass().getName() + " id field " + field.getName() + " miss annotation Column");
                }
                idFieldMap.put(field.getName(), field);
            }
        }
        return idFieldMap;
    }

    public static Field getAutoIncrementField(Map<String, Field> allDBFieldMap) {
        Field autoIncrementField = null;
        Collection<Field> fieldList = allDBFieldMap.values();
        if(fieldList == null || fieldList.isEmpty()){
            return autoIncrementField;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Id.class) && field.getAnnotation(Id.class).autoIncrement()){
                if(autoIncrementField != null){
                    throw new MyJdbcTempletAnnotationException("There can only be one for the autoIncrement primary key");
                }
                autoIncrementField = field;
            }
        }
        return autoIncrementField;
    }

    public static Map<String, Field> getInsertableFieldMap(Map<String, Field> allDBFieldMap){
        Map<String, Field> insertableFieldMap = new HashMap<>();
        Collection<Field> fieldList = allDBFieldMap.values();
        if(fieldList == null || fieldList.isEmpty()){
            return insertableFieldMap;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).insertAlle()){
                insertableFieldMap.put(field.getName(), field);
            }
        }
        return insertableFieldMap;
    }

    public static Map<String, Field> getUpdateableFieldMap(Map<String, Field> allDBFieldMap){
        Map<String, Field> updateableFieldMap = new HashMap<>();
        Collection<Field> fieldList = allDBFieldMap.values();
        if(fieldList == null || fieldList.isEmpty()){
            return updateableFieldMap;
        }
        for(Field field : fieldList){
            if(field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).updateAble()){
                updateableFieldMap.put(field.getName(), field);
            }
        }
        return updateableFieldMap;
    }

    public static Map<String, Method> getSetMethodMap(Map<String, Field> allDBFieldMap) throws IntrospectionException {
        Map<String, Method> setMethodMap = new HashMap<>();
        Collection<Field> fieldList = allDBFieldMap.values();
        if(fieldList == null || fieldList.isEmpty()){
            return setMethodMap;
        }
        for(Field field : fieldList){
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), field.getDeclaringClass());
            Method setMethod = pd.getWriteMethod();
            if(setMethod != null){
                setMethodMap.put(field.getName(), setMethod);
            }
        }
        return setMethodMap;
    }

    public static Map<String, Method> getGetMethodMap(Map<String, Field> allDBFieldMap) throws IntrospectionException {
        Map<String, Method> getMethodMap = new HashMap<>();
        Collection<Field> fieldList = allDBFieldMap.values();
        if(fieldList == null || fieldList.isEmpty()){
            return getMethodMap;
        }
        for(Field field : fieldList){
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), field.getDeclaringClass());
            Method setMethod = pd.getReadMethod();
            if(setMethod != null){
                getMethodMap.put(field.getName(), setMethod);
            }
        }
        return getMethodMap;
    }

    public static void setPara(PreparedStatement ps, Object value, int columnIndex) throws SQLException {
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

    public static void setNumberValue(Object obj, Field field, Method setMethod, Number number) throws InvocationTargetException, IllegalAccessException {
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

    public static String getValue(Object bean, Method method) throws InvocationTargetException, IllegalAccessException {

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

    /**
     * 获取主键列表
     * @param idFieldMap
     * @return
     */
    public static List<Field> getIdFieldList(Map<String, Field> idFieldMap){
        Collection<Field> values = idFieldMap.values();
        if(values == null || values.size() == 0){
            return null;
        }
        return new ArrayList<Field>(values);
    }

    /**
     * 获取可插入的字段列表
     * @param insertableFieldMap
     * @return
     */
    public static List<Field> getIndertableFieldList(Map<String, Field> insertableFieldMap){
        Collection<Field> values = insertableFieldMap.values();
        if(values == null || values.size() == 0){
            return null;
        }
        return new ArrayList<Field>(values);
    }

    /**
     * 获取可插入的字段列表
     * @param updateableFieldMap
     * @return
     */
    public static List<Field> getUpdateableFieldList(Map<String, Field> updateableFieldMap){
        Collection<Field> values = updateableFieldMap.values();
        if(values == null || values.size() == 0){
            return null;
        }
        return new ArrayList<Field>(values);
    }

}
