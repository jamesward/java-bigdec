import com.github.tonivade.purefun.effect.PureIO;
import com.github.tonivade.purefun.effect.RIO;
import com.github.tonivade.purefun.effect.Schedule;
import com.github.tonivade.purefun.effect.util.PureConsole;


import java.math.BigDecimal;

public class Main {

    static RIO<PureConsole, BigDecimal> inputBigDecimalValue(final String prompt, final BigDecimal min, final BigDecimal max) {
        return PureConsole.println(prompt)
                .andThen(PureConsole.readln())
                .flatMap(s -> RIO.task(() -> new BigDecimal(s))).toPureIO().mapError(t -> new Exception("Invalid input.")).toRIO()
                .flatMap(n -> {
                    if (n.compareTo(min) >= 0 && n.compareTo(max) <= 0) {
                        return RIO.pure(n);
                    } else {
                        return RIO.raiseError(new Exception("Input out of the range from " + min + " to " + max));
                    }
                });
    }

    public static void main(String[] args) {
        var program = inputBigDecimalValue("Enter a number", new BigDecimal(1), new BigDecimal(10))
                .toPureIO()
                .flatMapError(t -> PureConsole.println(t.getMessage()).toPureIO().flatMap(i -> PureIO.raiseError(t)))
                .toRIO();

        program.retry(Schedule.forever()).safeRunSync(PureConsole.live());
    }

}
