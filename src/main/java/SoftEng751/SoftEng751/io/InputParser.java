package SoftEng751.SoftEng751.io;

import SoftEng751.SoftEng751.testMethods.LoopVar;

import java.util.List;

public interface InputParser {
    List<LoopVar> parse(String filepath);
}