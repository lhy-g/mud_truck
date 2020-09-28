package com.tc.mud.applet.mapper;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.mud.applet.dto.MudTicketAgentInfoDTO;
import com.tc.mud.applet.dto.MudTicketInfoDTO;
import com.tc.mud.applet.dto.MudTicketSellDTO;
import com.tc.mud.applet.dto.MudTruckApplyDTO;
import com.tc.mud.applet.dto.MudTruckInfoDTO;
import com.tc.mud.applet.dto.dto.MudHeadTranDTO;
import com.tc.mud.applet.dto.dto.MudTailInfoDTO;
import com.tc.mud.applet.dto.dto.MudTailTranDTO;

public interface MudTicketsMapper {

	/** 泥场:新增泥票 */
	void saveMudHeadInfo(MudTicketInfoDTO ticketInfo);

	/** 泥场:获取泥票信息 */
	MudTicketInfoDTO getMudTicketInfoByTicketId(String ticketId);
	
	/** 运输公司:添加运输公司购票*/
	void addMudTicketSellCompanyId(MudTicketSellDTO ticketSellDTO);
	
	/** 运输公司:添加泥票再发行 */
	void addMudTicketsAgent(MudTicketAgentInfoDTO mudTicketAgent);

	/** 运输司机:添加司机购票 **/
	void addDriverBuyMudTicket(MudTicketSellDTO mudTicketSellDTO);

	/** 泥场:查询泥票列表 */
	IPage<MudTicketInfoDTO> queryMudTicket(Page<MudTicketInfoDTO> page, String userId);

	/** 运输队老板:查看已购泥票列表 **/
	IPage<MudTicketInfoDTO> queryMudTicketsByTruckCompanyId(Page<MudTicketInfoDTO> page, String userId, String type);

	/** 运输队老板:查询再发行的泥票 */
	IPage<MudTicketInfoDTO> queryMudTicketsAgent(Page<MudTicketInfoDTO> page, String userId,String type);

	/** 司机:查询泥票列表 */
	IPage<MudTicketInfoDTO> queryMudTicketByDriverId(Page<MudTicketInfoDTO> page, String userId, String type);

	/** 泥场出售泥票:获取泥票信息*/
	MudTicketInfoDTO getMudTicketInfoByBossIdAndTimeStamp(String bossId, String timeStamp);

	/** 运输队出售泥票:获取泥票代理信息*/
	MudTicketInfoDTO getMudTicketAgentInfo(String bossId, String timeStamp);

	/** 运输队:购买泥票获取泥票二维码信息*/
	Map<String, String> agentGetTicketCodeInfo(String ticketId);

	/** 司机:购买泥票获取泥票二维码信息*/
	Map<String, String> driverGetTicketCodeInfo(String certId);

	/** 司机:泥车放行,获取放行泥票二维码信息*/
	Map<String, String> getDriverMudTicketCodeByNumId(String userId, String numId);

	/** 放行员:放行验证泥票信息*/
	MudTicketInfoDTO getMudTicketSellInfo(String sellerId, String timeStamp);

	/** 添加运输泥头信息 */
	void addMudTruckHeadInfo(MudTruckInfoDTO mudTruckInfoDTO);

	/** 添加运输泥尾信息  */
	void addMudTruckTailInfo(MudTruckInfoDTO mudTruckInfoDTO);

	/** 查询泥场所有泥票 */
	IPage<MudTicketInfoDTO> queryMudYardTicketByYardId(Page<MudTicketInfoDTO> page, String yardId);

	/** 查看出售泥票记录*/
	IPage<Map<String, String>> queryMudTicketSell(Page<Map<String, String>> page, String userId);

	/** 运输队出纳员查看出售记录*/
	IPage<Map<String, String>> queryTruckMudTicketSell(Page<Map<String, String>> page, String userId);

	/** 添加泥票预约*/
	void addMudTruckApply(MudTruckApplyDTO mudTruckApplyDTO);

	/** 查看泥场放行记录*/
	IPage<Map<String, String>> queryCheckTicketHeadCode(Page<Map<String, String>> page, String userId);

	/** 查看泥场收纳记录*/
	IPage<Map<String, String>> queryCheckTicketTailCode(Page<Map<String, String>> page, String userId);

	

	

}
