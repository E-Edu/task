package de.themorpheus.edu.taskservice.spring;

import de.themorpheus.edu.taskservice.pubsub.dto.ServiceStartDTO;
import de.themorpheus.edu.taskservice.pubsub.dto.ServiceStopDTO;
import de.themorpheus.edu.taskservice.pubsub.pub.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStartListener {

	@Autowired private MessagePublisher publisher;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationStart() {
		this.publisher.publish(new ServiceStartDTO(this.getClass().getPackage().getImplementationTitle()));
	}

	@EventListener(ContextStoppedEvent.class)
	public void onApplicationStop() {
		this.publisher.publish(new ServiceStopDTO(this.getClass().getPackage().getImplementationTitle()));
	}

}
