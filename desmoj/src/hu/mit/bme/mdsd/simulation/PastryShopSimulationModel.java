package hu.mit.bme.mdsd.simulation;

import hu.mit.bme.mdsd.simulation.entities.CustomerGroupEntity;
import hu.mit.bme.mdsd.simulation.entities.TableEntity;
import hu.mit.bme.mdsd.simulation.entities.WaitressEntity;
import hu.mit.bme.mdsd.simulation.events.ArrivalEvent;
import desmoj.core.dist.ContDist;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.dist.DiscreteDist;
import desmoj.core.dist.DiscreteDistPoisson;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.Histogram;
import desmoj.core.statistic.Tally;

public class PastryShopSimulationModel extends Model{

	private static final int WAITRESSES = 1;
	private static final int TABLES = 3;
	
	public Queue<CustomerGroupEntity> buyersQueue;
	public Queue<CustomerGroupEntity> eatersQueue;
	public Queue<WaitressEntity> idleWaitressQueue;
	public Queue<TableEntity> idleTableQueue;
	
	private DiscreteDist<?> customerGroupSize;
	private ContDist customerArrivalTime;
	private ContDist buyTime;
	private ContDist eatTime;
	
	public Tally timeSpentByCustomer;
	public Histogram customerGroupSizeHistogram;
	
	public int getCustomerGroupSize(){
		int value = customerGroupSize.sample().intValue();
		return value == 0 ? 1 : value;
	}
	
	public int getCustomerArrivalTime(){
		int value = customerArrivalTime.sample().intValue();
		return value <= 0 ? 0 : value;
	}
	
	public int getBuyTime(){
		return buyTime.sample().intValue();
	}
	
	public int getEatTime(){
		return eatTime.sample().intValue();
	}

	public PastryShopSimulationModel(Model owner, String name,
			boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return "Pastry shop simulation";
	}

	@Override
	public void doInitialSchedules() {
		ArrivalEvent event = new ArrivalEvent(this, "Arrival event", true);
		event.schedule(new TimeInstant(0));
	}

	@Override
	public void init() {
		buyersQueue = new Queue<CustomerGroupEntity>(this, "Buyers Queue", true, false);
		eatersQueue = new Queue<CustomerGroupEntity>(this, "Eaters Queue", true, false);
		idleWaitressQueue = new Queue<WaitressEntity>(this, "Idle Waitresses Queue", true, false);
		idleTableQueue = new Queue<TableEntity>(this, "Idle Tables Queue", true, false);

		customerGroupSize = new DiscreteDistPoisson(this, "Customer group size", 2, true, false);
		customerArrivalTime = new ContDistNormal(this, "Customer arrival time", 4*60, 1*60, true, false);
		buyTime = new ContDistUniform(this, "Buying time", 1*60, 3*60, true, false);
		eatTime = new ContDistUniform(this, "Eating time", 5*60, 20*60, true, false);
	
		for (int i = 0; i < WAITRESSES; i++){
			idleWaitressQueue.insert(new WaitressEntity(this, "Waitress", false));
		}
		
		for (int i = 0; i < TABLES; i++){
			idleTableQueue.insert(new TableEntity(this, "Table", false));
		}
		
		timeSpentByCustomer = new Tally(this, "Time spent by customer", true, false);
		customerGroupSizeHistogram = new Histogram(this, "Histogram", 1, 10, 9, true, false);
	}

}
