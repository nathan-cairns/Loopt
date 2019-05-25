package SoftEng751.SoftEng751;

import static org.junit.jupiter.api.Assertions.*;

import SoftEng751.SoftEng751.testMethods.AffineTransformation;
import SoftEng751.SoftEng751.testMethods.LoopVar;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SkewingTests {

    @Test
    public void testDefaultSkewing(){
        LoopVar i = new LoopVar("i", 1, 6);
        LoopVar j = new LoopVar("j", 1, 5);
        List<LoopVar> inputVariables = new ArrayList<LoopVar>();
        inputVariables.add(i);
        inputVariables.add(j);

        AffineTransformation affineTransformation = new AffineTransformation();
        List<LoopVar> transformedVariables = affineTransformation.method(inputVariables);

        final String expectedITransformation = "i1";
        final String expectedJTransformation = "-1 * i1 + j1";
        assertEquals(transformedVariables.get(0).transformedname, expectedITransformation);
        assertEquals(transformedVariables.get(1).transformedname, expectedJTransformation);
    }
}
