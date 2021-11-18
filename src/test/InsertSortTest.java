package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class InsertSortTest {

	@Test
	public void test() {
		assertEquals(Arrays.asList(1,2,3,4,5), new InsertSort(Arrays.asList(5,3,2,4,1)).getSortedList());
	}

	@Test
	public void test_one_element() {
		assertEquals(Arrays.asList(1), new InsertSort(Arrays.asList(1)).getSortedList());
	}
}
