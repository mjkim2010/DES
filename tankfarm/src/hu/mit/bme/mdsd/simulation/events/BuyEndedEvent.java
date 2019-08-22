/*package hu.mit.bme.mdsd.simulation.events;

import hu.mit.bme.mdsd.simulation.TankFarmSimulationModel;
import hu.mit.bme.mdsd.simulation.entities.LevelEntity;
import hu.mit.bme.mdsd.simulation.entities.TableEntity;
import hu.mit.bme.mdsd.simulation.entities.TemperatureEntity;
import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class BuyEndedEvent extends EventOf2Entities<LevelEntity, TemperatureEntity>{

	private TankFarmSimulationModel model;
	
	public BuyEndedEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (TankFarmSimulationModel) owner;
	}

	@Override
	public void eventRoutine(LevelEntity who1, TemperatureEntity who2) {
		
		if (model.buyersQueue.isEmpty()) {
			model.idleWaitressQueue.insert(who2);
		}
		else {
			LevelEntity customerGroup = model.buyersQueue.removeFirst();
			BuyEndedEvent event = new BuyEndedEvent(model, "Buy ended event", true);
			event.schedule(customerGroup, who2, new TimeSpan(model.getBuyTime()));
		}
		
		if (model.idleTableQueue.isEmpty()) {
			model.eatersQueue.insert(who1);
		}
		else {
			TableEntity table = model.idleTableQueue.removeFirst();
			DepartureEvent event = new DepartureEvent(model, "Departure event", true);
			event.schedule(who1, table, new TimeSpan(model.getEatTime()));
		}
		
	}

}
*/