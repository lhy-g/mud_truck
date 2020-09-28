package com.tc.mud.applet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.core.exception.BaseException;
import com.tc.core.utils.DateUtils;
import com.tc.core.utils.RedisUtil;
import com.tc.core.utils.UserContextHolder;
import com.tc.core.web.responce.UserVO;
import com.tc.mud.applet.constant.CodeConstant;
import com.tc.mud.applet.dto.MudTicketAgentInfoDTO;
import com.tc.mud.applet.dto.MudTicketInfoDTO;
import com.tc.mud.applet.dto.MudTicketSellDTO;
import com.tc.mud.applet.dto.MudTruckApplyDTO;
import com.tc.mud.applet.dto.MudTruckInfoDTO;
import com.tc.mud.applet.dto.dto.MudHeadTranDTO;
import com.tc.mud.applet.dto.dto.MudTailInfoDTO;
import com.tc.mud.applet.dto.dto.MudTailTranDTO;
import com.tc.mud.applet.mapper.AppletUserMapper;
import com.tc.mud.applet.mapper.MudTicketMapper;
import com.tc.mud.applet.mapper.MudTicketsMapper;
import com.tc.mud.applet.service.CertService;
import com.tc.mud.applet.service.MudTicketService;
import com.tc.mud.applet.service.MudTicketsService;

import cn.hutool.json.JSONUtil;

@Service
public class MudTicketsServiceImpl implements MudTicketsService {
	@Autowired
	private MudTicketsMapper mudMapper;

	@Autowired
	private AppletUserMapper userMapper;

	@Autowired
	private CertService certService;

	@Autowired
	private RedisUtil redisService;

	@Override
	public void saveMudHeadInfo(MudTicketInfoDTO ticketInfo) {
		String filePath = ticketInfo.getImageNum();
		if (filePath.startsWith("https")) {
			filePath = filePath.substring(filePath.indexOf("upload/") + 7);
			ticketInfo.setImageNum(filePath);
		}
		ticketInfo.setBossId(UserContextHolder.getUserId());
		ticketInfo.setTimeStamp(DateUtils.getTimeStamp());
		try {
			String sign = certService.sign("泥场出售", UserContextHolder.getUserId());
			ticketInfo.setSign(sign);
		} catch (Exception e) {
			throw new BaseException("生成签字错误,请稍后再试");
		}
		mudMapper.saveMudHeadInfo(ticketInfo);
	}

	@Override
	public MudTicketInfoDTO getMudTicketInfo(String ticketId) {
		return mudMapper.getMudTicketInfoByTicketId(ticketId);
	}

