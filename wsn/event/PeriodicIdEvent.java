package eboracum.wsn.event;

import ptolemy.actor.NoTokenException;
import ptolemy.actor.util.Time;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
public class PeriodicIdEvent extends BasicEvent {
// Abstract class representing WSN event actors
private static final long serialVersionUID = 1L;
public Parameter lifetime;
public Parameter period;
protected Time finishTime;
public PeriodicIdEvent(CompositeEntity container, String name)
throws IllegalActionException, NameDuplicationException {
super(container, name);
period = new Parameter(this, "Period");
period.setExpression("21600");// Colocar aqui a periodicidade desejada (periodicidade * 60 * 60)
lifetime = new Parameter(this, "Lifetime");
lifetime.setExpression((Time.POSITIVE_INFINITY).toString());// default event duration is Time.POSITIVE_INFINITY
}
public void fire() throws NoTokenException, IllegalActionException {
message = this.getName().split("_")[0]+"_"+this.numberOfProducedEvents;
super.fire();
}
public void initialize() throws IllegalActionException {
super.initialize();
Double t = Double.parseDouble(lifetime.getValueAsString());
this.finishTime = this.getDirector().getModelTime().add(t).add(Double.parseDouble(this.triggerTime.getExpression()));
}
public boolean postfire() throws IllegalActionException{
if (this.getDirector().getModelTime().compareTo(this.finishTime.subtract(Double.parseDouble(period.getValueAsString())))
< 0) {

_fireAt(this.getDirector().getModelTime().add(Double.parseDouble(period.getValueAsString())));
}
return super.postfire();
}
}