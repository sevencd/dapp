package com.crossoverJie.seconds.kill.service.impl;

import com.crossoverJie.seconds.kill.api.constant.RedisKeysConstant;
import com.crossoverJie.seconds.kill.dao.StockMapper;
import com.crossoverJie.seconds.kill.pojo.Stock;
import com.crossoverJie.seconds.kill.service.StockService;
import com.crossoverJie.seconds.kill.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 30/04/2018 22:39
 * @since JDK 1.8
 */
@Service("DBStockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;
    /*    @Autowired
        private RedisUtil redisUtil;
       */
    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    @PostConstruct
    public void initMethod() {
        //初始化库存
        int sid = 1;
        Stock stock = this.getStockById(sid);
        redisTemplate.opsForValue().set(RedisKeysConstant.STOCK_COUNT + sid, stock.getCount().toString());
        redisTemplate.opsForValue().set(RedisKeysConstant.STOCK_SALE + sid, stock.getSale().toString());
        redisTemplate.opsForValue().set(RedisKeysConstant.STOCK_VERSION+sid,stock.getVersion().toString());
        redisTemplate.opsForValue().set(RedisKeysConstant.STOCK_NAME+sid,stock.getName());
    }

    @Override
    public int getStockCount(int id) {
        Stock ssmStock = stockMapper.selectByPrimaryKey(id);
        return ssmStock.getCount();
    }

    @Override
    public Stock getStockById(int id) {
        return stockMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStockById(Stock stock) {
        return stockMapper.updateByPrimaryKeySelective(stock);
    }

    @Override
    public int updateStockByOptimistic(Stock stock) {
        return stockMapper.updateByOptimistic(stock);
    }
}
