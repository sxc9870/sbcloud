package com.sbcloud.hystrix;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class MyHystrixCommanMode extends HystrixCommand<Object> {

	private final RestTemplate restTemplate;

	private String id;

	public MyHystrixCommanMode(String id, RestTemplate restTemplate) {
		//原理 通过线程池隔离请求
		 // java代码配置， 只针对这个命令
        super(Setter
                // 必填项。指定命令分组名，主要意义是用于统计
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("DnHello-Group"))
                // 依赖名称（如果是服务调用，这里就写具体的接口名， 如果是自定义的操作，就自己命令），默认是command实现类的类名。 熔断配置就是根据这个名称
                .andCommandKey(HystrixCommandKey.Factory.asKey("HystrixController"))
                // 线程池命名，默认是HystrixCommandGroupKey的名称。 线程池配置就是根据这个名称
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("DnUser-ThreadPool"))
                // command 熔断相关参数配置
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                // 配置隔离方式：默认采用线程池隔离。还有一种信号量隔离方式,
                                // .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                // 超时时间500毫秒 服务端超过时间未响应 则客户端自动降级
                                .withExecutionTimeoutInMilliseconds(100)
                        // 信号量隔离的模式下，最大的请求数。和线程池大小的意义一样
                        // .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
                        // 熔断时间（熔断开启后，各5秒后进入半开启状态，试探是否恢复正常）
                        // .withCircuitBreakerSleepWindowInMilliseconds(5000)
                )
                // 设置线程池参数
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        // 线程池大小  监控的线程池如果超过范围则降级
                        .withCoreSize(1)
                        //允许最大的缓冲区大小
                    //  .withMaxQueueSize(0)
                ));
        // super(HystrixCommandGroupKey.Factory.asKey("DnUser-command"),100);
        this.id = id;
        this.restTemplate = restTemplate;
	}

	  @Override
	    protected Object run() throws Exception {
	        System.out.println("###command#######" + Thread.currentThread().toString());
	    	Object o = restTemplate.getForObject("http://eurekaclient/getPort2", Object.class);
	        System.out.println(
	                "###command结束#######" + Thread.currentThread().toString() + ">><>>>执行结果:" + o.toString());
	        return o;
	    }

	    @Override
	    protected Object getFallback() {
	        System.out.println("###降级啦####" + Thread.currentThread().toString());
	        return "出錯了，我降級了";
	        //降级的处理：
	        //1.返回一个固定的值
	        //2.去查询缓存
	        //3.调用一个备用接口
	    }

}
