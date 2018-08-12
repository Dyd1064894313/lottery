package top.duanyd.lottery.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;
import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;
import top.duanyd.lottery.annotation.Table;

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

    public MyJdbcTemplet(DataSource dataSource){
        super(dataSource);
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
    public void insert(Object entity)throws Exception{
        Assert.notNull(entity, "entity must not be null!");
        final Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Field> idFieldMap = entityClassInfo.getIdFieldMap();
        Field autoIncrementField = entityClassInfo.getAutoIncrementField();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        Map<String, Method> getMethodMap = entityClassInfo.getGetMethodMap();
        List<Field> insertableFieldList = entityClassInfo.getInsertableFieldList();
        String sql = getInsertSql(tableName, insertableFieldList);
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
                        logger.info("--\t\t" + field.getName() + ":\t\t" + value);
                    }catch (IllegalAccessException e) {
                        logger.error(e);
                    } catch (InvocationTargetException e) {
                        logger.error(e);
                    }finally {
                        throw new SQLException("Entity " + c.getName() + " set value to PrepareStatement for insert have an error!");
                    }
                }
                return preparedStatement;
            }
        }, keyHolder);
        if(autoIncrementField != null){
            //获取自增主键的值并赋给传入的entity
            Number key = keyHolder.getKey();
            EntityClassInfoManager.setNumberValue(entity, autoIncrementField, setMethodMap.get(autoIncrementField.getName()), key);
        }
    }

    private String getInsertSql(String tableName, List<Field> fieldList){

        StringBuffer head = new StringBuffer("INSERT INTO `").append(tableName).append("` (");
        StringBuffer tail = new StringBuffer(" VALUES (");
        Field field = fieldList.get(0);
        head.append(field.getAnnotation(Column.class).value());
        tail.append("?");
        for(int i = 1; i < fieldList.size(); i++){
            field = fieldList.get(i);
            head.append(", ").append(field.getAnnotation(Column.class).value());
            tail.append(", ?");
        }
        head.append(")");
        tail.append(")");
        return head.append(tail).toString();
    }

}
