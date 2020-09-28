package com.tc.mud.applet.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.mud.applet.dto.MudTicketInfoDTO;
import com.tc.mud.applet.dto.MudTruckInfoDTO;
import com.tc.mud.applet.dto.dto.MudHeadTranDTO;
import com.tc.mud.applet.dto.dto.MudTailInfoDTO;
import com.tc.mud.applet.dto.dto.MudTailTranDTO;

public interface MudTicketMapper {

	
	/** 保存泥[头]票信息*/
	void saveMudHeadInfo(MudTicketInfoDTO mudHeadInfo);

	/** 保存泥[尾]票信息*/
	void saveMudTailInfo(MudTailInfoDTO mudTailInfo);
	
	/** 获取泥头票信息*/
	MudTicketInfoDTO getMudHeadInfoByHeadId(String headId);

	/** 获取泥尾票信息*/
	MudTailInfoDTO getMudTailInfoByTailId(String tailId);

	/** 查询发布的泥尾票列表*/
	IPage<MudTailInfoDTO> queryMudTailList(Page<MudTailInfoDTO> page);

	/** 查询发布的泥头票列表*/
	IPage<MudTicketInfoDTO> queryMudHeadList(Page<MudTicketInfoDTO> page);

	/** 新增泥尾票交易*/
	void addMudTailTran(String tailId, String companyId);

	/** 新增泥头票交易*/
	void addMudHeadTran(String headId, String companyId);

	/** 查看公司泥头票列表*/
	IPage<MudTicketInfoDTO> queryCompanyMudHeadTranList(Page<MudTicketInfoDTO> page, String companyId,String status);

	/** 查看公司泥尾票列表*/
	IPage<MudTailInfoDTO> queryCompanyMudTailTranList(Page<MudTailInfoDTO> page, String companyId,String status);

	/** 查看公司发行的泥头票*/
	IPage<MudTicketInfoDTO> queryMudHeadByCompanyId(Page<MudTicketInfoDTO> page, String companyId);

	/** 查看公司发行的泥尾票*/
	IPage<MudTailInfoDTO> queryTailByCompanyId(Page<MudTailInfoDTO> page, String companyId);

	/** 查看泥尾票交易列表*/
	IPage<MudTailTranDTO> queryMudTailTranList(Page<MudTailTranDTO> page, String tailId, String status);

	/** 查看泥头票交易列表*/
	IPage<MudHeadTranDTO> queryMudHeadTranList(Page<MudHeadTranDTO> page, String headId, String status);

	/** 更新泥头票交易状态*/
	void updateMudHeadTranStatus(String headId, String companyId, String status);

	/** 更新泥尾票交易状态*/
	void updateMudTailTranStatus(String tailId, String companyId, String status);

	/** 更新泥头票状态*/
	void updateMudTailStatus(String tailId, String status);
	
	/** 更新泥尾票状态*/
	void updateMudHeadStatus(String headId, String status);

	/** 添加泥车信息*/
	void addMudTruckInfo(MudTruckInfoDTO mudTruckInfoDto);
	
	/** 更新运泥车放行信息*/
	void updateReleaseInfoByMudId(MudTruckInfoDTO mudTruckInfoDto);

	/** 更新运泥车接收信息*/
	void updateReceiveInfoByMudId(MudTruckInfoDTO mudTruckInfoDto);

	/** 更新泥车运输状态*/
	void updateMudTruckStatus(String mudId, String status);

	/** 查看运输信息*/
	MudTruckInfoDTO queryMudTruckInfoByMudId(String mudId);

	/** 查看泥头场状态*/
	MudTruckInfoDTO queryMudHeadStatusByHeadId(String headId);

//	/** 司机添加泥票预约*/
//	void addMudTruckApply(MudTruckApplyDTO mudTruckApplyDTO);
//
//	/** 查看预约列表*/
//	IPage<MudTruckApplyDTO> queryMudTrucKApplyStatus(Page<MudTruckApplyDTO> page, String date, String userId);
//
//	/** 驾驶员日志-已完成的运输预约*/
//	IPage<MudTruckApplyDTO> queryFinishedDrivingLog(Page<MudTruckApplyDTO> page, String date, String userId);
//
//	/** 驾驶员日志--未完成的运输预约*/
//	IPage<MudTruckApplyDTO> queryUnfinishedDrivingLog(Page<MudTruckApplyDTO> page, String date, String userId);
}
