package hu.mit.bme.mdsd.simulation;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.EventList;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Scheduler;
import desmoj.core.simulator.SimClock;
import desmoj.core.simulator.TimeInstant;

public class SimulationRunner {

	private static SimClock clock;
	
	public static void main(String[] args) {
		
		TankFarmSimulationModel model = new TankFarmSimulationModel(null, "TankFarmSimulationModel", true, false);
		
		@SuppressWarnings("deprecation")
		Experiment experiment = new Experiment("Experiment", TimeUnit.SECONDS, TimeUnit.SECONDS, null);
		
		model.connectToExperiment(experiment);
		Scheduler scheduler = new Scheduler(experiment, "scheduler", null);
		clock = new SimClock("Simulation Clock");
		
		// Turn on the simulation trace from the start to the end
		experiment.traceOn(new TimeInstant(0));
		
		// Set when to stop the simulation
		experiment.stop(new TimeInstant(24, TimeUnit.HOURS));
		
		experiment.start();
		
		// Create the report files
		experiment.report();
		
		experiment.finish();
		
	}
	
	public TimeInstant getTime() {
		return clock.getTime();
	}
}
