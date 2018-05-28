package boss.portal.web.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boss.portal.service.RadioService;
import boss.portal.util.MsgCode;
import boss.portal.util.Result;

/**
 * 音频管理接口
 * @author YBK
 * @Date 2018-05-28 19:21
 */

@RestController
@RequestMapping("/radio")
public class RadiosController {
	
	@Autowired
	private RadioService radioService;
	
	@RequestMapping(value = "/list" ,method = RequestMethod.POST)
	public Result<Map<String,List<String>>> getList(){
		Result<Map<String,List<String>>> result = new Result<>();
		result.setCode(MsgCode.SUCCESS.getCode());
		result.setMsg(MsgCode.SUCCESS.getDescription());
		result.setData(radioService.getRadios());
		return result;
	}
}
