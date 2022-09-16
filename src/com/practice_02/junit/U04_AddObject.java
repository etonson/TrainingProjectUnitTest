package com.practice_02.junit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.practice_02.support.model.Machine;
import com.practice_02.support.model.MachinePK;
import com.practice_02.support.model.MachineWorkingTime;
import com.practice_02.support.remote.AddObjectRemote;
import com.tool.EjbClient;

class U04_AddObject {
	
	static IBasicDataPreparation dataprepare;
	static AddObjectRemote<Machine> machineDAO;
	
	@BeforeAll
	static void setUp() throws Exception {
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		dataprepare.practice_02_initinalData();
		machineDAO = EjbClient.factory("Q04_AddObject_Multi");
	}

	/**
	 * 4-1
	 * 完成 TranningProject.com.practice_02.ejb.Q04_AddObject_Multi 方法 addByEntityManager
	 * 使用 EntityManager 於資料庫中存入一筆新Machine
	 * 
	 * Machine id: machineA
	 * Machine version: 20001
	 * Machine name: nameA
	 * Machine region: ABC(Area.name) *需先查詢關聯物件
	 * MachineExtraInfo machine_name_alias: aliasNameAA
	 * MachineWorkingTime : 星期 1-7 皆為 09:00-18:00
	 * 
	 */
	@Test
	void testAddByEntityManager() throws Exception {
		Machine t = new Machine();
		Machine machine = machineDAO.addByEntityManager(t);
		
		Assert.assertEquals(new MachinePK("machineA", 20001), machine.getPk());
		Assert.assertEquals(7, machine.getMachineWorkingTimes().size(), 0.001);
		Assert.assertEquals("ABC", machine.getRegion().getName());
		for(MachineWorkingTime mrt : machine.getMachineWorkingTimes()) {
			Assert.assertEquals(9, mrt.getStartHour(), 0.0);
		}
	}

	/**
	 * 4-2
	 * 完成 TranningProject.com.practice_02.ejb.Q04_AddObject_Multi 方法 addWithNativeSQL
	 * 使用 NativeSQL 於資料庫中存入一筆新Machine
	 * 
	 * Machine id: machineB
	 * Machine version: 30001
	 * Machine name: nameB
	 * Machine region: zone1(Area.id)
	 * MachineExtraInfo machine_name_alias: aliasNameBB
	 * 
	 */
	@Test
	void testAddWithNativeSQL() throws Exception {
		Machine t = new Machine();
		Machine machine = machineDAO.addWithNativeSQL(t);
		
		Assert.assertEquals(new MachinePK("machineB", 30001), machine.getPk());
		Assert.assertEquals("nameB", machine.getName());
		Assert.assertEquals("AAA", machine.getRegion().getName());
		Assert.assertEquals("aliasNameBB", machine.getMachineextraInfo().getMachine_name_alias());
		
	}

	
	@AfterAll
	static void reset() throws Exception {
		dataprepare.practice_02_resetDataMachine();
	}
}
