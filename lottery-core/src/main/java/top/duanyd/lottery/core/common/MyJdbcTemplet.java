package top.duanyd.lottery.core.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;
import top.duanyd.lottery.core.annotation.Column;
import top.duanyd.lottery.core.exception.MyJdbcTempletAnnotationException;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    /**
     * 单个对象插入，如果主键自增会把插入数据库生成的自增主键值回写到对象里
     * @param entity
     * @throws MyJdbcTempletAnnotationException
     */
    public void insert(Object entity) throws MyJdbcTempletAnnotationException{
        Assert.notNull(entity, "Param entity cannot be null!");
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
        String sql = EntityClassInfoManager.getInsertSql(tableName, insertableFieldList);
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

    /**
     * 批量插入对象，不支持返回自增主键值
     * @param entityList
     * @throws MyJdbcTempletAnnotationException
     */
    public void batchInsert(List<?> entityList)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(entityList, "Param list cannot be empty!");
        final Class<?> c = entityList.get(0).getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> insertableFieldList = entityClassInfo.getInsertableFieldList();
        if(insertableFieldList == null || insertableFieldList.size() == 0){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no insertable field!");
        }
        String sql = EntityClassInfoManager.getInsertSql(tableName, insertableFieldList);
        logger.info(sql);
        logListTotle("insert ", entityList.size());
        this.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                logOperateDataNum(i);
                setObjectValuesToPrepareStatement(entityList.get(i), insertableFieldList, getMethodMap, preparedStatement, false);
            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }
        });
    }

    /**
     * 根据主键删除
     * @param entity
     * @return 影响行数
     * @throws MyJdbcTempletAnnotationException
     */
    public int deleteById(Object entity)throws MyJdbcTempletAnnotationException{
        Assert.notNull(entity, "Param entity cannot be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns!");
        }
        String sql = EntityClassInfoManager.getDeleteSqlHead(tableName) +  EntityClassInfoManager.getWhereAfterSql(idFieldList);
        logger.info(sql);
        Object args[] = getObjectValue(entity, idFieldList, getMethodMap, true);
        return this.update(sql, args);
    }

    /**
     * 根据主键批量删除
     * @param entityList
     * @throws MyJdbcTempletAnnotationException
     */
    public void batchDeleteById(List<?> entityList)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(entityList, "Param entityList cannot be empty!");
        final Class<?> c = entityList.get(0).getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns!");
        }
        String sql = EntityClassInfoManager.getDeleteSqlHead(tableName) +  EntityClassInfoManager.getWhereAfterSql(idFieldList);
        logger.info(sql);
        logListTotle("delete", entityList.size());
        Object args[] = new Object[idFieldList.size()];
        this.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                logOperateDataNum(i);
                setObjectValuesToPrepareStatement(entityList.get(i), idFieldList, getMethodMap, preparedStatement, true);
            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }
        });
    }

    /**
     * 根据指定字段删除
     * @param entity
     * @param fields
     * @return
     * @throws MyJdbcTempletAnnotationException
     */
    public int deleteByFields(String[] fields, Object entity)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(fields, "Param fields cannot be empty!");
        Assert.notNull(entity, "Param entity cannot be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Field> allDBFieldMap = entityClassInfo.getAllDBFieldMap();
        List<Field> fieldList = new ArrayList<>();
        for(String field : fields){
            Field f = allDBFieldMap.get(field);
            if(f == null){
                throw new MyJdbcTempletAnnotationException("Filed " + field + " have no Column annotation cannot trans to database field!");
            }else{
                fieldList.add(f);
            }
        }
        String sql = EntityClassInfoManager.getDeleteSqlHead(tableName) +  EntityClassInfoManager.getWhereAfterSql(fieldList);
        logger.info(sql);
        Object args[] = getObjectValue(entity, fieldList, getMethodMap, false);
        return this.update(sql, args);
    }

    /**
     * 根据指定字段批量删除
     * @param fields
     * @param entityList
     * @throws MyJdbcTempletAnnotationException
     */
    public void batchDeleteByFields(String[] fields, List<?> entityList)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(fields, "Param fields cannot be empty!");
        Assert.notEmpty(entityList, "Param entityList cannot be empty!");
        final Class<?> c = entityList.get(0).getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Field> allDBFieldMap = entityClassInfo.getAllDBFieldMap();
        List<Field> fieldList = new ArrayList<>();
        for(String field : fields){
            Field f = allDBFieldMap.get(field);
            if(f == null){
                throw new MyJdbcTempletAnnotationException("Filed " + field + " have no Column annotation cannot trans to database field!");
            }else{
                fieldList.add(f);
            }
        }
        String sql = EntityClassInfoManager.getDeleteSqlHead(tableName) +  EntityClassInfoManager.getWhereAfterSql(fieldList);
        logger.info(sql);
        logListTotle("delete", entityList.size());
        this.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                logOperateDataNum(i);
                setObjectValuesToPrepareStatement(entityList.get(i), fieldList, getMethodMap, preparedStatement, false);
            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }
        });
    }

    /**
     * 删除表全部数据
     * @param entityClass
     * @return
     * @throws MyJdbcTempletAnnotationException
     */
    public int deleteAllData(Class<?> entityClass)throws MyJdbcTempletAnnotationException{
        Assert.notNull(entityClass, "Param entityClass cannot be null!");
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();

        String sql = EntityClassInfoManager.getDeleteSqlHead(tableName);
        logger.info(sql);
        return this.update(sql);
    }

    /**
     * 根据主键更新
     * @param entity
     * @return 影响行数
     * @throws MyJdbcTempletAnnotationException
     */
    public int updateById(Object entity)throws MyJdbcTempletAnnotationException{
        Assert.notNull(entity, "Param entity cannot be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns!");
        }
        List<Field> updateableFieldList = entityClassInfo.getUpdateableFieldList();
        if(updateableFieldList == null || updateableFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no insertable field!");
        }
        String sql = EntityClassInfoManager.getUpdateSqlHead(tableName, updateableFieldList) +  EntityClassInfoManager.getWhereAfterSql(idFieldList);
        logger.info(sql);
        Object argsBefore[] = getObjectValue(entity, updateableFieldList, getMethodMap, false);
        Object argsAfter[] = getObjectValue(entity, idFieldList, getMethodMap, true);
        Object args[] = Arrays.copyOf(argsBefore, argsBefore.length + argsAfter.length);
        System.arraycopy(argsAfter, 0, args, argsBefore.length, argsAfter.length);
        return this.update(sql, args);
    }

    /**
     * 根据主键批量更新
     * @param entityList
     * @throws MyJdbcTempletAnnotationException
     */
    public void batchUpdateById(List<?> entityList)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(entityList, "Param entityList cannot be empty!");
        final Class<?> c = entityList.get(0).getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns!");
        }
        List<Field> updateableFieldList = entityClassInfo.getUpdateableFieldList();
        if(updateableFieldList == null || updateableFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no insertable field!");
        }
        String sql = EntityClassInfoManager.getUpdateSqlHead(tableName, updateableFieldList) +  EntityClassInfoManager.getWhereAfterSql(idFieldList);
        logger.info(sql);
        logListTotle("update", entityList.size());
        this.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                logOperateDataNum(i);
                setObjectValuesToPrepareStatement(entityList.get(i), updateableFieldList, getMethodMap, preparedStatement, false);
                setObjectValuesToPrepareStatement(entityList.get(i), idFieldList, getMethodMap, preparedStatement, true);
            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }
        });
    }

    /**
     * 全表更新指定字段
     * @param entity
     * @param fields
     * @return
     * @throws MyJdbcTempletAnnotationException
     */
    public int updateFieldsAllData(String[] fields, Object entity)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(fields, "Param fields cannot be empty!");
        Assert.notNull(entity, "Param entity cannot be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Field> updateableFieldMap = entityClassInfo.getUpdateableFieldMap();
        List<Field> fieldLsit = new ArrayList<>();
        for(String field : fields){
            Field f = updateableFieldMap.get(field);
            if(f == null){
                throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " filed " + field + " cannot update!");
            }else{
                fieldLsit.add(f);
            }
        }
        String sql = EntityClassInfoManager.getUpdateSqlHead(tableName, fieldLsit);
        logger.info(sql);
        Object argsBefore[] = getObjectValue(entity, fieldLsit, getMethodMap, false);
        Object argsAfter[] = getObjectValue(entity, fieldLsit, getMethodMap, true);
        Object args[] = Arrays.copyOf(argsBefore, argsBefore.length + argsAfter.length);
        System.arraycopy(argsAfter, 0, args, argsBefore.length, argsAfter.length);
        return this.update(sql, args);
    }

    /**
     * 根据主键更新指定字段
     * @param entity
     * @param fields
     * @return
     * @throws MyJdbcTempletAnnotationException
     */
    public int updateFieldsById(String[] fields, Object entity)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(fields, "Param fields cannot be empty!");
        Assert.notNull(entity, "Param entity cannot be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Field> updateableFieldMap = entityClassInfo.getUpdateableFieldMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns!");
        }
        List<Field> fieldLsit = new ArrayList<>();
        for(String field : fields){
            Field f = updateableFieldMap.get(field);
            if(f == null){
                throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " filed " + field + " cannot update!");
            }else{
                fieldLsit.add(f);
            }
        }
        String sql = EntityClassInfoManager.getUpdateSqlHead(tableName, fieldLsit) +  EntityClassInfoManager.getWhereAfterSql(idFieldList);
        logger.info(sql);
        Object argsBefore[] = getObjectValue(entity, fieldLsit, getMethodMap, false);
        Object argsAfter[] = getObjectValue(entity, fieldLsit, getMethodMap, true);
        Object args[] = Arrays.copyOf(argsBefore, argsBefore.length + argsAfter.length);
        System.arraycopy(argsAfter, 0, args, argsBefore.length, argsAfter.length);
        return this.update(sql, args);
    }

    /**
     * 根据主键批量更新指定字段
     * @param fields
     * @param entityList
     * @throws MyJdbcTempletAnnotationException
     */
    public void batchUpdateFieldsById(String[] fields, List<?> entityList)throws MyJdbcTempletAnnotationException{
        Assert.notEmpty(fields, "Param fields cannot be empty!");
        Assert.notEmpty(entityList, "Param entityList cannot be empty!");
        final Class<?> c = entityList.get(0).getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Field> updateableFieldMap = entityClassInfo.getUpdateableFieldMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " have no id columns!");
        }
        List<Field> fieldLsit = new ArrayList<>();
        for(String field : fields){
            Field f = updateableFieldMap.get(field);
            if(f == null){
                throw new MyJdbcTempletAnnotationException("Entity " + c.getName() + " filed " + field + " cannot update!");
            }else{
                fieldLsit.add(f);
            }
        }
        String sql = EntityClassInfoManager.getDeleteSqlHead(tableName) +  EntityClassInfoManager.getWhereAfterSql(fieldLsit);
        logger.info(sql);
        logListTotle("update", entityList.size());
        this.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                logOperateDataNum(i);
                for(int j = 0; j < fieldLsit.size(); j++) {
                    Field field = fieldLsit.get(j);
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

    /**
     * 根据SQL更新
     * @param sql
     * @param args
     * @return
     * @throws MyJdbcTempletAnnotationException
     */
    public int updateBySQL(String sql, Object[] args)throws MyJdbcTempletAnnotationException{
        Assert.notNull(sql, "Param sql cannot be null!");
        logger.info(sql);
        return this.update(sql, args);
    }

    public <T> T queryById(Object entity, Class<T> entityClass){
        Assert.notNull(entity, "Param entity cannot be null!");
        Assert.notNull(entityClass, "Param entityClass cannot be null!");
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + entityClass.getName() + " have no id columns!");
        }
        List<Field> allDBFieldList = entityClassInfo.getAllDBFieldList();
        if(allDBFieldList == null || allDBFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + entityClass.getName() + " have no Column annotation!");
        }
        String sql = EntityClassInfoManager.getSelectSqlHead(tableName, allDBFieldList) + EntityClassInfoManager.getWhereAfterSql(idFieldList);
        logger.info(sql);
        Object[] args = getObjectValue(entity, idFieldList, getMethodMap, true);
        List<T> list = this.query(sql, args, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                return EntityClassInfoManager.setValueToEntityFromResultSet(entityClass, resultSet, allDBFieldList, setMethodMap);
            }
        });
        if(list == null || list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    public <T> T queryBySingleId(Object id, Class<T> entityClass){
        Assert.notNull(id, "Param id cannot be null!");
        Assert.notNull(entityClass, "Param entityClass cannot be null!");
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        List<Field> idFieldList = entityClassInfo.getIdFieldList();
        if(idFieldList == null || idFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + entityClass.getName() + " have no id columns!");
        }
        if(idFieldList.size() != 1){
            throw new MyJdbcTempletAnnotationException("Entity " + entityClass.getName() + " have more than one id columns!");
        }
        List<Field> allDBFieldList = entityClassInfo.getAllDBFieldList();
        if(allDBFieldList == null || allDBFieldList.isEmpty()){
            throw new MyJdbcTempletAnnotationException("Entity " + entityClass.getName() + " have no Column annotation!");
        }
        String sql = EntityClassInfoManager.getSelectSqlHead(tableName, allDBFieldList) + EntityClassInfoManager.getWhereAfterSql(idFieldList);
        logger.info(sql);
        Object[] args = new Object[]{id};
        logFieldValue(idFieldList.get(0).getAnnotation(Column.class).value().toUpperCase(), id);
        List<T> list = this.query(sql, args, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                return EntityClassInfoManager.setValueToEntityFromResultSet(entityClass, resultSet, allDBFieldList, setMethodMap);
            }
        });
        if(list == null || list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    /**
     * 根据条件查询数据总数
     * @param condition
     * @param args
     * @param entityClass
     * @return
     */
    public int getCountByCondition(String condition, Object[] args, Class<?> entityClass){
        Assert.notNull(entityClass, "Param entityClass cannot be null!");
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        StringBuffer sql = new StringBuffer("SELECT (1) FROM `");
        sql.append(tableName).append("` WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(condition)){
            sql.append(" AND ").append(condition);
        }
        logger.info(sql.toString());
        return this.queryForObject(sql.toString(), args, Integer.TYPE);
    }

    /**
     * 根据指定字段获取数据总数
     * @param fields
     * @param entity
     * @return
     */
    public int getCountByFields(String[] fields, Object entity){
        Class<?> entityClass = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM `");
        sql.append(tableName).append("` WHERE 1 = 1 ");
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Field> allDBFieldMap = entityClassInfo.getAllDBFieldMap();
        List<Field> fieldList = new ArrayList<>();
        for(String field : fields){
            Field f = allDBFieldMap.get(field);
            if(f == null){
                throw new MyJdbcTempletAnnotationException("Filed " + field + " have no Column annotation cannot trans to database field!");
            }else{
                fieldList.add(f);
            }
        }
        sql.append(EntityClassInfoManager.getWhereAfterSql(fieldList));
        logger.info(sql.toString());
        Object[] args = getObjectValue(entity, fieldList, getMethodMap, false);
        return this.queryForObject(sql.toString(), args, Integer.TYPE);
    }

    /**
     * 获取表全部数据总数
     * @param entityClass
     * @return
     */
    public int getCountAllData(Class<?> entityClass){
        Assert.notNull(entityClass, "Param entityClass cannot be null!");
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM `");
        sql.append(tableName).append("` WHERE 1 = 1 ");
        logger.info(sql.toString());
        return this.queryForObject(sql.toString(),Integer.TYPE);
    }

    /**
     * 根据条件查询数据
     * @param condition
     * @param args
     * @param entityClass
     * @param page
     * @param size
     * @param <T>
     * @return
     */
    public<T> List<T> queryByCondition(String condition, Object[] args, Class<T> entityClass, int page, int size){
        Assert.notNull(entityClass, "Param entityClass cannot be null!");
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        List<Field> allDBFieldList = entityClassInfo.getAllDBFieldList();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        StringBuffer sqlbuff = new StringBuffer(EntityClassInfoManager.getSelectSqlHead(tableName, allDBFieldList));
        if(StringUtils.isNotBlank(condition)){
            sqlbuff.append(" AND ").append(condition);
        }
        if(page > -1 && size > -2){
            sqlbuff.append(" LIMIT ").append(page * size).append(",").append(size);
        }else if(page < 0 && size > 0){
            sqlbuff.append(" LIMIT ").append(size);
        }
        String sql = sqlbuff.toString();
        logger.info(sql);
        return this.query(sql, args, new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                return EntityClassInfoManager.setValueToEntityFromResultSet(entityClass, resultSet, allDBFieldList, setMethodMap);
            }
        });
    }

    /**
     * 根据条件查询数据
     * @param condition
     * @param args
     * @param entityClass
     * @param size
     * @param <T>
     * @return
     */
    public<T> List<T> queryByCondition(String condition, Object[] args, Class<T> entityClass, int size){
        return this.queryByCondition(condition, args, entityClass, -1, size);
    }

    /**
     * 根据指定字段获取数据
     * @param fields
     * @param entity
     * @return
     */
    public<T> List<T> queryByFields(String[] fields, Object entity, Class<T> entityClass, int page, int size, String orderBy){
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        Map<String, Field> allDBFieldMap = entityClassInfo.getAllDBFieldMap();
        List<Field> fieldList = new ArrayList<>();
        for(String field : fields){
            Field f = allDBFieldMap.get(field);
            if(f == null){
                throw new MyJdbcTempletAnnotationException("Filed " + field + " have no Column annotation cannot trans to database field!");
            }else{
                fieldList.add(f);
            }
        }
        StringBuffer sqlbuff = new StringBuffer();
        sqlbuff.append(EntityClassInfoManager.getSelectSqlHead(tableName, fieldList));
        sqlbuff.append(EntityClassInfoManager.getWhereAfterSql(fieldList));
        if(StringUtils.isNotBlank(orderBy)){
            sqlbuff.append(" ORDER BY ").append(orderBy);
        }
        if(page > -1 && size > -1){
            sqlbuff.append(" LIMIT ").append(page * size).append(",").append(size);
        }
        String sql = sqlbuff.toString();
        logger.info(sql.toString());
        Object[] args = getObjectValue(entity, fieldList, getMethodMap, false);
        return this.query(sql, args, new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                return EntityClassInfoManager.setValueToEntityFromResultSet(entityClass, resultSet, fieldList, setMethodMap);
            }
        });
    }

    /**
     * 获取表全部数据
     * @param entityClass
     * @return
     */
    public<T> List<T> queryAllData(Class<T> entityClass, int page, int size, String orderBy){
        Assert.notNull(entityClass, "Param entityClass cannot be null!");
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(entityClass);
        String tableName = entityClassInfo.getTableName();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        List<Field> allDBFieldList = entityClassInfo.getAllDBFieldList();
        StringBuffer sqlbuff = new StringBuffer(EntityClassInfoManager.getSelectSqlHead(tableName, allDBFieldList));
        if(StringUtils.isNotBlank(orderBy)){
            sqlbuff.append(" ORDER BY ").append(orderBy);
        }
        if(page > -1 && size > -2){
            sqlbuff.append(" LIMIT ").append(page * size).append(",").append(size);
        }else if(page < 0 && size > 0){
            sqlbuff.append(" LIMIT ").append(size);
        }
        String sql = sqlbuff.toString();
        logger.info(sql);
        return this.query(sql, new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                return EntityClassInfoManager.setValueToEntityFromResultSet(entityClass, resultSet, allDBFieldList, setMethodMap);
            }
        });
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

    /**
     * 获取obj指定字段的值
     * @param obj
     * @param fieldList
     * @param getMethodMap
     * @param checkNull
     * @return
     */
    public Object[] getObjectValue(Object obj, List<Field> fieldList, Map<String, Method> getMethodMap, boolean checkNull){
        Object[] values = new Object[fieldList.size()];
        if(checkNull) {
            for (int i = 0; i < fieldList.size(); i++) {
                Field field = fieldList.get(i);
                Method getMethod = getMethodMap.get(field.getName());
                if (getMethod == null) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " field " + field.getName() + " have no get method!");
                }
                try {
                    values[i] = getMethod.invoke(obj);
                } catch (Exception e) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " set value to PrepareStatement for insert have an error!", e);
                }
                if (values[i] == null) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " field " + field.getName() + " value cannot be null!");
                }
                logFieldValue(field.getAnnotation(Column.class).value().toUpperCase(), values[i]);
            }
        }else {
            for (int i = 0; i < fieldList.size(); i++) {
                Field field = fieldList.get(i);
                Method getMethod = getMethodMap.get(field.getName());
                if (getMethod == null) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " field " + field.getName() + " have no get method!");
                }
                try {
                    values[i] = getMethod.invoke(obj);
                } catch (Exception e) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " set value to PrepareStatement for insert have an error!", e);
                }
                logFieldValue(field.getName(), values[i]);
            }
        }
        return values;
    }

    /**
     * 将obj指定字段的值赋给prepareStatement
     * @param obj
     * @param fieldList
     * @param getMethodMap
     * @param preparedStatement
     * @param checkNull
     */
    public void setObjectValuesToPrepareStatement(Object obj, List<Field> fieldList, Map<String, Method> getMethodMap, PreparedStatement preparedStatement, boolean checkNull){
        if(checkNull){
            for(int j = 0; j < fieldList.size(); j++) {
                Field field = fieldList.get(j);
                Method getMethod = getMethodMap.get(field.getName());
                if (getMethod == null) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " field " + field.getName() + " have no get method!");
                }
                try {
                    Object value = getMethod.invoke(obj);
                    if(value == null){
                        throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " field " + field.getName() + " value cannot be null!");
                    }
                    EntityClassInfoManager.setPara(preparedStatement, value, j + 1);
                    logFieldValue(field.getName(), value);
                } catch (Exception e) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " set value to PrepareStatement have an error!", e);
                }
            }
        }else{
            for(int j = 0; j < fieldList.size(); j++) {
                Field field = fieldList.get(j);
                Method getMethod = getMethodMap.get(field.getName());
                if (getMethod == null) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " field " + field.getName() + " have no get method!");
                }
                try {
                    Object value = getMethod.invoke(obj);
                    EntityClassInfoManager.setPara(preparedStatement, value, j + 1);
                    logFieldValue(field.getName(), value);
                } catch (Exception e) {
                    throw new MyJdbcTempletAnnotationException("Entity " + field.getDeclaringClass().getName() + " set value to PrepareStatement have an error!", e);
                }
            }
        }
    }
}
