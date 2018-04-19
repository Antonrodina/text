package text.handler;

import org.junit.Test;

import text.repository.TextRepository;

import static org.junit.Assert.*;

/**
 * @author antonio
 */
public class HistoryHandlerTest {

    @Test
    public void givenNoElementsInRepo_returnEmpty(){
        // Given
        MockTextRepository textRepository = new MockTextRepository();
        HistoryHandler historyHandler = new HistoryHandler(textRepository);


    }
}