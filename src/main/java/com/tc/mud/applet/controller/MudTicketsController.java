package com.tc.mud.applet.controller;

 
 
 
 

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tc.core.annotation.OpenMapping;
import com.tc.core.utils.DateUtils;
import com.tc.core.utils.MyCodeUtil;
import com.tc.core.utils.MyFileUtils;
import com.tc.core.utils.UserContextHolder;
 
import com.tc.core.web.responce.R;
 
 
import com.tc.mud.applet.constant.CodeConstant;
import com.tc.mud.applet.dto.MudTicketAgentInfoDTO;
import com.tc.mud.applet.dto.MudTicketInfoDTO;
import com.tc.mud.applet.dto.MudTicketSellDTO;
import com.tc.mud.applet.dto.MudTruckApplyDTO;
import com.tc.mud.applet.dto.MudTruckInfoDTO;
import com.tc.mud.applet.service.CertService;
import com.tc.mud.applet.service.MudTicketsService;
 
 

@Slf4j
@RestController
@Api(tags = "3.1泥票相关接口")
@RequestMapping("/applet/mud")
@SuppressWarnings("unchecked")
public class MudTicketsController {
	
	@Autowired
    private MudTicketsService mudTicksService;
	@Autowired
	private CertService certService;
	
	@OpenMapping
	@ApiOperation("自定义")
	@RequestMapping(value = "/tests",method = RequestMethod.GET)
	public void hello(HttpServletResponse response) throws Exception {
		byte[] fimalByte = MyCodeUtil.generateQRCodeToByte("6666", "1629");
		// response.addHeader("Content-Type", "text/plain;charset=UTF-8");
		response.addHeader("Content-Type", "image/jpg");
		response.getOutputStream().write(fimalByte);
	}
	
	@ApiOperation("泥场:新增泥票")
    @ApiOperationSupport(order = 101)
    @RequestMapping(value="/save_mud_ticket",method = RequestMethod.POST)
    public R<Void> saveMudHead(@RequestBody MudTicketInfoDTO ticketInfo) {
    		mudTicksService.saveMudHeadInfo(ticketInfo);
    		return R.success().setData(null);
    }
	
	@ApiOperation("泥场:获取泥票信息")
    @ApiOperationSupport(order =102)
    @RequestMapping(value="/get_mud_ticket",method = RequestMethod.GET)
    public R<Void> getMudTicketInfo(
    		@ApiParam(value="泥票ID",required=true) @RequestParam String ticketId) {
    		MudTicketInfoDTO MudTicketInfoDTO = mudTicksService.getMudTicketInfo(ticketId);
    		return R.success().setData(MudTicketInfoDTO);
    }
	
	@ApiOperation("泥场:查看泥票发行列表")
    @ApiOperationSupport(order = 103)
    @RequestMapping(value="/query_mud_ticket",method = RequestMethod.GET)
    public R<Void> queryMudTicket(
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    		String userId = UserContextHolder.getUserId();
    		IPage<MudTicketInfoDTO> ipage = mudTicksService.queryMudTicket(userId,size,current);
    		modifyImageUrl(ipage.getRecords());
    		return R.success().setData(ipage);
		 
    }
	
	@ApiOperation("运输队:查找泥场泥票")
    @ApiOperationSupport(order = 104)
    @RequestMapping(value="/search_mud_yard",method = RequestMethod.GET)
    public R<Void> searchMudYard(
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
    		@ApiParam(value="泥场Id",required=true) @RequestParam String yardId) {
			IPage<MudTicketInfoDTO> ipage = mudTicksService.queryMudYardTicketByYardId(yardId,size,current);
			modifyImageUrl(ipage.getRecords());
    		return R.success().setData(ipage);
    }
	
	@ApiOperation("运输队:购买泥票(展示泥票大图)")
    @ApiOperationSupport(order = 105)
    @RequestMapping(value="/agent_show_code",method = RequestMethod.GET)
    public R<Void> agentShowCode(
    		@ApiParam(value="泥票Id",required=true) @RequestParam String ticketId) {
    		String userId = UserContextHolder.getUserId();
    		Map<String,String> map = mudTicksService.agentGetTicketCodeInfo(ticketId);
    		map.put(CodeConstant.CONSUMER_ID, userId);
    		return R.success().setData(buildCodeImgPath(map));
    }
	
