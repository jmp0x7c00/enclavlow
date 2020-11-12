package io.github.sof3.enclavlow.cases.lfg;

@SuppressWarnings("unused")
public class LoopCase {
    public static int loopInc(int i) {
        int a = 0;
        for (int j = 0; j < i; j++) {
            a += j;
        }
        return a;
    }

    public static int loopDec(int i) {
        int a = 0;
        while (i-- > 0) {
            a += i;
        }
        return a;
    }

}
