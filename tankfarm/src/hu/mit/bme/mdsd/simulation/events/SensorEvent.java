package hu.mit.bme.mdsd.simulation.events;

import hu.mit.bme.mdsd.simulation.TankFarmSimulationModel;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class SensorEvent extends ExternalEvent{

	private TankFarmSimulationModel model;
	
	private static final int BASE_TIME_MIN = 50;
	private static final int TIME_DELTA_RANGE = 11;
	private int currentTankLevel;


	public SensorEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (TankFarmSimulationModel) owner;
		currentTankLevel = 0;

	}

	@Override
	public void eventRoutine() {
		
		if (currentTankLevel >= 90) {
			currentTankLevel = 0;
		}
		else {
		currentTankLevel += model.getTankLevelChange();
		}
		
		System.out.println(currentTankLevel);

		model.getTemperatureChange();
		model.getPressureChange();
		
		Random rand = new Random();
		int nextTime = BASE_TIME_MIN + rand.nextInt(TIME_DELTA_RANGE);
		schedule(new TimeSpan(nextTime, TimeUnit.MINUTES));   //schedules another measurement every 2 minutes
		
	}
}