	@ApiOperation("泥场:出售泥票(运输队购买泥票)")
    @ApiOperationSupport(order = 106)
    @RequestMapping(value="/add_mud_ticket_sell",method = RequestMethod.GET)
    public R<Void> addMudTicketSellCompany(
    		@ApiParam(value="二维码",required=true) @RequestParam String data) {
    	try {
    		Map<String, String> dataMap = certService.dataStringToMap(data);
    		MudTicketInfoDTO ticketInfo = mudTicksService.verifyTicketSell(dataMap);
    		MudTicketSellDTO ticketSellDTO = new MudTicketSellDTO();
    		String  userId = UserContextHolder.getUserId();
    		//ticketSellDTO.setOrderId(DateUtils.getGuid());
    		ticketSellDTO.setTicketId(ticketInfo.getTicketId());
    		ticketSellDTO.setSellerId(userId);
    		ticketSellDTO.setBuyerId(dataMap.get(CodeConstant.CONSUMER_ID));
    		String sign = certService.sign("泥场出纳签字", userId);
    		ticketSellDTO.setSign(sign);
    		ticketSellDTO.setTimeStamp(DateUtils.getTimeStamp());
    		mudTicksService.addMudTicketSellCompanyId(ticketSellDTO);
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("泥场出售票错误:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("泥场:查看出售记录")
	@ApiOperationSupport(order = 107)
	@RequestMapping(value = "/query_mud_ticket_sell", method = RequestMethod.GET)
	public R<Void> queryMudTicketSell(@ApiParam(value = "行数", required = true) @RequestParam Integer size,
			@ApiParam(value = "页数", required = true) @RequestParam Integer current) {
		IPage<Map<String, String>> ipage = mudTicksService.queryMudTicketSell( size, current,UserContextHolder.getUserId());
		return R.success().setData(ipage);
	}

	@ApiOperation("运输队:查看购买的泥票")
    @ApiOperationSupport(order = 201)
    @RequestMapping(value="/query_mud_ticket_sell_to_agent",method = RequestMethod.GET)
    public R<Void> queryMudTicketsByTruckCompanyId(
    		@ApiParam(value="类型(head:泥头,tail:泥尾)",required=true) @RequestParam String type,
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    	 
    		String userId = UserContextHolder.getUserId();
    		IPage<MudTicketInfoDTO> ipage =  mudTicksService.queryMudTicketsByTruckCompanyId(size,current,userId,type);
    		modifyImageUrl(ipage.getRecords());
    		return R.success().setData(ipage);
		 
    }
	
	@ApiOperation("运输队:泥票再发行")
    @ApiOperationSupport(order = 202)
    @RequestMapping(value="/agent_mud_ticket",method = RequestMethod.GET)
    public R<Void> agentMudTickets(
    		@ApiParam(value="订单ID",required=true) @RequestParam String orderId,
    		@ApiParam(value="泥票ID",required=true) @RequestParam String ticketId,
    		@ApiParam(value="价格",required=true) @RequestParam Integer price,
    		@ApiParam(value="车次",required=true) @RequestParam Integer count) {
    	try {
    		String companyId = UserContextHolder.getCompanyId();
    		MudTicketAgentInfoDTO  mudTicketAgent = new MudTicketAgentInfoDTO();
    		//mudTicketAgent.setAgentId(DateUtils.getGuid());
    		mudTicketAgent.setOrderId(orderId);
    		mudTicketAgent.setBossId(UserContextHolder.getUserId());
    		mudTicketAgent.setCompanyId(companyId);
    		mudTicketAgent.setPrice(price);
    		mudTicketAgent.setCount(count);
    		mudTicketAgent.setTimeStamp(DateUtils.getTimeStamp());
    	 	mudTicketAgent.setTicketId(ticketId);
    	 	//mudTicketAgent.setSign(certService.sign(mudTicketAgent, UserContextHolder.getUserId()));
    		mudTicketAgent.setSign(certService.sign("运输公司出售", UserContextHolder.getUserId()));
    		mudTicksService.addMudTicketsAgent(mudTicketAgent);
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("泥票再发行异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("运输队/司机:查看再发行的泥票列表")
    @ApiOperationSupport(order = 203)
    @RequestMapping(value="/query_mud_ticket_agent",method = RequestMethod.GET)
    public R<Void> queryAgentMudTickets(
    		@ApiParam(value="类型(泥头票:head;泥尾票:tail)",required=true) @RequestParam String type,
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    		IPage<MudTicketInfoDTO> ipage =  mudTicksService.queryMudTicketsAgent(size,current,UserContextHolder.getUserId(),type);
    		modifyImageUrl(ipage.getRecords());
    		return R.success().setData(ipage);
    }
	
	@ApiOperation("司机:购买泥票(展示泥票大图)")
    @ApiOperationSupport(order = 204)
    @RequestMapping(value="/driver_show_code",method = RequestMethod.GET)
    public R<Void> driverShowCode(
    		@ApiParam(value="代理Id",required=true) @RequestParam String agentId) {
    		String userId = UserContextHolder.getUserId();
    		Map<String,String> map = mudTicksService.driverGetTicketCodeInfo(agentId);
    		map.put(CodeConstant.CONSUMER_ID, userId);
    		return R.success().setData(buildCodeImgPath(map));
    }
	
	
	@ApiOperation("运输队:出售泥票(司机购买泥票)")
    @ApiOperationSupport(order = 205)
    @RequestMapping(value="/driver_buy_ticket",method = RequestMethod.GET)
    public R<Void> driverBuyMudTicket(
    		@ApiParam(name="data",value="二维码",required=true) @RequestParam String data) {
    	try {
    		Map<String, String> dateMap = certService.dataStringToMap(data);
    		MudTicketInfoDTO ticketInfo = mudTicksService.verifyTicketSellDriver(data);
    		MudTicketSellDTO mudTicketSellDTO = new MudTicketSellDTO();
    		mudTicketSellDTO.setSellerId(UserContextHolder.getUserId());
    		mudTicketSellDTO.setAgentId(ticketInfo.getAgentId());
    		mudTicketSellDTO.setDriverId(dateMap.get(CodeConstant.CONSUMER_ID));
    		mudTicketSellDTO.setSign(certService.sign("运输公司出售", UserContextHolder.getUserId()));
    		mudTicksService.driverBuyMudTicket(mudTicketSellDTO);
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("运输队出售泥票异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("运输队:查看运输队出售记录")
	@ApiOperationSupport(order =206)
	@RequestMapping(value = "/query_truck_mud_ticket_sell", method = RequestMethod.GET)
	public R<Void> queryTruckMudTicketSell(
			@ApiParam(value = "行数", required = true) @RequestParam Integer size,
			@ApiParam(value = "页数", required = true) @RequestParam Integer current) {
		IPage<Map<String, String>> ipage = mudTicksService.queryTruckMudTicketSell( size, current,UserContextHolder.getUserId());
		return R.success().setData(ipage);
	}
	
	@ApiOperation("司机:查看购买泥票列表")
    @ApiOperationSupport(order = 301)
    @RequestMapping(value="/query_ticket_by_driver",method = RequestMethod.GET)
    public R<Void> queryMudTicketByDriverId(
    		@ApiParam(value="类型(泥头票:head;泥尾票:tail)",required=true) @RequestParam String type,
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    		IPage<MudTicketInfoDTO> ipage = mudTicksService.queryMudTicketByDriverId(size,current,UserContextHolder.getUserId(),type);
    		modifyImageUrl(ipage.getRecords());
    		return R.success().setData(ipage);
    }
	
	@ApiOperation("司机:放行员放行(展示泥票大图)")
    @ApiOperationSupport(order = 302)
    @RequestMapping(value="/truck_show_code",method = RequestMethod.GET)
    public R<Void> queryMudTicketByDriverId(
    		@ApiParam(value="司机的泥票编号",required=true) @RequestParam String numId) {
    		String userId = UserContextHolder.getUserId();
    		Map<String,String> map = mudTicksService.getDriverMudTicketCodeByNumId(userId,numId);
    		map.put(CodeConstant.CONSUMER_ID, userId);
    		return R.success().setData(buildCodeImgPath(map));
    }
	
	@ApiOperation("司机:添加泥漂头预约申请")
	@RequestMapping(value = "/add_mud_truck_apply", method = RequestMethod.POST)
	public R<Void> addMudTruckApply(@RequestBody MudTruckApplyDTO mudTruckApplyDTO) {
		try {
			mudTruckApplyDTO.setUserId(UserContextHolder.getUserId());
			mudTicksService.addMudTruckApply(mudTruckApplyDTO);
			return R.success();
		} catch (Exception e) {
			log.error("添加泥漂预约申请异常:", e);
			return R.fail().setMsg("系统异常!");
		}
	}
	
	@ApiOperation("泥场放行员:验证泥头票放行")
    @ApiOperationSupport(order = 303)
    @RequestMapping(value="/check_ticket_head_code",method = RequestMethod.GET)
    public R<Void> checkTicketHeadCode(
    		@ApiParam(value="二维码",required=true) @RequestParam String data) {
    	try {
    		String userId = UserContextHolder.getUserId();
    		Map<String, String> dateMap = certService.dataStringToMap(data);
    		MudTicketInfoDTO ticketInfo = mudTicksService.verifyTicketHeadCode(data);
    		MudTruckInfoDTO mudTruckInfoDTO = new MudTruckInfoDTO();
    		mudTruckInfoDTO.setMudId(DateUtils.getGuid());
    		mudTruckInfoDTO.setHeadId(ticketInfo.getTicketId());
    		mudTruckInfoDTO.setDriverId(dateMap.get(CodeConstant.CONSUMER_ID));
    		mudTruckInfoDTO.setReleaseId(userId);
    		mudTruckInfoDTO.setReleaseTime(DateUtils.getTimeStamp());
    		String sign = certService.sign("放行员签字", userId);
    		mudTruckInfoDTO.setReleaseSign(sign);
    		mudTicksService.addMudTruckHeadInfo(mudTruckInfoDTO);
    		return R.success().setData("");
		} catch (Exception e) {
			log.error("泥场放行员验证泥头票放行异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("泥场放行员:查看泥头票放行记录")
	@ApiOperationSupport(order =304)
	@RequestMapping(value = "/query_check_ticket_head_code", method = RequestMethod.GET)
	public R<Void> queryCheckTicketHeadCode(
			@ApiParam(value = "行数", required = true) @RequestParam Integer size,
			@ApiParam(value = "页数", required = true) @RequestParam Integer current) {
		IPage<Map<String, String>> ipage = mudTicksService.queryCheckTicketHeadCode( size, current,UserContextHolder.getUserId());
		return R.success().setData(ipage);
	}
	
	
	
	@ApiOperation("泥场收纳员:验证泥尾票")
    @ApiOperationSupport(order = 305)
    @RequestMapping(value="/check_ticket_tail_code",method = RequestMethod.GET)
    public R<Void> checkTicketTailCode(
    		@ApiParam(value="二维码",required=true) @RequestParam String data,
    		@ApiParam(value="泥土运输ID",required=true) @RequestParam String mudId) {
    	try {
    		String userId = UserContextHolder.getUserId();
    		Map<String, String> dateMap = certService.dataStringToMap(data);
    		MudTicketInfoDTO ticketInfo = mudTicksService.verifyTicketTailCode(dateMap);
    		MudTruckInfoDTO mudTruckInfoDTO = new MudTruckInfoDTO();
    		mudTruckInfoDTO.setMudId(mudId);
    		mudTruckInfoDTO.setTailId(ticketInfo.getTicketId());
    		mudTruckInfoDTO.setReceiverId(userId);
    		mudTruckInfoDTO.setReceiveTime(DateUtils.getTimeStamp());
    		String sign = certService.sign("放行员签字", userId);
    		mudTruckInfoDTO.setReceiveSign(sign);
    		mudTicksService.addMudTruckTailInfo(mudTruckInfoDTO);
    		return R.success().setData("");
		} catch (Exception e) {
			log.error("泥场收纳员验证泥尾票异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("泥场放行员:查看泥头票放行记录")
	@ApiOperationSupport(order =306)
	@RequestMapping(value = "/query_check_ticket_tail_code", method = RequestMethod.GET)
	public R<Void> queryCheckTicketTailCode(
			@ApiParam(value = "行数", required = true) @RequestParam Integer size,
			@ApiParam(value = "页数", required = true) @RequestParam Integer current) {
		IPage<Map<String, String>> ipage = mudTicksService.queryCheckTicketTailCode( size, current,UserContextHolder.getUserId());
		return R.success().setData(ipage);
	}
	
	@ApiOperation("司机:查看运输记录")
	@ApiOperationSupport(order =306)
	@RequestMapping(value = "/query_mud_ticket_tail_code", method = RequestMethod.GET)
	public R<Void> queryMudTruckByDriverId(
			@ApiParam(value = "行数", required = true) @RequestParam Integer size,
			@ApiParam(value = "页数", required = true) @RequestParam Integer current) {
		IPage<Map<String, String>> ipage = mudTicksService.queryMudTruckByDriverId( size, current,UserContextHolder.getUserId());
		return R.success().setData(ipage);
	}
	
	/**
	 * 生成泥票图片请求路径
	 * */
	public static String buildCodeImgPath(Map<String,String> map) {
		String img = map.remove("img");
		String url = CodeConstant.URL + map;
		byte[] fimalByte = MyCodeUtil.generateQRCodeToByte(url, img);
		String fileName ="tmp-" +DateUtils.getTimeStamp()+".jpg";
		File file = new File(MyFileUtils.uploadPath + fileName);
		MyFileUtils.fileWriteByte(file, fimalByte);
		String path = MyFileUtils.urlPath + fileName;
		return path;
		//return "https://p.3p3.top/upload/test.jpg";
	}
	
	/**
	 * 修改图片路径
	 * @param list
	 * @return
	 */
	public static List<MudTicketInfoDTO> modifyImageUrl(List<MudTicketInfoDTO> list){
		StringBuilder sb = new StringBuilder(MyFileUtils.urlPath);
		for(MudTicketInfoDTO dto:list) {
			sb.append(dto.getImageNum());
			dto.setImageNum(sb.toString());
			sb.delete(MyFileUtils.urlPath.length(),sb.length());
			//dto.setImageNum("https://p.3p3.top/upload/2008105278528101.jpg");
		}
		return list;
	}
}
