package boss.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import boss.portal.service.RadioService;
import boss.portal.util.QCloudUtil;

@Service
public class RadioServiceImpl implements RadioService{

	
	@Override
	public Map<String, List<String>> getRadios() {
		
		return QCloudUtil.listBuckets();
	}
	
}
