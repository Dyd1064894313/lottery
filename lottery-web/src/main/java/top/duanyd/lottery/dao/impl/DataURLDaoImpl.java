package top.duanyd.lottery.dao.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import top.duanyd.lottery.dao.interfaces.IDataURLDao;
import top.duanyd.lottery.entity.DataURLEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/8/8.
 */
@Component
public class DataURLDaoImpl implements IDataURLDao {
    private static Logger logger = LoggerFactory.getLogger(DataURLDaoImpl.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<DataURLEntity> getAllDataURL() {
        String sql = "select * from lottery_data_url";
        List<DataURLEntity> list = jdbcTemplate.query(sql, new RowMapper<DataURLEntity>() {
            @Override
            public DataURLEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                DataURLEntity dataURLEntity = new DataURLEntity();
                dataURLEntity.setId(resultSet.getLong("id"));
                dataURLEntity.setLotteryCode(resultSet.getString("lottery_code"));
                dataURLEntity.setUrl(resultSet.getString("url"));
                dataURLEntity.setParam(resultSet.getString("param"));
                dataURLEntity.setStatus(resultSet.getInt("status"));
                dataURLEntity.setCreateTime(resultSet.getDate("create_time"));
                dataURLEntity.setUpdateTime(resultSet.getDate("update_time"));
                dataURLEntity.setRemark(resultSet.getString("remark"));
//                dataURLEntity.setId2(resultSet.getLong("id2"));
                return dataURLEntity;
            }
        });
        return list;
    }

//    @Override
//    public Long insert(DataURLEntity dataURLEntity){
//        final String sql = "insert into lottery_data_url (lottery_code, url, param, status, create_time, update_time, remark, id2)" +
//                "values(?, ?, ?, ?, ?, ?, ?, ?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//                statement.setString(1, dataURLEntity.getLotteryCode());
//                statement.setString(2, dataURLEntity.getUrl());
//                statement.setString(3, dataURLEntity.getParam());
//                statement.setInt(4, dataURLEntity.getStatus());
//                statement.setDate(5, dataURLEntity.getCreateTime());
//                statement.setDate(6, dataURLEntity.getUpdateTime());
//                statement.setString(7, dataURLEntity.getRemark());
//                statement.setLong(8, System.currentTimeMillis());
//                return statement;
//            }
//        }, keyHolder);
//        logger.info(JSONObject.toJSONString(keyHolder.getKeyList()));
//        return null;
//    }

    @Override
    public Long insert(DataURLEntity entity){
        final String sql = "insert into test1 (q, w, e)" +
                "values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                Random random = new Random();
                statement.setInt(1, 2);
                statement.setInt(2, 2);
                statement.setInt(3, 3);
                return statement;
            }
        }, keyHolder);
        logger.info(JSONObject.toJSONString(keyHolder.getKeyList()));
        return null;
    }
}