	@Override
	public IPage<MudTicketInfoDTO> queryMudTicket(String userId, Integer size, Integer current) {
		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current, size);
		IPage<MudTicketInfoDTO> ipage = mudMapper.queryMudTicket(page, userId);
		return ipage;
	}

	@Override
	public IPage<MudTicketInfoDTO> queryMudTicketsByTruckCompanyId(Integer size, Integer current, String userId,
			String type) {
		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current, size);
		IPage<MudTicketInfoDTO> ipage = mudMapper.queryMudTicketsByTruckCompanyId(page, userId, type);
		return ipage;
	}

	@Override
	public void addMudTicketSellCompanyId(MudTicketSellDTO ticketSellDTO) {

		mudMapper.addMudTicketSellCompanyId(ticketSellDTO);
	}

	@Override
	public void addMudTicketsAgent(MudTicketAgentInfoDTO mudTicketAgent) {
		mudMapper.addMudTicketsAgent(mudTicketAgent);
	}

	@Override
	public IPage<MudTicketInfoDTO> queryMudTicketsAgent(Integer size, Integer current, String userId, String type) {
		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current, size);
		IPage<MudTicketInfoDTO> ipage = mudMapper.queryMudTicketsAgent(page, userId, type);
		return ipage;
	}

	@Override
	public void driverBuyMudTicket(MudTicketSellDTO mudTicketSellDTO) {

		mudMapper.addDriverBuyMudTicket(mudTicketSellDTO);
	}

	@Override
	public IPage<MudTicketInfoDTO> queryMudTicketByDriverId(Integer size, Integer current, String userId, String type) {
		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current, size);
		IPage<MudTicketInfoDTO> ipage = mudMapper.queryMudTicketByDriverId(page, userId, type);
		return ipage;
	}

	@Override
	public MudTicketInfoDTO verifyTicketSell(Map<String, String> dataMap) throws Exception {
		String userId = UserContextHolder.getUserId();
		UserVO userVO = UserContextHolder.get();
		if (!"mt".equals(userVO.getType())) {
			throw new BaseException("只有出纳员才可出售");
		}
		;
		String bossId = dataMap.get(CodeConstant.BOSS_ID);
		String timeStamp = dataMap.get(CodeConstant.TIME_STAMP);
		String mybossId = userMapper.getBossIdByMemberId(userId);
		if (!StringUtils.equals(bossId, mybossId)) {
			throw new BaseException("不可出售非本公司泥票");
		}
		MudTicketInfoDTO mudTicketInfo = mudMapper.getMudTicketInfoByBossIdAndTimeStamp(bossId, timeStamp);
		certService.verify("泥场出售", bossId, mudTicketInfo.getSign());
		return mudTicketInfo;
	}

	@Override
	public MudTicketInfoDTO verifyTicketSellDriver(String data) throws Exception {
		String userId = UserContextHolder.getUserId();
		UserVO userVO = UserContextHolder.get();
		if (!"tt".equals(userVO.getType())) {
			throw new BaseException("只有出纳员才可出售");
		}
		Map<String, String> map = certService.dataStringToMap(data);
		String bossId = map.get(CodeConstant.BOSS_ID);
		String timeStamp = map.get(CodeConstant.TIME_STAMP);
		String mybossId = userMapper.getBossIdByMemberId(userId);
		if (!StringUtils.equals(bossId, mybossId)) {
			throw new BaseException("不可出售非本公司泥票");
		}
		MudTicketInfoDTO mudTicketInfo = mudMapper.getMudTicketAgentInfo(bossId, timeStamp);
		// certService.verify(mudTicketInfo, userId, sign);
		certService.verify("运输公司出售", bossId, mudTicketInfo.getSign());
		return mudTicketInfo;
	}

	@Override
	public Map<String, String> agentGetTicketCodeInfo(String ticketId) {

		return mudMapper.agentGetTicketCodeInfo(ticketId);
	}

	@Override
	public Map<String, String> driverGetTicketCodeInfo(String agentId) {

		return mudMapper.driverGetTicketCodeInfo(agentId);
	}

	@Override
	public Map<String, String> getDriverMudTicketCodeByNumId(String userId, String numId) {
		return mudMapper.getDriverMudTicketCodeByNumId(userId, numId);
	}

	@Override
	public MudTicketInfoDTO verifyTicketHeadCode(String data) throws Exception {
		String userId = UserContextHolder.getUserId();
		UserVO userVO = UserContextHolder.get();
		if (!"ms".equals(userVO.getType())) {
			throw new BaseException("只有放行员才可以放行");
		}
		Map<String, String> map = certService.dataStringToMap(data);
		String bossId = map.get(CodeConstant.BOSS_ID);
		String sellerId = map.get(CodeConstant.APPLICANT);
		String timeStamp = map.get(CodeConstant.TIME_STAMP);
		String mybossId = userMapper.getBossIdByMemberId(userId);
		if (!StringUtils.equals(bossId, mybossId)) {
			// throw new BaseException("不可非本公司泥票");
		}
		MudTicketInfoDTO mudTicketInfo = mudMapper.getMudTicketSellInfo(sellerId, timeStamp);
		// certService.verify(mudTicketInfo, userId, sign);
		certService.verify("泥场出纳签字", sellerId, mudTicketInfo.getSign());
		return mudTicketInfo;

	}

	@Override
	public void addMudTruckHeadInfo(MudTruckInfoDTO mudTruckInfoDTO) {

		mudMapper.addMudTruckHeadInfo(mudTruckInfoDTO);
	}

	@Override
	public void addMudTruckTailInfo(MudTruckInfoDTO mudTruckInfoDTO) {
		mudMapper.addMudTruckTailInfo(mudTruckInfoDTO);
	}

	@Override
	public MudTicketInfoDTO verifyTicketTailCode(Map<String, String> dateMap) throws Exception {
		String userId = UserContextHolder.getUserId();
		UserVO userVO = UserContextHolder.get();
		if (!"tb".equals(userVO.getType())) {
			throw new BaseException("只有收纳员才可操作");
		}
		String bossId = dateMap.get(CodeConstant.BOSS_ID);
		String sellerId = dateMap.get(CodeConstant.APPLICANT);
		String timeStamp = dateMap.get(CodeConstant.TIME_STAMP);
		String mybossId = userMapper.getBossIdByMemberId(userId);
		if (!StringUtils.equals(bossId, mybossId)) {
			// throw new BaseException("不可非本公司泥票");
		}
		MudTicketInfoDTO mudTicketInfo = mudMapper.getMudTicketSellInfo(sellerId, timeStamp);
		// certService.verify(mudTicketInfo, userId, sign);
		certService.verify("泥场出纳签字", sellerId, mudTicketInfo.getSign());
		return mudTicketInfo;
	}

	@Override
	public IPage<MudTicketInfoDTO> queryMudYardTicketByYardId(String yardId, Integer size, Integer current) {
		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current, size);
		IPage<MudTicketInfoDTO> ipage = mudMapper.queryMudYardTicketByYardId(page, yardId);
		return ipage;
	}

	@Override
	public IPage<Map<String, String>> queryMudTicketSell( Integer size, Integer current,String userId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> ipage = mudMapper.queryMudTicketSell(page, userId);
		return ipage;
	}
	
	@Override
	public IPage<Map<String, String>> queryTruckMudTicketSell( Integer size, Integer current,String userId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> ipage = mudMapper.queryTruckMudTicketSell(page, userId);
		return ipage;
	}

	@Override
	public void addMudTruckApply(MudTruckApplyDTO mudTruckApplyDTO) {
		mudMapper.addMudTruckApply(mudTruckApplyDTO);
	}

	@Override
	public IPage<Map<String, String>> queryCheckTicketHeadCode(Integer size, Integer current, String userId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> ipage = mudMapper.queryCheckTicketHeadCode(page, userId);
		return ipage;
	}

	@Override
	public IPage<Map<String, String>> queryCheckTicketTailCode(Integer size, Integer current, String userId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
		IPage<Map<String, String>> ipage = mudMapper.queryCheckTicketTailCode(page, userId);
		return ipage;
	}

	@Override
	public IPage<Map<String, String>> queryMudTruckByDriverId(Integer size, Integer current, String userId) {
		Page<Map<String, String>> page = new Page<Map<String, String>>(current, size);
//		IPage<Map<String, String>> ipage = mudMapper.queryMudTruckByDriverId(page, userId);
//		return ipage;
		return null;
	}
}
