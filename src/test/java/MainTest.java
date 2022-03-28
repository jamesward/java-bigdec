import com.github.tonivade.purefun.effect.util.PureConsole;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testValidValue() {
        var program = Main.inputBigDecimalValue("Enter a number", new BigDecimal(1), new BigDecimal(10));

        var in = new LinkedList<String>();
        in.add("1");
        var out = new LinkedList<String>();
        var result = program.safeRunSync(PureConsole.test(in, out));
        assertEquals(result.get(), new BigDecimal(1));
    }

    @Test
    public void testNonParsable() {
        var program = Main.inputBigDecimalValue("Enter a number", new BigDecimal(1), new BigDecimal(10));

        var in = new LinkedList<String>();
        in.add("a");
        var out = new LinkedList<String>();
        var result = program.safeRunSync(PureConsole.test(in, out));
        assertEquals(result.getCause().getMessage(), "Invalid input.");
    }

    @Test
    public void testOutOfRange() {
        var program = Main.inputBigDecimalValue("Enter a number", new BigDecimal(1), new BigDecimal(10));

        var in = new LinkedList<String>();
        in.add("0");
        var out = new LinkedList<String>();
        var result = program.safeRunSync(PureConsole.test(in, out));
        assertEquals(result.getCause().getMessage(), "Input out of the range from 1 to 10");
    }

}
