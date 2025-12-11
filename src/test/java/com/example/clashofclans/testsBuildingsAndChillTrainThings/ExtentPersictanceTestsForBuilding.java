package com.example.clashofclans.testsBuildingsAndChillTrainThings;
import com.example.clashofclans.buildings.*;
		import com.example.clashofclans.enums.*;
		import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.theRest.Village;
import org.junit.jupiter.api.*;
		import java.nio.file.*;
		import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ExtentPersictanceTestsForBuilding  {

	private static final Path BUILDING_FILE = Path.of("building_extent_test.dat");
	private static final Path INSTANCE_FILE = Path.of("instance_extent_test.dat");

	@BeforeEach
	void reset() {
		Building.deleteExtent(BUILDING_FILE);
		BuildingInstance.deleteExtent(INSTANCE_FILE);
	}

	@Test
	void testSaveAndLoadBuildingExtent() {
		Building b1 = new Building(100, 3, 1, 200);
		Building b2 = new Building(200, 5, 2, 400);

		Building.saveExtent(BUILDING_FILE);

		Building.deleteExtent(BUILDING_FILE);
		assertEquals(0, Building.getExtent().size());

		Building.loadExtent(BUILDING_FILE);
		assertEquals(2, Building.getExtent().size());
	}

	@Test
	void testSaveAndLoadBuildingInstanceExtent() {
		Village v = new Village(VillageType.regular, new Player("Tester"));
		Building b = new Building(100, 5, 2, 300);

		new BuildingInstance(v, b, 100, 1,
				LocalDateTime.now(), new int[]{1,2}, false);

		BuildingInstance.saveExtent(INSTANCE_FILE);

		BuildingInstance.deleteExtent(INSTANCE_FILE);
		assertEquals(0, BuildingInstance.getExtent().size());

		BuildingInstance.loadExtent(INSTANCE_FILE);
		assertEquals(1, BuildingInstance.getExtent().size());
	}

	@Test
	void testDeleteExtentFile() {
		Building b = new Building(100, 3, 1, 100);

		Building.saveExtent(BUILDING_FILE);
		assertTrue(Files.exists(BUILDING_FILE));

		Building.deleteExtent(BUILDING_FILE);

		assertFalse(Files.exists(BUILDING_FILE));
		assertEquals(0, Building.getExtent().size());
	}

	@Test
	void testFindMethod() {
		Building b1 = new Building(100, 3, 1, 200);
		Building b2 = new Building(500, 7, 3, 900);

		Building result = Building.find(b -> b.getResourceCost() == 900);

		assertNotNull(result);
		assertEquals(500, result.getHitPoints());
	}
}
