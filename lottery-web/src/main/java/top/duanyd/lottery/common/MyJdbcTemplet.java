package top.duanyd.lottery.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;
import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;
import top.duanyd.lottery.annotation.Table;
import top.duanyd.lottery.exception.MyJdbcTempletAnnotationException;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2018/8/8.
 */
public class MyJdbcTemplet extends JdbcTemplate {

    public MyJdbcTemplet(){
        super();
    }
    public MyJdbcTemplet(DataSource dataSource){
        super(dataSource);
    }
    public MyJdbcTemplet(DataSource dataSource, boolean lazyInit){
        super(dataSource, lazyInit);
    }
    public List queryForMethod(String sql, Object[] args, final Class c) {

        return super.query(sql, args, new RowMapper() {

            public Object mapRow(ResultSet rs, int arg1) throws SQLException {

                int count = rs.getMetaData().getColumnCount();
                Method[] mst = c.getDeclaredMethods();

                try {
                    Object	ob = c.newInstance();
                    for (int i = 0; i < count; i++) {
                        String ms = "set"
                                + rs.getMetaData().getColumnName(i + 1);
                        for (int j = 0; j < mst.length; j++) {
                            //equalsIgnoreCase 没有大小写的对比;
                            if (mst[j].getName().equalsIgnoreCase(ms)) {
                                mst[j].invoke(ob, rs.getObject(i + 1));
                            }
                        }
                    }
                    return ob;
                } catch (Exception e) {
                    return null;
                }

            }
        });
    }
    public List query(String sql, Object[] args, final Class<?> c) {
        final List al = new ArrayList();
        Object ob;
        return super.query(sql, args, new RowMapper() {
            public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
                Class[] classtype = new Class[arg0.getMetaData()
                        .getColumnCount()];
                Object[] args = new Object[arg0.getMetaData().getColumnCount()];
                for (int i = 0; i < arg0.getMetaData().getColumnCount(); i++) {
                    args[i] = arg0.getObject(i + 1);
                    classtype[i] = args[i].getClass();
                }
                try {
                    Constructor cors = c.getConstructor(classtype);
                    Object ob = cors.newInstance(args);
                    al.add(ob);
                    return ob;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    /**
     * 单个对象插入，如果主键自增会把插入数据库生成的自增主键值回写到对象里
     * @param entity
     * @throws MyJdbcTempletAnnotationException
     */
    public void insert(Object entity) throws MyJdbcTempletAnnotationException{
        Assert.notNull(entity, "Entity must not be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Field autoIncrementField = entityClassInfo.getAutoIncrementField();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> insertableFieldList = entityClassInfo.getInsertableFieldList();
        if(insertableFieldList == null || insertableFieldList.size() == 0){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no insertable field!");
        }
        String sql = getInsertSql(tableName, insertableFieldList);
        logger.info(sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                for(int i = 0; i < insertableFieldList.size(); i++){
                    Field field = insertableFieldList.get(i);
                    Method getMethod = getMethodMap.get(field.getName());
                    if(getMethod == null){
                        throw new SQLException("Entity " + c.getName() + " field " + field.getName() + " have no get method!");
                    }
                    try {
                        Object value = getMethod.invoke(entity);
                        EntityClassInfoManager.setPara(preparedStatement, value, i + 1);
                        logFieldValue(field.getName(), value);
                    }catch (Exception e) {
                        throw new SQLException("Entity " + c.getName() + " set value to PrepareStatement for insert have an error!", e);
                    }
                }
                return preparedStatement;
            }
        }, keyHolder);
        if(autoIncrementField != null){
            //获取自增主键的值并赋给传入的entity
            try {
                Number key = keyHolder.getKey();
                EntityClassInfoManager.setNumberValue(entity, autoIncrementField, setMethodMap.get(autoIncrementField.getName()), key);
            } catch (Exception e) {
                throw new MyJdbcTempletAnnotationException(e);
            }
        }
    }

    public void insertBatch(List<Object> entityList)throws MyJdbcTempletAnnotationException{
        if(entityList == null || entityList.isEmpty()){
            throw new IllegalArgumentException("Param list cannot be empty!");
        }
        final Class<?> c = entityList.get(0).getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> insertableFieldList = entityClassInfo.getInsertableFieldList();
        if(insertableFieldList == null || insertableFieldList.size() == 0){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no insertable field!");
        }
        String sql = getInsertSql(tableName, insertableFieldList);
        logger.info(sql);
        logListTotle("insert ", entityList.size());
        this.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                logOperateDataNum(i);
                for(int j = 0; j < insertableFieldList.size(); j++) {
                    Field field = insertableFieldList.get(j);
                    Method getMethod = getMethodMap.get(field.getName());
                    if (getMethod == null) {
                        throw new SQLException("Entity " + c.getName() + " field " + field.getName() + " have no get method!");
                    }
                    try {
                        Object value = getMethod.invoke(entityList.get(i));
                        EntityClassInfoManager.setPara(preparedStatement, value, j + 1);
                        logFieldValue(field.getName(), value);
                    } catch (Exception e) {
                        throw new SQLException("Entity " + c.getName() + " set value to PrepareStatement for insert have an error!", e);
                    }
                }
            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }
        });
    }

