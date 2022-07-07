package com.example.myseckill;

import com.example.myseckill.dto.Goods;
import com.example.myseckill.mapper.GoodsMapper;
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
public class GoodsMapperTest {


    @Test
    public void selectAll() throws IOException {

        // Get SqlSession through factory
        SqlSession sqlSession = this.getSqlSessionFactory().openSession();

        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        List<Goods> list = goodsMapper.selectAll();
        assert(goodsMapper.selectAll().isEmpty() == false);

        // Release resources
        sqlSession.close();
    }
    @Test
    public void insertAndSelect() throws IOException {
        SqlSession sqlSession = this.getSqlSessionFactory().openSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goods = new Goods();
        goods.setGoods_name("iphone 13");
        goods.setGoods_price(7000.0);
        goods.setGoods_stock(9);
        int info = goodsMapper.insertGoods(goods);
        Assert.assertTrue(info == 1);//insert success

        //selectById
        Goods goods2 = goodsMapper.selectById(goods.getId());
        Assert.assertTrue(goods2.equals(goods));
        // Release resources
        sqlSession.close();
    }
    @Test
    public void updateStockById() throws IOException {
        SqlSession sqlSession = this.getSqlSessionFactory().openSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goods = new Goods();
        goods.setGoods_name("iphone 13");
        goods.setGoods_price(7000.0);
        goods.setGoods_stock(9);
        int info = goodsMapper.insertGoods(goods);
        Assert.assertTrue(info == 1);//insert success
        goodsMapper.reduceStockById(goods.getId(), 1);
        Assert.assertEquals(goods.getGoods_stock() -1 , (long)goodsMapper.selectById(goods.getId()).getGoods_stock());
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
