package text.repository;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author antonio
 */
public class AddOnlyCircularBufferTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotInstantiateWithInvalidSize() {
        // When
        new AddOnlyCircularBuffer<>(0);
    }

    @Test
    public void givenNoElements_isEmpty() {
        // Given
        AddOnlyCircularBuffer<Integer> circularBuffer = new AddOnlyCircularBuffer<>(2);

        // When
        Queue<Integer> actualElements = circularBuffer.getAll();

        // Then
        assertTrue(actualElements.isEmpty());
    }

    @Test
    public void givenOneElementIsAdded_elementIsReturned() {
        // Given
        AddOnlyCircularBuffer<Integer> circularBuffer = new AddOnlyCircularBuffer<>(2);

        // When
        circularBuffer.add(1);
        Queue<Integer> actualElements = circularBuffer.getAll();

        // Then
        Queue<Integer> expectedElements = new LinkedList<>();
        expectedElements.add(1);
        assertEquals(expectedElements, actualElements);
    }

    @Test
    public void givenTwoElementsAreAddedAndSizeIs2_bothAreStored() {
        // Given
        AddOnlyCircularBuffer<Integer> circularBuffer = new AddOnlyCircularBuffer<>(2);

        // When
        circularBuffer.add(1);
        circularBuffer.add(2);
        Queue<Integer> actualElements = circularBuffer.getAll();

        // Then
        Queue<Integer> expectedElements = new LinkedList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        assertEquals(expectedElements, actualElements);
    }

    @Test
    public void givenSize2IsExceeded_onlyLast2AreStored() {
        // Given
        AddOnlyCircularBuffer<Integer> circularBuffer = new AddOnlyCircularBuffer<>(2);

        // When
        circularBuffer.add(1);
        circularBuffer.add(2);
        circularBuffer.add(3);
        Queue<Integer> actualElements = circularBuffer.getAll();

        // Then
        Queue<Integer> expectedElements = new LinkedList<>();
        expectedElements.add(2);
        expectedElements.add(3);
        assertEquals(expectedElements, actualElements);
    }
}