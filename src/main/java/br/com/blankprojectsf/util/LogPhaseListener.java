package br.com.blankprojectsf.util;


import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LogPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 2665118935135157055L;

	@Override
	public void afterPhase(final PhaseEvent $event) {
		System.out.printf("after %s %n", $event.getPhaseId());
	}

	@Override
	public void beforePhase(final PhaseEvent $event) {
		System.out.printf("before %s %n", $event.getPhaseId());
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}