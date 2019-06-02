package SoftEng751.SoftEng751.io;

import spoon.reflect.code.CtFor;

import java.util.List;

import SoftEng751.SoftEng751.polyhedral.DependencyVector;
import SoftEng751.SoftEng751.polyhedral.LoopVar;

/**
 * This interface outlines the methods required to extract the loop bounds and dependency vectors from a loop.
 */
public interface LoopParser {

    /**
     * This method extracts the loop bounds from a loop,
     *
     * @return A list of LoopVars representing the loop bounds
     */
    List<LoopVar> getLoopVars();

    /**
     * This method extracts dependency vectors from a loop.
     *
     * @return A list of DependencyVectors.
     */
    List<DependencyVector> getDependencyVectors();

    /**
     * Gets the outermost loop.
     *
     * @return A CtFor object representing the outermost loop.
     */
    CtFor getOutermostLoop();
}
