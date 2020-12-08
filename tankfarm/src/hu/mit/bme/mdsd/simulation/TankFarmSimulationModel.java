package hu.mit.bme.mdsd.simulation;

import hu.mit.bme.mdsd.simulation.events.SensorEvent;
import hu.mit.bme.mdsd.simulation.SimulationRunner;

import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDist;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.dist.DiscreteDist;
import desmoj.core.dist.DiscreteDistPoisson;
import desmoj.core.dist.DiscreteDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimClock;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.Histogram;
import desmoj.core.statistic.Tally;

public class TankFarmSimulationModel extends Model {

	//Arbitrary values for which tank level can change
	private static final int TANK_DELTA_MIN = 0;
	private static final int TANK_DELTA_MAX = 10;
	private DiscreteDistUniform tankLevelDelta;	  //uniform distribution for modeling tank level change
	private ContDist temperatureDay;
	private TimeInstant currentTime;
	private ContDist temperatureNight;
	private ContDist pressure;
	
	public int getTankLevelChange() {
		int value = tankLevelDelta.sample().intValue();
		System.out.println("Tank Level: "  + value);
		return value;
	}
	
	public int getTemperatureChange() {
		
		int value; 
		TimeInstant noon = new TimeInstant(12, TimeUnit.HOURS);
		System.out.println(currentTime);
		//If the current time in the simulation is before noon
		if (TimeInstant.isBefore(currentTime, noon) ) {
			value = temperatureDay.sample().intValue();
		} else {
			value = temperatureNight.sample().intValue();
		}
		System.out.println("Temperature: "  + value);
		return value;
	}
	
	public int getPressureChange() {
		int value = pressure.sample().intValue();
		System.out.println("Pressure: "  + value);
		return value;
	}

	public TankFarmSimulationModel(Model owner, String name,
			boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return "This model simulates a tank farm scenario, where there are multiple" +
				"tanks, each with a unique temperature, pressure, and tank level. " +
				"Every hour, a sensor will measure those three attributes for each tank ";
	}

	@Override
	public void doInitialSchedules() {
		SensorEvent event = new SensorEvent(this, "Sensor event", true);
		event.schedule(new TimeInstant(0));
	}

	@Override
	public void init() {
		SimulationRunner simModel = new SimulationRunner();
		currentTime = simModel.getTime();
		System.out.println(currentTime);
		
		tankLevelDelta = new DiscreteDistUniform(this, "Change in tank level", TANK_DELTA_MIN, TANK_DELTA_MAX, true, false);
		temperatureDay = new ContDistNormal(this, "Temperature", 70, 5, true, false);
		temperatureNight = new ContDistNormal(this, "Temperature", 55, 5, true, false);
		pressure = new ContDistNormal(this, "Pressure", 10, 3, true, false);
	}

}
