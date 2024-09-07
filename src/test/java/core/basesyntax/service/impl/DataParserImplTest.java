package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataParserImplTest {
    private static final List<FruitTransaction> DEFAULT_FRUITS;
    private static final List<String> DEFAULT_TEST_STRINGS;
    private static final DataParserImpl dataParser;
    private List<String> fruits;
    private List<FruitTransaction> expected;

    static {
        DEFAULT_FRUITS = new ArrayList<>(List.of(
                new FruitTransaction("apple", 20,
                        FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 15,
                        FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("strawberry", 70,
                        FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("orange", 42,
                        FruitTransaction.Operation.RETURN)));
        DEFAULT_TEST_STRINGS = new ArrayList<>(List.of(
                "type,fruit,quantity","b,apple,20", "s,banana,15",
                "p,strawberry,70", "r,orange,42"));
        dataParser = new DataParserImpl();
    }

    @BeforeEach
    void setUp() {
        fruits = DEFAULT_TEST_STRINGS;
        expected = DEFAULT_FRUITS;
    }

    @Test
    void parse_ValidInput_ok() {
        List<FruitTransaction> parsedInfo = dataParser.parse(fruits);
        assertEquals(expected, parsedInfo);
    }

    @Test
    void parse_nullInput_notOk() {
        expected = new ArrayList<>();
        List<FruitTransaction> parsedInfo = dataParser.parse(null);
        assertEquals(expected, parsedInfo);
    }

    @Test
    void parse_invalidInput_notOk() {
        fruits.set(1, "invalid,data");
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataParser.parse(fruits));
        String expected = "Invalid fruit transaction line: invalid,data";
        assertEquals(expected, exception.getMessage());
    }
}
