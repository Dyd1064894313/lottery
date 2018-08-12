package top.duanyd.lottery.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/10.
 */
public class EntityClassInfo {

    private String tableName;
    private Map<String, Field> idFieldMap;
    private Field autoIncrementField;
    private Map<String, Field> insertableFieldMap;
    private Map<String, Field> updateableFieldMap;
    private Map<String, Method> setMethodMap;
    private Map<String, Method> getMethodMap;
    private List<Field> insertableFieldList;
    private List<Field> updateableFieldList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Field getAutoIncrementField() {
        return autoIncrementField;
    }

    public void setAutoIncrementField(Field autoIncrementField) {
        this.autoIncrementField = autoIncrementField;
    }

    public Map<String, Field> getIdFieldMap() {
        return idFieldMap;
    }

    public void setIdFieldMap(Map<String, Field> idFieldMap) {
        this.idFieldMap = idFieldMap;
    }

    public Map<String, Field> getInsertableFieldMap() {
        return insertableFieldMap;
    }

    public void setInsertableFieldMap(Map<String, Field> insertableFieldMap) {
        this.insertableFieldMap = insertableFieldMap;
    }

    public Map<String, Field> getUpdateableFieldMap() {
        return updateableFieldMap;
    }

    public void setUpdateableFieldMap(Map<String, Field> updateableFieldMap) {
        this.updateableFieldMap = updateableFieldMap;
    }

    public Map<String, Method> getSetMethodMap() {
        return setMethodMap;
    }

    public void setSetMethodMap(Map<String, Method> setMethodMap) {
        this.setMethodMap = setMethodMap;
    }

    public Map<String, Method> getGetMethodMap() {
        return getMethodMap;
    }

    public void setGetMethodMap(Map<String, Method> getMethodMap) {
        this.getMethodMap = getMethodMap;
    }

    public List<Field> getInsertableFieldList() {
        return insertableFieldList;
    }

    public void setInsertableFieldList(List<Field> insertableFieldList) {
        this.insertableFieldList = insertableFieldList;
    }

    public List<Field> getUpdateableFieldList() {
        return updateableFieldList;
    }

    public void setUpdateableFieldList(List<Field> updateableFieldList) {
        this.updateableFieldList = updateableFieldList;
    }
}
