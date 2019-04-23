package com.sbcloud.hystrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapser.Setter;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.sbcloud.controller.HystrixController;

//请求合并
public class TestCollasperCommand extends HystrixCollapser<Map<String, List<String>>, List<String>, String> {

	private String requestArg;
	private HystrixController HystrixController;

	public TestCollasperCommand(String arg, HystrixController controller) {
		super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("TestCollasperCommand"))

				.andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
						// X毫秒内请求合并成一个
						.withTimerDelayInMilliseconds(1000)
						// X毫秒内最大200个请求合并到一起
						.withMaxRequestsInBatch(200)).andScope(Scope.GLOBAL));
		this.requestArg = arg;
		this.HystrixController = controller;
	}

	@Override
	public String getRequestArgument() {
		return requestArg;
	}

	/**
	 * 实际发起请求的命令
	 */
	@Override
	protected HystrixCommand<Map<String, List<String>>> createCommand(
			Collection<CollapsedRequest<List<String>,String>> requests) {
		// requests 为单位时间内所有请求的集合
		List<String> list = new ArrayList<>(requests.size());

		list.addAll(requests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));

		return new BatchCommand(list, HystrixController);
	}

	private static class BatchCommand extends HystrixCommand<Map<String, List<String>>> {

		private List<String> ids;
		private HystrixController hystrixController;

		protected BatchCommand(List<String> list, HystrixController controller) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BatchCommand"))
					.andCommandPropertiesDefaults(
							HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(100000)));
			this.ids = list;
			this.hystrixController = controller;
		}

		@Override
		protected Map<String, List<String>> run() throws Exception {
			return hystrixController.query(ids);
		}

	}

	@Override
	protected void mapResponseToRequests(Map<String, List<String>> batchResponse,
			Collection<CollapsedRequest<List<String>, String>> requests) {
		for (CollapsedRequest<List<String>, String> r : requests) {
			 r.setResponse(batchResponse.get( r.getArgument()));
		}
		
	}

	 

}
