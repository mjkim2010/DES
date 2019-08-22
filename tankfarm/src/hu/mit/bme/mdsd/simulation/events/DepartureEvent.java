/*package hu.mit.bme.mdsd.simulation.events;

import hu.mit.bme.mdsd.simulation.TankFarmSimulationModel;
import hu.mit.bme.mdsd.simulation.entities.LevelEntity;
import hu.mit.bme.mdsd.simulation.entities.TableEntity;
import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class DepartureEvent extends EventOf2Entities<LevelEntity, TableEntity>{

	private TankFarmSimulationModel model;
	
	public DepartureEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (TankFarmSimulationModel) owner;
	}

	@Override
	public void eventRoutine(LevelEntity who1, TableEntity who2) {
		
		if (model.eatersQueue.isEmpty()) {
			model.idleTableQueue.insert(who2);
		}
		else {
			LevelEntity customerGroup = model.eatersQueue.removeFirst();
			DepartureEvent event = new DepartureEvent(model, "Departure event", true);
			event.schedule(customerGroup, who2, new TimeSpan(model.getEatTime()));
		}
		
		model.timeSpentByCustomer.update(presentTime().getTimeAsDouble() - who1.getArrivalTime().getTimeAsDouble());
		
	}

}
*/