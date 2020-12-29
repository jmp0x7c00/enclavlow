package io.github.sof3.enclavlow.cases.lfg;

@SuppressWarnings("unused")
public class AssignCase {
    public static int paramToReturn(int x) {
        return x;
    }

    public static int paramToThrow(RuntimeException x) {
        throw x;
    }

    static int a;

    public static void paramToStatic(int x) {
        a = x;
    }

    public static int zeroizeAssign(int x) {
        @SuppressWarnings({"SuspiciousNameCombination", "UnusedAssignment"}) int y = x;
        y = 0;
        return y;
    }

    @SuppressWarnings({"UnusedAssignment", "ParameterCanBeLocal"})
    public static void assignParam(int x) {
        x = 1;
    }
}
