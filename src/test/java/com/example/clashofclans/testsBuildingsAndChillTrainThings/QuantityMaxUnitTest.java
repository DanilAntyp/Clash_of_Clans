package com.example.clashofclans.testsBuildingsAndChillTrainThings;

import com.example.clashofclans.buildings.*;
import com.example.clashofclans.enums.*;
import com.example.clashofclans.exceptions.building.InvalidBuildingArgumentException;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.theRest.Village;
import com.example.clashofclans.units.Troop;
import com.example.clashofclans.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMaxUnitTest {


	private Village village;
	private ArmyBuilding barracks;
	private ArmyBuilding armyCamp;
	private Building building;
	private BuildingInstance buildingInstance;
	private Unit unit1;

	@BeforeEach
	void setUp() {
		village = new Village(VillageType.regular, new Player("testPlayer"));
		barracks = new ArmyBuilding(ArmyBuildingType.barracks, 50);
		armyCamp = new ArmyBuilding(ArmyBuildingType.armyCamp, 100);
		building = new Building(100, 5, 2, 500);
		buildingInstance = new BuildingInstance(village, building, 100, 1,
				LocalDateTime.now().plusHours(2), new int[]{0,0}, false,new QuantityMaxUnit(100)
		);
		unit1 = new Troop(village,
				100, 20, 5,
				AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN,
				AttackStyle.GROUND_TROOP, 50
		);


	}
	@Test
	void addUnit_null_throws() {
		QuantityMaxUnit q = new QuantityMaxUnit(5);
		assertThrows(InvalidUnitArgumentException.class, () -> q.addUnit(null));
	}

	@Test
	void addUnit_duplicate_throws() {
		QuantityMaxUnit q = new QuantityMaxUnit(5);
		q.addUnit(unit1);
		assertThrows(InvalidUnitArgumentException.class, () -> q.addUnit(unit1));
	}

	@Test
	void removeUnit_null_throws() {
		QuantityMaxUnit q = new QuantityMaxUnit(5);
		assertThrows(InvalidUnitArgumentException.class, () -> q.removeUnit(null));
	}

	@Test
	void addInstance_null_throws() {
		QuantityMaxUnit q = new QuantityMaxUnit(5);
		assertThrows(InvalidBuildingArgumentException.class, () -> q.addInstance(null));
	}

	@Test
	void addInstance_duplicate_throws() {
		QuantityMaxUnit q = new QuantityMaxUnit(5);
		BuildingInstance b = new BuildingInstance(village, building, 100, 1,
				LocalDateTime.now().plusHours(2), new int[]{0,0}, false,new QuantityMaxUnit(100)
		);

		q.addInstance(b);
		assertThrows(InvalidBuildingArgumentException.class, () -> q.addInstance(b));
	}

	@Test
	void removeInstance_null_throws() {
		QuantityMaxUnit q = new QuantityMaxUnit(5);
		assertThrows(InvalidBuildingArgumentException.class, () -> q.removeInstance(null));
	}

	@Test
	void validCalls_doNotThrow() {
		QuantityMaxUnit q = new QuantityMaxUnit(5);

		assertDoesNotThrow(() -> q.addUnit(unit1));
		assertDoesNotThrow(() -> q.removeUnit(unit1));
		assertDoesNotThrow(() -> q.addInstance(buildingInstance));
		assertDoesNotThrow(() -> q.removeInstance(buildingInstance));
	}
}
