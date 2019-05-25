package SoftEng751.SoftEng751.testMethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyVector {
    private Map<String, Integer> vectors;

    public DependencyVector(List<String> variables) {
        this.vectors = new HashMap<String, Integer>();
        for (String variable : variables) {
            this.vectors.put(variable, 0);
        }
    }

    public void setDistance(String variableName, int distance) throws Exception {
        if (!this.vectors.containsKey(variableName)) {
            throw new Exception("Variable is not in dependency vector");
        }
        this.vectors.put(variableName, distance);
    }

    public int getDependencyDistance(String variableName) throws Exception {
        if (!this.vectors.containsKey(variableName)) {
            throw new Exception("Variable is not in dependency vector");
        }
        return this.vectors.get(variableName);
    }
}
