package SoftEng751.SoftEng751.io;

import SoftEng751.SoftEng751.testMethods.DependencyVector;
import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.reflect.code.CtFor;

import java.util.List;
import java.util.Map;

public interface LoopParser {
    List<LoopVar> getLoopVars();

    List<DependencyVector> getDependencyVectors();
}
