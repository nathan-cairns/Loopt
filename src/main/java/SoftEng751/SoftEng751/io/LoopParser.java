package SoftEng751.SoftEng751.io;

import SoftEng751.SoftEng751.testMethods.DependencyVector;
import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.reflect.code.CtFor;

import java.util.List;

public interface LoopParser {
    List<LoopVar> getLoopVars();

    List<DependencyVector> getDependencyVectors();

    CtFor getOutermostLoop();
}
