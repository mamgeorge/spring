package com.basics.testmore.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockTests {

	@Mock List<String> listMock; // = new ArrayList<>();

	@BeforeEach public void setup( ) {
		// if we don't call below, we will get NullPointerException
		MockitoAnnotations.openMocks(this);
	}

	// uses: mock, when.thenReturn()
	@Test void showTimeMock1( ) {
		//
		Date dateMock = mock(Date.class);
		when(dateMock.toString()).thenReturn("2021/02/20").thenReturn("12:34:56 AM");
		String txtLine = dateMock + " " + dateMock;
		System.out.println(PAR + txtLine);
		assertTrue(txtLine.length() > 10);
	}

	// uses: mock, when, anyLong()
	@Test void showTimeMock2( ) {
		//
		Date dateMock = mock(Date.class);
		dateMock.setTime(anyLong());
		when(dateMock.toString()).thenReturn("2021/02/20 12:34:56 AM");
		System.out.println(PAR + dateMock);
		assertTrue(dateMock.toString().length() > 20);
	}

	// uses: mock, when, doReturn
	@Test void showTimeMock3( ) {
		//
		Date dateMock = mock(Date.class);
		doReturn(1000L).when(dateMock).getTime();
		//
		System.out.println(
			PAR + dateMock.toString() + " / " + dateMock.getTime()); // Mock for Date, hashCode: 1947064457
		assertEquals(1000L, dateMock.getTime());
	}

	@Test void showTimeMock4( ) {
		//
		when(listMock.get(0)).thenReturn("MGeorge");
		assertEquals("MGeorge", listMock.get(0));
	}
}
