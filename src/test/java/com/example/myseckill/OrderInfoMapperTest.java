package com.example.myseckill;

import com.example.myseckill.dto.OrderInfo;
import com.example.myseckill.mapper.OrderInfoMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderInfoMapperTest {


    @Test
    public void selectAll() throws IOException {

        // Get SqlSession through factory
        SqlSession sqlSession = this.getSqlSessionFactory().openSession();

        OrderInfoMapper orderInfoMapper = sqlSession.getMapper(OrderInfoMapper.class);
        List<OrderInfo> list = orderInfoMapper.selectAll();
        assert(orderInfoMapper.selectAll().isEmpty() == false);

        // Release resources
        sqlSession.close();
    }
    @Test
    public void insertAndSelect() throws IOException {
        SqlSession sqlSession = this.getSqlSessionFactory().openSession();
        OrderInfoMapper orderInfoMapper = sqlSession.getMapper(OrderInfoMapper.class);
        OrderInfo orderInfo = new OrderInfo();
        //user_id,goods_id,goods_name,goods_count,goods_price
        orderInfo.setUser_id((long)1);
        orderInfo.setGoods_id((long)1);
        orderInfo.setGoods_name("iphone  13");
        orderInfo.setGoods_count(2);
        orderInfo.setGoods_price(7300.0);
        int info = orderInfoMapper.insertOrderInfo(orderInfo);
        Assert.assertTrue(info == 1);//insert success

        //selectById
        OrderInfo orderInfo2 = orderInfoMapper.selectById(orderInfo.getId());
        Assert.assertTrue(orderInfo2.equals(orderInfo));
        // Release resources
        sqlSession.close();
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        // mybatis configuration file. The root address of this location is: resources. The path should be correct.
        String resource = "mybatis-config.xml";
        // Get profile stream
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // Create a session factory and pass in the profile information of mybatis
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}