    public int deleteById(Object entity)throws MyJdbcTempletAnnotationException{
        Assert.notNull(entity, "Entity must not be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns cannot user this method!");
        }
        String sql = getDeleteSqlHead(tableName) +  getWhereAfterSql(idFieldList);
        logger.info(sql);
        Object args[] = new Object[idFieldList.size()];
        for(int i = 0; i < idFieldList.size(); i++){
            Method getMethod = getMethodMap.get(idFieldList.get(i).getName());
            if (getMethod == null) {
                throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " field " + idFieldList.get(i).getName() + " have no get method!");
            }
            try {
                args[i] = getMethod.invoke(entity);
            }catch (Exception e){
                throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " set value to PrepareStatement for insert have an error!", e);
            }
            if(args[i] == null){
                throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " field " + idFieldList.get(i).getName() + " is null!");
            }
            logFieldValue(idFieldList.get(i).getName(), args[i]);
        }
        int num = this.update(sql,args);
        return num;
    }

    public void batchDeleteById(List<Object> entityList)throws MyJdbcTempletAnnotationException{
        if(entityList == null || entityList.isEmpty()){
            throw new IllegalArgumentException("Param entityList cannot be empty!");
        }
        final Class<?> c = entityList.get(0).getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns cannot user this method!");
        }
        String sql = getDeleteSqlHead(tableName) +  getWhereAfterSql(idFieldList);
        logger.info(sql);
        logListTotle("insert", entityList.size());
        Object args[] = new Object[idFieldList.size()];
        this.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                logOperateDataNum(i);
                for(int j = 0; j < idFieldList.size(); j++) {
                    Field field = idFieldList.get(j);
                    Method getMethod = getMethodMap.get(field.getName());
                    if (getMethod == null) {
                        throw new SQLException("Entity " + c.getName() + " field " + field.getName() + " have no get method!");
                    }
                    try {
                        Object value = getMethod.invoke(entityList.get(i));
                        EntityClassInfoManager.setPara(preparedStatement, value, j + 1);
                        logFieldValue(field.getName(), value);
                    } catch (Exception e) {
                        throw new SQLException("Entity " + c.getName() + " set value to PrepareStatement for insert have an error!", e);
                    }
                }
            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }
        });
    }

    private static String getInsertSql(String tableName, List<Field> fieldList){

        StringBuffer head = new StringBuffer("INSERT INTO `").append(tableName.toUpperCase()).append("` (");
        StringBuffer tail = new StringBuffer(" VALUES (");
        Field field = fieldList.get(0);
        head.append("`").append(field.getAnnotation(Column.class).value()).append("`");
        tail.append("?");
        for(int i = 1; i < fieldList.size(); i++){
            field = fieldList.get(i);
            head.append(", `").append(field.getAnnotation(Column.class).value().toUpperCase()).append("`");
            tail.append(", ?");
        }
        head.append(")");
        tail.append(")");
        return head.append(tail).toString();
    }

    public static String getDeleteSqlHead(String tableName){
        StringBuffer sql = new StringBuffer("DELETE FROM `").append(tableName.toUpperCase()).append("` WHERE ");
        return sql.toString();
    }

    /**
     * 获取 WHERE 之后的查询条件sql
     * @param fieldList
     * @return
     */
    public static String getWhereAfterSql(List<Field> fieldList){
        StringBuffer sql = new StringBuffer();
        sql.append("`").append(fieldList.get(0).getAnnotation(Column.class).value().toUpperCase()).append("` = ? ");
        for(int i = 1; i < fieldList.size(); i++){
            sql.append("AND `").append(fieldList.get(i).getAnnotation(Column.class).value().toUpperCase()).append("` = ? ");
        }
        return sql.toString();
    }

    public void logFieldValue(String fieldName, Object value){
        logger.info("-- " + fieldName + ":\t\t" + value);
    }

    public void logListTotle(String methodName, int size){
        logger.info("----- Totle " + methodName + " " + size + " data -----");
    }

    public void logOperateDataNum(int index){
        logger.info("----- Batch insert " + index + "th data -----");
    }

}
