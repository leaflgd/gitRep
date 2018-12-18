package org.zixing.service.imp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixing.dao.SubmitbDao;
import org.zixing.dto.SubmitbQuery;
import org.zixing.service.SubmitbService;


@Service("submitbServiceImp")
public class SubmitbServiceImp implements SubmitbService {

	@Autowired
	private SubmitbDao sd;
	
	
	public void SubmitbDispose() {
		System.out.println("查询数据库");
		//查询数据库中的操作完毕的数据
		List<SubmitbQuery> list=sd.submitbQuery();
		Iterator<SubmitbQuery> i = list.iterator();
		for (SubmitbQuery submitbQuery : list) {
			System.out.println(submitbQuery);
		}
		while(i.hasNext()){
			SubmitbQuery sq = i.next();
			String id=sq.getDialPieceId();
			int status = sq.getStatus();
			if(status==3){
				System.out.println("处理异常"+id);
				//小图删除
				System.out.println("小图删除");
				sd.chromDelete(id);
				//状态回滚
//				System.out.println("状态回滚删除");
				sd.grayStateRoll(id);
				System.out.println("处理完毕"+id);
//				sd.submitbProcessStatus(id);
			}else if(status==2){
				System.out.println("处理正常"+id);
				//提交时间修改
				sd.submitbDate(id, new Date());
				sd.submitbDelete(id);
				//提交成功修改锁定
				sd.submitbStatusUpdate(id, 0);
				System.out.println("处理完毕"+id);
			}
			
		}
	}

}
