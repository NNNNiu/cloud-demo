package cn.itcast.order.service;


import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;

    /**
     * feign方法
     */
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.用feign远程调用
        User user = userClient.findById(order.getUserId());
        // 3.数据封装
        order.setUser(user);
        // 4.返回
        return order;
    }
    //RestTemplate方法
    /*public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.跨服务调用
        String url = "http://user-service/user/"+order.getUserId();
        User user = restTemplate.getForObject(url, User.class);
        // 3.数据封装
        order.setUser(user);
        // 4.返回
        return order;
    }*/
}
