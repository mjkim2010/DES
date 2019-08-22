package hu.mit.bme.mdsd.simulation.events;

import hu.mit.bme.mdsd.simulation.PastryShopSimulationModel;
import hu.mit.bme.mdsd.simulation.entities.CustomerGroupEntity;
import hu.mit.bme.mdsd.simulation.entities.TableEntity;
import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class DepartureEvent extends EventOf2Entities<CustomerGroupEntity, TableEntity>{

	private PastryShopSimulationModel model;
	
	public DepartureEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (PastryShopSimulationModel) owner;
	}

	@Override
	public void eventRoutine(CustomerGroupEntity who1, TableEntity who2) {
		
		if (model.eatersQueue.isEmpty()) {
			model.idleTableQueue.insert(who2);
		}
		else {
			CustomerGroupEntity customerGroup = model.eatersQueue.removeFirst();
			DepartureEvent event = new DepartureEvent(model, "Departure event", true);
			event.schedule(customerGroup, who2, new TimeSpan(model.getEatTime()));
		}
		
		model.timeSpentByCustomer.update(presentTime().getTimeAsDouble() - who1.getArrivalTime().getTimeAsDouble());
		
	}

}
