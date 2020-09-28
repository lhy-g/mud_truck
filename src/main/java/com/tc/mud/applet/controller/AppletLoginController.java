package com.tc.mud.applet.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tc.core.annotation.OpenMapping;
import com.tc.core.utils.MyFileUtils;
import com.tc.core.utils.UserContextHolder;
import com.tc.core.web.responce.JsCode2Session;
import com.tc.core.web.responce.R;
import com.tc.core.web.responce.SynInfoFormDTO;
import com.tc.core.web.responce.SynInfoVO;
import com.tc.core.web.responce.UserVO;
import com.tc.mud.applet.service.LoginService;
 

@Slf4j
@RestController
@Api(tags = "1.0小程序登录")
@RequestMapping("/applet")
@SuppressWarnings("unchecked")
public class AppletLoginController {
	
	@Autowired
    private LoginService loginService;
	 
	@OpenMapping
	@ApiOperation("获取微信sessionKey")
	@ApiOperationSupport(order = 1)
	@RequestMapping(value="/getSession/{jsCode}",method = RequestMethod.GET)
    public R<JsCode2Session> getJscode2session(
    		@PathVariable String jsCode
    		//@ApiParam(value = "jsCode") @RequestParam String jsCode
    		){
        return R.success().setData(loginService.getJscode2session(jsCode));
    }
	
    @OpenMapping
    @ApiOperation("登录||注册")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public R<SynInfoVO> synInfo(@RequestBody SynInfoFormDTO synInfoFormDto) {
    	try {
    		return R.success().setData(loginService.synInfo(synInfoFormDto));
		} catch (Exception e) {
			log.error("同步用户信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
    
    @ApiOperation("获取用户当前信息")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value="/get_user_info",method = RequestMethod.GET)
    public R<UserVO> findCurrentInfo() {
    	try {
			return R.success().setData(UserContextHolder.get());
		} catch (Exception e) {
			e.printStackTrace();
			return R.fail().setMsg("请求失败!");
		}
    	
    }
    
    @OpenMapping
    @ApiOperation("上传文件")
    @ApiOperationSupport(order = 501)
	@RequestMapping(value="/file/upload",method = RequestMethod.POST)
	public R<String> uploadMusicFile(HttpServletRequest request, @ApiParam("文件") MultipartFile file,
			@ApiParam(name = "type", value = "上传类型(mu:泥票)") @RequestParam(required = false)String type) {
		long fileSize = file.getSize() / 1024 / 1024;
		if("mu".equals(type)) {
			String name = file.getOriginalFilename();
			if (!name.endsWith("jpg")) {
				return R.fail().setMsg("目前只支持JPG格式图片!");
			}
		}
    	if(fileSize>5) {
    		return R.fail().setMsg("文件大小不得大于5M");
    	}
    	return R.success().setData(MyFileUtils.fileUpload(file));
    	//return R.success().setData("https://p.3p3.top/upload/1593750942.jpg");
	}

	 
    
}
