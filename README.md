# Loopt
Loop Optimizer - Dependence analysis and simple loop transformations

## Problem Description
In the lectures we have learnt about dependences in loops. For the examples given it was easy to manually determine dependence distances and vectors. Compilers use automatic dependence tests. Study the literature and describe these dependence tests. What are the conditions so that dependences can be determined and how is that done? Consider also techniques based on the polyhedral approach for dependence analysis and dependence modelling.In some cases we can transform a loop with inter-iteration dependences into a loop without such dependences. We saw the example of loop shifting in the lecture. Investigate what loop transformations exist, how they work and when they can be applied. Document that systematically. Implement a dependence test with a number of these loop transformations where you take as input a code snippet (in Java) with a loop and output a transformed loop (in Java). You can use libraries and tools to help you do this.

## Running Loopt

Download the latest jar from [releases](https://github.com/Nathan-Cairns/Loopt/releases).

Run the Jar from the command line

```
java -jar Loopt.jar {flags}
```

### Flags

```
-inFilePath {filepath} - indicates your input will be a path to a file containing a java loop.
-inString {string} - indicates your input will be a raw string representing a java loop.
-outFilePath {filepath} - specifies a file to write your transformed loop to. This is optional if not provided the transformed loop will be printed to the console.
```

*Note: You must provide either the -inFilePath or -inString flag to indicate what kind of input you are passing Loopt.*

### Input Restrictions
Inputted loops must conform to the following restricitons:
* Loops must be two dimensional (double nested)
* Loops must be for loops, with the following format: for (int i = x; i < n; i++)
* All array accesses must be at most as complex as simple arithmetic. Some valid examples are as follows:
  * A[i][j]
  * A[i + 1][ j - 1]
  * A[i + 8][1]


### Test snippets

There are several example loops which can be used as input. These can be found in the [TestSnippets](https://github.com/Nathan-Cairns/Loopt/tree/master/Testsnippets) folder.
