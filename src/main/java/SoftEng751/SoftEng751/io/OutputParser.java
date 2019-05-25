package SoftEng751.SoftEng751.io;

import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.reflect.code.CtFor;

import java.util.List;

public interface OutputParser {
    String output(List<LoopVar> transformedLoopVars, CtFor originalLoop);
}
