package hu.mit.bme.mdsd.simulation.events;

import hu.mit.bme.mdsd.simulation.TankFarmSimulationModel;
import hu.mit.bme.mdsd.simulation.entities.LevelEntity;
import hu.mit.bme.mdsd.simulation.entities.TemperatureEntity;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class SensorEvent extends ExternalEvent{

	private TankFarmSimulationModel model;

	public SensorEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (TankFarmSimulationModel) owner;
	}

	@Override
	public void eventRoutine() {

		int size = model.getCustomerGroupSize();   //M: samples for random value
		model.customerGroupSizeHistogram.update(size);
		
		LevelEntity customerGroup = new LevelEntity(size, model, "Customer Group", false);
		
		/*if (model.idleWaitressQueue.isEmpty()) {
			model.buyersQueue.insert(customerGroup);
		}
		else {
			//TemperatureEntity waitress = model.idleWaitressQueue.removeFirst();
			//BuyEndedEvent event = new BuyEndedEvent(model, "Buy ended event", true);
			//event.schedule(customerGroup, waitress, new TimeSpan(model.getBuyTime()));
		}
		*/
		schedule(new TimeSpan(1, TimeUnit.HOURS));   //M: schedules another measurement in an hour
		
	}
}
