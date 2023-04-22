package com.basics.testmore.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduledTasksTest {

	private ScheduledTasks scheduledTasks = new ScheduledTasks();

	@Test
	void logSeconds_5( ) {

		scheduledTasks.logSeconds_5();
		assertNotNull(scheduledTasks);
	}

	@Test
	void logMinutes( ) {

		scheduledTasks.logMinutes();
		assertNotNull(scheduledTasks);
	}

	@Test
	void logDaily( ) {

		scheduledTasks.logDaily();
		assertNotNull(scheduledTasks);
	}

	@Test
	void logWeekend( ) {

		scheduledTasks.logWeekend();
		assertNotNull(scheduledTasks);
	}
}