package com.tc.mud.applet.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.constant.ResEnum;
import com.tc.core.exception.BizException;
import com.tc.core.utils.DateUtils;
import com.tc.core.utils.JwtUtils;
import com.tc.core.web.responce.JsCode2Session;
import com.tc.core.web.responce.SynInfoFormDTO;
import com.tc.core.web.responce.SynInfoVO;
import com.tc.core.web.responce.UserVO;
import com.tc.core.web.responce.WxPhone;
import com.tc.mud.applet.constant.CommonConstant;
import com.tc.mud.applet.dto.UserInfo;
import com.tc.mud.applet.service.AppletUserService;
import com.tc.mud.applet.service.LoginService;

import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;

@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AppletUserService  userService;
	
	/***
	 * wx184132c59d008055
	 * e11f30789c36f2eb6507a0b4ee58a72e
	 * wxeebd23a615535d8d
	 * 6be36bdef78d2ef367752f43477deb2b
	 */
	@Override
	public JsCode2Session getJscode2session(String jsCode) {
		String url = String.format(
				"https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
				"wxeebd23a615535d8d", "6be36bdef78d2ef367752f43477deb2b", jsCode);
		String resStr = restTemplate.getForObject(url, String.class);
		JSONObject res = JSONObject.parseObject(resStr);
		JsCode2Session jsCode2Session = new JsCode2Session();
		jsCode2Session.setOpenId(res.getString("openid"));
		jsCode2Session.setSessionKey(res.getString("session_key"));
		jsCode2Session.setUnionId(res.getString("unionid"));
		jsCode2Session.setErrCode(res.getInteger("errcode"));
		jsCode2Session.setErrMsg(res.getString("errmsg"));
		return jsCode2Session;
	}

	@Override
	public SynInfoVO synInfo(SynInfoFormDTO synInfoFormDto) {
		String wxres = WxMaCryptUtils.decrypt(synInfoFormDto.getSessionKey(), synInfoFormDto.getEncryptedData(),synInfoFormDto.getIv());
		WxPhone wxPhone = JSONObject.parseObject(wxres, WxPhone.class);

		if (Objects.isNull(wxPhone)) {
			throw new BizException("未找到手机号").end(ResEnum.NOT_FOUND);
		}
		// UserInfo userInfo =userMapper.queryUserInfoByPhone(wxPhone.getPhoneNumber());
		// 默认为消费者
		UserInfo userInfo = userService.getUserInfoByUnionId(synInfoFormDto.getUnionId());
		if (null == userInfo) {
			userInfo = new UserInfo();
			userInfo.setUserId(DateUtils.getGuid());
			userInfo.setStatus("1");
			userInfo.setPhone(wxPhone.getPhoneNumber());
		}
		userInfo.setUnionId(synInfoFormDto.getUnionId());
		userInfo.setHeadUrl(synInfoFormDto.getHeadPortraitLink());
		userInfo.setNickname(synInfoFormDto.getNickname());
		userService.saveUserInfo(userInfo);
		UserVO userVo = new UserVO();
		BeanUtils.copyProperties(userInfo, userVo);
		userVo.setHeadPortraitLink(userInfo.getHeadUrl());
		return SynInfoVO.builder().token(JwtUtils.generateToken(userVo)).build();
	}
	
	 
	 

	

}
