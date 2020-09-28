package com.tc.mud.applet.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tc.core.web.responce.JsCode2Session;
import com.tc.core.web.responce.R;

import io.swagger.annotations.ApiOperation;

@RestController
public class TestController {

	
	@ApiOperation("获取微信sessionKey")
	@RequestMapping(value="/t",method = RequestMethod.GET)
    public R<JsCode2Session> getJscode2session(){
        return R.success().setData("hello");
    }
}
