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
        Class<?> c = entity.getClass();
        EntityClassInfo entityClassInfo = EntityClassInfoManager.getEntityClassInfo(c);
        String tableName = entityClassInfo.getTableName();
        Map<String, Field> idFieldMap = entityClassInfo.getIdFieldMap();
        Field autoIncrementField = entityClassInfo.getAutoIncrementField();
        Map<String, Method> setMethodMap = entityClassInfo.getSetMethodMap();
        if(autoIncrementField != null){
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    StringBuffer sql = new StringBuffer();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
                    return null;
                }
            }, keyHolder);
            //获取自增主键的值并赋给传入的entity
            Number key = keyHolder.getKey();
            EntityClassInfoManager.setNumberValue(entity, autoIncrementField, setMethodMap.get(autoIncrementField.getName()), key);
        }
    }

    private void setValue(Object obj, Field field, Method setMethod, Number number){


    }

}
