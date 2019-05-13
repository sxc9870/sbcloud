package com.sbcloud;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sbcloud.common.utils.DateUtils;
import com.sbcloud.pojo.KLinePojo;
import com.sbcloud.pojo.StockPojo;
import com.show.api.ShowApiRequest;

@Component
public class After implements CommandLineRunner {

	@Autowired
	private com.sbcloud.dao.KLineRepository KLineRepository;
	@Autowired
	private com.sbcloud.dao.StockRepository stockRepository;

	@Override
	public void run(String... args) throws Exception {
//		stockRepository.findAll().forEach(e -> {
//			if (e.getIsComp() == 1) {
//				return;
//			}
//			try {
//				boolean b = a(e.getCode());
//				if (!b) {
//					stockRepository.delete(e);
//					return;
//				}
//				e.setIsComp((byte) 1);
//				stockRepository.save(e);
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//		});

	}

	public void b() {
		int i = 300730;

		while (i <= 300760) {
			StockPojo s = new StockPojo();
			s.setIsComp((byte) 0);
			s.setCode(i + "");
			i++;
			stockRepository.save(s);
		}
	}

	public boolean a(String code) throws ParseException {
		String id = "94901";
		String key = "b92d69fc91e743179be190da6541f8c5";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int i = 0;
		int j = 1;
		Date pre = DateUtils.addMonth(sdf.parse("2018-01-01"), 0);
		Date after = DateUtils.getLastDateByMonth(pre);

		while (true) {
			String res = new ShowApiRequest("http://route.showapi.com/1529-2", id, key)
					.addTextPara("begin", sdf.format(pre)).addTextPara("end", sdf.format(after))
					.addTextPara("code", code).post();
			System.out.println(res);
			if (res.contains("找不到此股票")) {
				return false;
			}
			JSONObject obb = JSONObject.parseObject(res);
			JSONArray aa = obb.getJSONObject("showapi_res_body").getJSONArray("list");
			List<KLinePojo> kline = aa.toJavaList(KLinePojo.class);
			kline.sort((a, b) -> a.getDate().compareTo(b.getDate()));
			for (KLinePojo k : kline) {
				KLineRepository.save(k);
			}
			pre = DateUtils.addMonth(pre, 1);
			after = DateUtils.getLastDateByMonth(pre);
			if (after.after(new Date())) {
				break;
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(DateUtils.addMonth(sdf.parse("2018-01-01"), 13));

	}

}
