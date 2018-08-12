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
    private Map<String, Field> insertAbleFieldMap;
    private Map<String, Field> updateAbleFieldMap;
    private Map<String, Method> setMethodMap;
    private Map<String, Method> getMethodMap;

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

    public Map<String, Field> getInsertAbleFieldMap() {
        return insertAbleFieldMap;
    }

    public void setInsertAbleFieldMap(Map<String, Field> insertAbleFieldMap) {
        this.insertAbleFieldMap = insertAbleFieldMap;
    }

    public Map<String, Field> getUpdateAbleFieldMap() {
        return updateAbleFieldMap;
    }

    public void setUpdateAbleFieldMap(Map<String, Field> updateAbleFieldMap) {
        this.updateAbleFieldMap = updateAbleFieldMap;
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
}
