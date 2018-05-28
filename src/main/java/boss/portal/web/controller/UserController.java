package boss.portal.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boss.portal.entity.User;
import boss.portal.exception.UsernameIsExitedException;
import boss.portal.repository.UserRepository;
import boss.portal.util.JsonFormat;
import boss.portal.util.MsgCode;
import boss.portal.util.Result;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{id}")
    public Result<User> getUser(@PathVariable long id) {
    	Result<User> result = new Result<>();
    	User user = userRepository.findOne(id);
    	if(null != user) {
//    		return JsonFormat.formatResult(MsgCode.SUCCESS.getCode(), MsgCode.SUCCESS.getDescription(), user);
    		result.setCode(MsgCode.SUCCESS.getCode());
    		result.setMsg(MsgCode.SUCCESS.getDescription());
    		result.setData(user);
//    		return new ResponseEntity<User>(HttpStatus.OK);
    	}else {
//    		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    		result.setCode(MsgCode.SUCCESS.getCode());
    		result.setMsg(MsgCode.FAILED.getDescription());
    	}
    	return result;
    }
    
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/userList")
    public Map<String, Object> userList(){
        List<User> users = userRepository.findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("users",users);
        return map;
    }

    /**
     * 注册用户 默认开启白名单
     * @param user
     */
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        User bizUser = userRepository.findByUsername(user.getUsername());
        if(null != bizUser){
            throw new UsernameIsExitedException("用户已经存在");
        }
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));
        return userRepository.save(user);
    }

}
