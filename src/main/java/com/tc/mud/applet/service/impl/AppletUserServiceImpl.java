package com.tc.mud.applet.service.impl;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc.core.exception.BaseException;
import com.tc.core.utils.MyKeyPariUtils;
import com.tc.core.utils.RedisUtil;
import com.tc.core.utils.UserContextHolder;
import com.tc.core.web.responce.SynInfoFormDTO;
import com.tc.core.web.responce.SynInfoVO;
import com.tc.mud.applet.dto.CompanyInfoDTO;
import com.tc.mud.applet.dto.MudYardHeadDTO;
import com.tc.mud.applet.dto.MudYardTailDTO;
import com.tc.mud.applet.dto.UserInfo;
import com.tc.mud.applet.mapper.AppletUserMapper;
import com.tc.mud.applet.service.AppletUserService;

import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;

@Service
public class AppletUserServiceImpl implements AppletUserService {

	@Autowired
	private AppletUserMapper userMapper;

	@Autowired
	private RedisUtil redisService;

	@Override
	public UserInfo getUserInfoByUnionId(String unionId) {
		UserInfo userInfo = userMapper.getUserInfoByUnionId(unionId);
		return userInfo;
	}

	@Override
	public String getUserPrivateKey(String userId) throws Exception {
		String privateKey = userMapper.getUserPrivateKey(userId);
		if (StringUtils.isEmpty(privateKey)) {
			// 为空时,给用户添加密钥对
			try {
				KeyPair keyPair = MyKeyPariUtils.getKeyPair();
				privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
				String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
				userMapper.addUserKeyPari(publicKey, privateKey, userId);
			} catch (Exception e) {
				throw new BaseException("获取用户签名信息失败,请稍后重试!!");
			}
		}
		return privateKey;
	}

	@Override
	public String getUserPublicKey(String userId) throws Exception {
		String publicKey = userMapper.getUserPublicKey(userId);
		if (StringUtils.isEmpty(publicKey)) {
			// 为空时,给用户添加密钥对
			try {
				KeyPair keyPair = MyKeyPariUtils.getKeyPair();
				String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
				publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
				userMapper.addUserKeyPari(publicKey, privateKey, userId);
			} catch (Exception e) {
				throw new BaseException("获取用户签名信息失败,请稍后重试!!");
			}
		}
		return publicKey;
	}

	@Override
	public void saveCompanyInfo(CompanyInfoDTO companyInfo) {
		userMapper.saveCompanyInfo(companyInfo);
		if("mud".equals(companyInfo.getType())) {
			userMapper.addCompanyBoss("mb",UserContextHolder.getUserId());
		}else {
			userMapper.addCompanyBoss("tb",UserContextHolder.getUserId());
		}
	}

	@Override
	public void addMemberApply(String companyId, String type, String userId) {
		userMapper.addMemberApply(companyId, type, userId);
	}

	@Override
	public IPage<Map<String, String>> searchCompany(Integer size, Integer current, String userId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> iPage = userMapper.searchCompany(page, userId);
		return iPage;
	}

	@Override
	public CompanyInfoDTO getCompanyInfoByCompanyId(String companyId) {

		return userMapper.getCompanyInfoByCompanyId(companyId);
	}

	@Override
	public IPage<Map<String, String>> queryCompanyMember(Integer size, Integer current, String status,String bossId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> iPage = userMapper.queryCompanyMember(page, status,bossId);
		return iPage;
	}

	@Override
	public void updateMemberStatusByUserId(String bossId, String memberId, String status) {
		userMapper.updateMemberStatusByUserId(bossId, memberId, status);
	}

	@Override
	public void saveUserInfo(UserInfo userInfo) {
		int count = userMapper.saveUserInfo(userInfo);
		if (count == 0) {
			throw new BaseException("更新用户信息错误,请重试!");
		}
	}

	@Override
	public IPage<CompanyInfoDTO> queryManageCompanyList(Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCompanyIdByUserId(String userId) {
		return userMapper.getCompanyIdByUserId(userId);
	}

	@Override
	public void saveMudYardHeadInfo(MudYardHeadDTO mudYardHeadDTO) {
		mudYardHeadDTO.setCompanyId(UserContextHolder.getCompanyId());
		userMapper.saveMudYardHeadInfo(mudYardHeadDTO);

	}

	@Override
	public void saveMudYardTailInfo(MudYardTailDTO mudYardTailDTO) {
		mudYardTailDTO.setCompanyId(UserContextHolder.getCompanyId());
		userMapper.saveMudYardTailInfo(mudYardTailDTO);
	}

	@Override
	public MudYardHeadDTO getMudYardHeadInfo(String yardId) {

		return userMapper.getMudYardHeadInfo(yardId);
	}

	@Override
	public MudYardTailDTO getMudYardTailInfo(String yardId) {
		return userMapper.getMudYardTailInfo(yardId);
	}

	@Override
	public IPage<Map<String, String>> queryMudYardHead(Integer size, Integer current, String companyId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> iPage = userMapper.queryMudYardHead(page, companyId);
		return iPage;
	}

	@Override
	public IPage<Map<String, String>> queryMudYardTail(Integer size, Integer current, String companyId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> iPage = userMapper.queryMudYardTail(page, companyId);
		return iPage;
	}

	@Override
	public int queryCountCompanyByUserId(String userId) {

		return userMapper.queryCountCompanyByUserId(userId);
	}

	@Override
	public List<Map<String, String>> queryUserTypeByUserId(String userId) {
		List<Map<String, String>> list = userMapper.queryUserTypeByUserId(userId);
		String str=null;
		for (Map<String,String> map:list) {
			 str= map.get("name");
		}
		if("mb".equals(str)) {
			List<Map<String, String>> listType = userMapper.queryBaseDataUserType("yardUserType");
			return listType;
		}
		if("tb".equals(str)) {
			List<Map<String, String>> listType = userMapper.queryBaseDataUserType("truckUserType");
			return listType;
		}
		return list;
	}

	@Override
	public IPage<Map<String, String>> queryAllMudYardHead(Integer size, Integer current, String name) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> iPage = userMapper.queryAllMudYardHead(page, name);
		return iPage;
	}
	@Override
	public IPage<Map<String, String>> queryAllMudYardTail(Integer size, Integer current, String name) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> iPage = userMapper.queryAllMudYardTail(page, name);
		return iPage;
	}

}
