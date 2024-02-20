package edu.pitt.cs;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MonkeySimPinningTest {
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private PrintStream stdout;
	
	@Before
	public void setUp() {
		// Back up the old output stream
		stdout = System.out;
		// Redirect the output stream
		System.setOut(new PrintStream(out));
		
		// Reset the monkey counter to 0
		try {
			Field f = Monkey.class.getDeclaredField("monkeyNum");
			f.setAccessible(true);
			f.set(null, 0);
		} catch (Exception e) {
			// fail(e.toString());
		}
	}
	
	@After
	public void tearDown() {
		System.setOut(stdout);
	}
	
	@Test
	public void testMain5() throws InfiniteLoopException, NoIdException {
		MonkeySim.main(new String[] { Integer.toString(5) });

		String nl = System.getProperty("line.separator");
		assertEquals("Defect in the output for running simulation with argument 5",
				"//Round 1: Threw banana from Monkey (#5 / ID 223497) to Monkey (#16 / ID 223508)" + nl
						+ "//Round 2: Threw banana from Monkey (#16 / ID 223508) to Monkey (#8 / ID 223500)" + nl
						+ "//Round 3: Threw banana from Monkey (#8 / ID 223500) to Monkey (#4 / ID 223496)" + nl
						+ "//Round 4: Threw banana from Monkey (#4 / ID 223496) to Monkey (#2 / ID 223494)" + nl
						+ "//Round 5: Threw banana from Monkey (#2 / ID 223494) to Monkey (#1 / ID 223493)" + nl
						+ "First monkey has the banana!" + nl
						+ "Completed in 5 rounds." + nl,
				out.toString());
	}
}