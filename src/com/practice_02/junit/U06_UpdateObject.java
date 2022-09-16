package com.practice_02.junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.practice_02.support.model.Machine;
import com.practice_02.support.model.MachinePK;
import com.practice_02.support.model.MachineWorkingTime;
import com.practice_02.support.remote.UpdateObjectRemote2;
import com.tool.EjbClient;

class U06_UpdateObject {
	static IBasicDataPreparation dataprepare;
	static UpdateObjectRemote2<Machine> machineDAO;
	Map<String, Object> filter = new HashMap<String, Object>();
	
	@BeforeAll
	static void setUp() throws Exception {
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		dataprepare.practice_02_initinalDataMachine();
		machineDAO = EjbClient.factory("Q06_UpdateObject_Multi");
	}
	
	/**
	 * 6-1
	 * 完成 TranningProject.com.practice_02.ejb.Q06_UpdateObject_Multi 方法 updateByEntityManager
	 * 使用 EntityManager 更新資料庫中指定Machine資料
	 * 指定目標: id為machineA 的Machine
	 * 修改條件: 
	 * 1) Area變更為zone2(Area.id)
	 * 2) MachineWorkingTime 設定為星期 1, 2, 3  9:00 - 18:00
	 */
	@Test
	void testUpdateByEntityManager() throws Exception {
		Machine t = new Machine(new MachinePK("machineA", 10001));
		Machine machine = machineDAO.updateByEntityManager(t);
		
		Assert.assertEquals(new MachinePK("machineA", 10001), machine.getPk());
		Assert.assertEquals("ABC", machine.getRegion().getName());
		Assert.assertEquals(3, machine.getMachineWorkingTimes().size(), 0.001);
		
		for(MachineWorkingTime mrt : machine.getMachineWorkingTimes()) {
			Assert.assertEquals(9, mrt.getStartHour(), 0.0);
		}
	}

	
	/**
	 * 6-2
	 * 完成 TranningProject.com.practice_02.ejb.Q06_UpdateObject_Multi 方法 updateWithNativeSQL
	 * 使用 NativeSQL 更新資料庫中指定Machine資料
	 * 指定目標: id為machineC 的Machine
	 * 修改條件: 
	 * 1) name變更為machineNameQQQ
	 * 2) internalUsage 變更為 false
	 */
	@Test
	void testUpdateWithNativeSQL() throws Exception {
		Machine t = new Machine(new MachinePK("machineC", 10002));
		Machine machine = machineDAO.updateWithNativeSQL(t);
		
		Assert.assertEquals(new MachinePK("machineC", 10002), machine.getPk());
		Assert.assertEquals("machineNameQQQ", machine.getName());
		Assert.assertEquals(false, machine.isInternalUsage());
	}

	
	/**
	 * 6-3
	 * 完成 TranningProject.com.practice_02.ejb.Q06_UpdateObject_Multi 方法 updateWithCriteria
	 * 使用 Criteria 更新資料庫中指定Machine資料
	 * 指定目標: id為machineF 的Machine
	 * 修改條件: 
	 * 1) name變更為machineNameQQQ
	 * 2) MachineExtraInfo.machine_name_alias 變更為 aliasMachineNameRRR
	 */
	@Test
	void testUpdateWithCriteria() throws Exception {
		Machine t = new Machine(new MachinePK("machineF", 10001));
		Machine machine = machineDAO.updateWithCriteria(t);
		Assert.assertEquals(new MachinePK("machineF", 10001), machine.getPk());
		Assert.assertEquals("machineNameQQQ", machine.getName());
		Assert.assertEquals("aliasMachineNameRRR", machine.getMachineextraInfo().getMachine_name_alias());
	}

	
	/**
	 * 6-4
	 * 完成 TranningProject.com.practice_02.ejb.Q06_UpdateObject_Multi 方法 updateWithJPQL
	 * 使用 JPQL 更新資料庫中指定Machine資料
	 * 指定目標: id為machineD 的Machine
	 * 修改條件: 
	 * 1) Area變更為zone4(Area.id)
	 * 2) MachineWorkingTime 設定為無
	 */
	@Test
	void testUpdateWithJPQL() throws Exception {
		Machine t = new Machine(new MachinePK("machineD", 10001));
		Machine machine = machineDAO.updateWithJPQL(t);
		Assert.assertEquals(new MachinePK("machineD", 10001), machine.getPk());
		Assert.assertEquals("AAA", machine.getRegion().getName());
		Assert.assertEquals(0, machine.getMachineWorkingTimes().size(), 0.001);
	}

	
	@AfterAll
	static void reset() throws Exception {
		dataprepare.practice_02_resetDataMachine();
	}
	
}
