package com.company.demo;

import com.company.utils.BstSet;
import com.company.utils.SplaySet;

public class Test {
    public static void main(String[] args) {
        SplaySet<Integer> splaySet = new SplaySet<>();
        System.out.println("Zig rotation");
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(5);
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(1);
        System.out.println(splaySet.toVisualizedString(""));
        splaySet.splayValue(2);
        System.out.println(splaySet.toVisualizedString(""));

        splaySet.clear();
        System.out.println("Zag rotation");
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(1);
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(5);
        System.out.println(splaySet.toVisualizedString(""));
        splaySet.splayValue(4);
        System.out.println(splaySet.toVisualizedString(""));

        splaySet.clear();
        System.out.println("Zig-Zig rotation");
        splaySet.addWithoutSplaying(6);
        splaySet.addWithoutSplaying(7);
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(5);
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(1);
        System.out.println(splaySet.toVisualizedString(""));
        splaySet.splayValue(2);
        System.out.println(splaySet.toVisualizedString(""));

        splaySet.clear();
        System.out.println("Zag-Zag rotation");
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(1);
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(6);
        splaySet.addWithoutSplaying(7);
        splaySet.addWithoutSplaying(5);
        System.out.println(splaySet.toVisualizedString(""));
        splaySet.splayValue(6);
        System.out.println(splaySet.toVisualizedString(""));

        splaySet.clear();
        System.out.println("Zig-Zag rotation");
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(1);
        splaySet.addWithoutSplaying(6);
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(5);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(7);
        System.out.println(splaySet.toVisualizedString(""));
        splaySet.splayValue(4);
        System.out.println(splaySet.toVisualizedString(""));

        splaySet.clear();
        System.out.println("Zag-Zig rotation");
        splaySet.addWithoutSplaying(6);
        splaySet.addWithoutSplaying(7);
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(5);
        splaySet.addWithoutSplaying(1);
        System.out.println(splaySet.toVisualizedString(""));
        splaySet.splayValue(4);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove non-existing: 10");
        splaySet.remove(10);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove node with no children: 5");
        splaySet.remove(5);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove node with 2 children: 6");
        splaySet.remove(6);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove node with 1 child: 7");
        splaySet.remove(7);
        System.out.println(splaySet.toVisualizedString(""));

        splaySet.clear();
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(1);
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(5);
        System.out.println(splaySet.toVisualizedString(""));
        System.out.println("Contains element: 4");
        System.out.println(splaySet.contains(4));
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Contains non-existing element: 6");
        System.out.println(splaySet.contains(6));
        System.out.println(splaySet.toVisualizedString(""));

        splaySet.clear();
        splaySet.addWithoutSplaying(6);
        splaySet.addWithoutSplaying(7);
        splaySet.addWithoutSplaying(2);
        splaySet.addWithoutSplaying(4);
        splaySet.addWithoutSplaying(3);
        splaySet.addWithoutSplaying(5);
        splaySet.addWithoutSplaying(1);
        splaySet.splayValue(4);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove non-existing: 10");
        splaySet.topDownRemove(10);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove node with no children: 5");
        splaySet.topDownRemove(5);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove node with 2 children: 2");
        splaySet.topDownRemove(2);
        System.out.println(splaySet.toVisualizedString(""));

        System.out.println("Remove node with 1 child: 7");
        splaySet.topDownRemove(7);
        System.out.println(splaySet.toVisualizedString(""));
    }
}
