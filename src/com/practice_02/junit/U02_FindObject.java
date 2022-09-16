package com.practice_02.junit;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.practice_02.support.model.Machine;
import com.practice_02.support.remote.FindObjectFromDatabaseRemote;
import com.tool.EjbClient;

class U02_FindObject {

	static IBasicDataPreparation dataprepare;
	static FindObjectFromDatabaseRemote<Machine> machineDAO;
	Map<String, Object> filter = new HashMap<String, Object>();
	
	@BeforeAll
	static void setUp() throws Exception {
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		dataprepare.practice_02_initinalDataMachine();
		machineDAO = EjbClient.factory("Q02_FindObjectFromDatabase_Multi");
	}
	
	/**
	 * 2-1
	 * 完成 TranningProject.com.practice_02.ejb.Q02_FindObjectFromDatabase_Multi 方法 findByEntityManager
	 * 使用 EntityManager 查詢指定Machine
	 * 
	 * Machine.id: machineE
	 * Machine.version: 10001
	 * 
	 */
	@Test
	void testFindByEntityManager() throws Exception {
		Machine data = machineDAO.findByEntityManager("machineE%AAT%10001");
		
		Assert.assertEquals(data.getPk().getId(), "machineE");
		Assert.assertEquals(data.getName(), "machineNameR");
		Assert.assertEquals(data.getRegion().getID(), "zone3");
	}

	/**
	 * 2-2
	 * 完成 TranningProject.com.practice_02.ejb.Q02_FindObjectFromDatabase_Multi 方法 findByFilterWithNativeSQL
	 * 使用 NativeSQL 查詢指定Machine
	 * 
	 * MachineWorkingTime: 在星期二(dayOfWeek=2)設定了3筆資料，其他日不限
	 * 
	 * note: 可能會有多筆資料
	 */
	@Test
	void testFindByFilterWithNativeSQL() throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("dayOfWeek", 2);
		filter.put("dayOfWeekCount", 3);
		List<Machine> datas = machineDAO.findByFilterWithNativeSQL(filter);
		
		if(datas.isEmpty())
			fail("No Result");
		
		for(Machine data : datas) {
			Assert.assertTrue(data.getPk().getId().equals("machineC") || data.getPk().getId().equals("machineD"));
		}
	}

	/**
	 * 2-3
	 * 完成 TranningProject.com.practice_02.ejb.Q02_FindObjectFromDatabase_Multi 方法 findByFilterWithCriteria
	 * 使用 Criteria 查詢指定Machine
	 * 
	 * MachineExtraInfo.machine_name_alias: aliasMachineNameR
	 * Area.name: AAA
	 * Machine.internalUsage: true
	 * 
	 * note: 可能會有多筆資料
	 */
	
//	SELECT  machine.no
//    ,machine.version
//    ,machine.internalUsage
//    ,machine.name 
//    ,machine.machineextraInfo_no
//    ,machine.machineextraInfo_version
//    ,machine.area_Id from Machine as machine
//	  LEFT JOIN area on  machine.area_Id=Area.ID
//	  LEFT JOIN MachineExtraInfo on  machine.no=MachineExtraInfo.no
//	  where Area.name='AAA' 
//	      AND MachineExtraInfo.machine_name_alias='aliasMachineNameR' 
//		  AND machine.internalUsage='True'
	@Test
	void testFindByFilterWithCriteria() throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("machine_name_alias", "aliasMachineNameR");
		filter.put("internalUsage", true);
		filter.put("areaName", "AAA");
		List<Machine> datas = machineDAO.findByFilterWithCriteria(filter);
		
		if(datas.isEmpty())
			fail("No Result");
		
		for(Machine data : datas) {
			Assert.assertEquals("aliasMachineNameR", data.getMachineextraInfo().getMachine_name_alias());
			Assert.assertEquals("AAA", data.getRegion().getName());
			Assert.assertEquals(true, data.isInternalUsage());
		}
	}

	/**
	 * 2-4
	 * 完成 TranningProject.com.practice_02.ejb.Q02_FindObjectFromDatabase_Multi 方法 findByFilterJPQL
	 * 使用 JPQL 查詢指定Machine
	 * 
	 * Area.name: AAA
	 * MachineWorkingTime: 有3筆資料
	 * MachineExtraInfo.machine_name_alias: aliasMachineNameQ
	 * 
	 * note: 可能會有多筆資料
	 */
	@Test
	void testFindByFilterJPQL() throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", "AAA");
		filter.put("machineWorkingTime", 3);
		filter.put("machine_name_alias", "aliasMachineNameQ");
		List<Machine> datas = machineDAO.findByFilterJPQL(filter);
		
		if(datas.isEmpty())
			fail("No Result");
		
		for(Machine data : datas) {
			Assert.assertEquals("aliasMachineNameQ", data.getMachineextraInfo().getMachine_name_alias());
			Assert.assertEquals("AAA", data.getRegion().getName());
			Assert.assertEquals(3, data.getMachineWorkingTimes().size(), 0.0);
		}
	}
	
	@AfterAll
	static void reset() throws Exception {
		dataprepare.practice_02_resetDataMachine();
	}
}
