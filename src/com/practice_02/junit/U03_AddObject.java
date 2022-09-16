package com.practice_02.junit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.AddObjectRemote;
import com.tool.EjbClient;

/**
 * 單一物件新增
 *
 */
public class U03_AddObject {
	static IBasicDataPreparation dataprepare;
	static AddObjectRemote<Area> areaDAO;
	
	Area a = new Area();
	
	@BeforeAll
	static void setUp() throws Exception {
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		areaDAO = EjbClient.factory("Q03_AddObject");
	}
	
	/**
	 * 3-1
	 * 完成 TranningProject.com.practice_02.ejb.Q03_AddObject 方法 addByEntityManager
	 * 使用 EntityManager 於資料庫中存入一筆新Area
	 * id: areaA
	 * name: nameA
	 * category: APS
	 * 
	 */
	@Test
	void testAddByEntityManager() throws Exception {
		Area data = areaDAO.addByEntityManager(a);
		
		Assert.assertEquals("areaA", data.getID());
		Assert.assertEquals("nameA", data.getName());
		Assert.assertEquals(AreaType.APS, data.getCategory());
	}

	/**
	 * 3-2
	 * 完成 TranningProject.com.practice_02.ejb.Q03_AddObject 方法  addWithNativeSQL
	 * 使用 NativeSQL 於資料庫中存入一筆新Area
	 * id: areaB
	 * name: nameB
	 * category: WMS
	 * 
	 */
	@Test
	void testAddWithNativeSQL() throws Exception {
		Area data = areaDAO.addWithNativeSQL(a);
		
		Assert.assertEquals("areaB", data.getID());
		Assert.assertEquals("nameB", data.getName());
		Assert.assertEquals(AreaType.WMS, data.getCategory());
	}


	
	@AfterAll
	static void reset() throws Exception {
		dataprepare.practice_02_resetData();
	}
}
