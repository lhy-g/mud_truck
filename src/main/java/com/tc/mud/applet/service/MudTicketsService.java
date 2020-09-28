package com.tc.mud.applet.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tc.mud.applet.dto.MudTicketAgentInfoDTO;
import com.tc.mud.applet.dto.MudTicketInfoDTO;
import com.tc.mud.applet.dto.MudTicketSellDTO;
import com.tc.mud.applet.dto.MudTruckApplyDTO;
import com.tc.mud.applet.dto.MudTruckInfoDTO;
import com.tc.mud.applet.dto.dto.MudHeadTranDTO;
import com.tc.mud.applet.dto.dto.MudTailInfoDTO;
import com.tc.mud.applet.dto.dto.MudTailTranDTO;

public interface MudTicketsService {

	/** 新增泥票*/
	void saveMudHeadInfo(MudTicketInfoDTO ticketInfo);

	/** 获取泥票信息*/
	MudTicketInfoDTO getMudTicketInfo(String ticketId);
	
	 

	/** 出售泥票给运输公司*/
	void addMudTicketSellCompanyId(MudTicketSellDTO ticketSellDTO);

	/** 泥场:查询我发行的泥票列表*/
	IPage<MudTicketInfoDTO> queryMudTicket(String userId, Integer size, Integer current);

	/** 运输公司查购买的泥票列表*/
	IPage<MudTicketInfoDTO> queryMudTicketsByTruckCompanyId(Integer size, Integer current, String userId,String type);

	/** 泥票现发行*/
	void addMudTicketsAgent(MudTicketAgentInfoDTO mudTicketAgent);

	/** 查看再发行的泥票*/
	IPage<MudTicketInfoDTO> queryMudTicketsAgent(Integer size, Integer current, String userId,String type);

	/** 添加司机购票 */
	void driverBuyMudTicket(MudTicketSellDTO mudTicketSellDTO);

	/** 查询已购泥票 
	 * @return */
	IPage<MudTicketInfoDTO> queryMudTicketByDriverId(Integer size, Integer current, String userId,String type);

	/** 泥票出售验证*/
	MudTicketInfoDTO verifyTicketSell(Map<String,String> dataMap)throws Exception;

	/**泥票出售验证(司机购买泥票)*/
	MudTicketInfoDTO verifyTicketSellDriver(String data)throws Exception;

	/** 运输队:购买泥票获取泥票二维码信息*/
	Map<String,String> agentGetTicketCodeInfo(String ticketId);

	/** 司机:购买泥票获取泥票二维码信息*/
	Map<String, String> driverGetTicketCodeInfo(String agentId);

	/** 司机:泥车放行,获取放行泥票二维码信息*/
	Map<String,String> getDriverMudTicketCodeByNumId(String userId, String numId);

	/** 泥头票放行验证*/
	MudTicketInfoDTO verifyTicketHeadCode (String data)throws Exception;

	/** 添加运输泥头信息*/
	void addMudTruckHeadInfo(MudTruckInfoDTO mudTruckInfoDTO);
	
	/** 泥尾票收纳验证*/
	MudTicketInfoDTO verifyTicketTailCode(Map<String, String> dateMap) throws Exception ;
	
	/** 添加运输泥尾信息*/
	void addMudTruckTailInfo(MudTruckInfoDTO mudTruckInfoDTO);

	/** 查看一个泥场所有泥票*/
	IPage<MudTicketInfoDTO> queryMudYardTicketByYardId(String yardId, Integer size, Integer current);

	/** 出纳员查看出售记录*/
	IPage<Map<String, String>> queryMudTicketSell( Integer size, Integer current,String userId);

	/** 运输队出纳员查看出售记录**/
	IPage<Map<String, String>> queryTruckMudTicketSell(Integer size, Integer current, String userId);

	/** 司机预约泥票*/
	void addMudTruckApply(MudTruckApplyDTO mudTruckApplyDTO);

	/** 泥头场查看放行记录*/
	IPage<Map<String, String>> queryCheckTicketHeadCode(Integer size, Integer current, String userId);


	/** 泥尾场查看收纳记录*/
	IPage<Map<String, String>> queryCheckTicketTailCode(Integer size, Integer current, String userId);

	/** 司机查看运输记录***/
	IPage<Map<String, String>> queryMudTruckByDriverId(Integer size, Integer current, String userId);
}
