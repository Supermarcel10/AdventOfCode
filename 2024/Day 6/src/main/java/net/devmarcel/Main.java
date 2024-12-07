package net.devmarcel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {
    static List<String> lines = new ArrayList<>();
    static HashSet<Pos> obstructions = new HashSet<>();
    static Pos guard = null;
    static MovementDir mov = new MovementDir(0, -1);
    static int lineSize = 0;

    public static void main(String[] args) {
        try {
            lines = Files.readAllLines(Paths.get("C:\\Users\\Marcel\\PycharmProjects\\AoC2023\\2024\\Day 6\\input.txt"));
        } catch (IOException ignored) {}

        for (int y = 0; y < lines.size(); ++y) {
            String line = lines.get(y);

            int obsIndex = 0;
            while ((obsIndex = line.indexOf('#', obsIndex)) != -1) {
                obstructions.add(new Pos(obsIndex, y));
                ++obsIndex;
            }

            int guardIndex = line.indexOf('^');
            if (guardIndex != -1) {
                guard = new Pos(guardIndex, y);
            }

            lineSize = line.length();
        }

        if (guard == null) {
            throw new RuntimeException("Guard Pos not found!");
        }

//        part1();
        part2();
    }

    static void part1() {
        HashSet<Pos> visitedPos = new HashSet<>();

        cont_while:
        while (true) {
            visitedPos.add(guard.copy());
            mov.move(guard);

            for (Pos obs : obstructions) {
                if (obs.equals(guard)) {
                    mov.moveBack(guard);
                    mov.turnRight();
                    continue cont_while;
                }
            }

            if (guard.outOfBounds(lineSize - 1, lines.size() - 1)) break;
        }

        for (int i = 0; i < lines.size() + 2; i++) {
            for (int j = 0; j < lineSize + 2; j++) {
                Pos curPos = new Pos(j, i);

                boolean obstruction = false;
                for (Pos obs : obstructions) {
                    if (obs.equals(curPos)) {
                        obstruction = true;
                        break;
                    }
                }

                boolean traversed = false;
                for (Pos visited : visitedPos) {
                    if (visited.equals(curPos)) {
                        traversed = true;
                        break;
                    }
                }

                if (obstruction) System.out.print('#');
                else if (traversed) System.out.print('X');
                else System.out.print('.');
            }
            System.out.print('\n');
        }

        System.out.println(visitedPos.size());
    }

    static void part2() {
        HashSet<Pos> loopPositions = new HashSet<>();

        for (int y = 0; y < lines.size(); ++y) {
            for (int x = 0; x < lineSize; ++x) {
                Pos testPos = new Pos(x, y);

                if (obstructions.contains(testPos) || testPos.equals(guard)) {
                    continue;
                }

                HashSet<Pos> tempObstructions = new HashSet<>(obstructions);
                tempObstructions.add(testPos);

                if (causesLoop(guard.copy(), mov.copy(), tempObstructions, lineSize, lines.size())) {
                    loopPositions.add(testPos);
                }
            }
        }

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lineSize; x++) {
                Pos curPos = new Pos(x, y);

                if (obstructions.contains(curPos)) System.out.print('#');
                else if (curPos.equals(guard)) System.out.print('^');
                else if (loopPositions.contains(curPos)) System.out.print('O');
                else System.out.print('.');
            }
            System.out.println();
        }

        System.out.println("Number of positions causing loops: " + loopPositions.size());
    }

    static boolean causesLoop(Pos guard, MovementDir mov, HashSet<Pos> obstructions, int xMax, int yMax) {
        HashSet<String> visitedStates = new HashSet<>();
        int maxSteps = lines.size() * lineSize;
        int steps = 0;

        while (steps < maxSteps) {
            String state = guard.toString() + ";" + mov.toString();

            if (visitedStates.contains(state)) {
                return true;
            }

            visitedStates.add(state);
            mov.move(guard);

            if (guard.outOfBounds(xMax - 1, yMax - 1)) {
                return false;
            }

            if (obstructions.contains(guard)) {
                mov.moveBack(guard);
                mov.turnRight();
            }

            ++steps;
        }

        return false;
    }

    static class Pos {
        public int x;
        public int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pos copy() {
            return new Pos(x, y);
        }

        public boolean outOfBounds(int xMax, int yMax) {
            return x < 0 || y < 0 || x > xMax || y > yMax;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pos pos) {
                return this.x == pos.x && this.y == pos.y;
            } else return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }

    static class MovementDir {
        public int x;
        public int y;

        public MovementDir(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public MovementDir copy() {
            return new MovementDir(x, y);
        }

        public void turnRight() {
            int oldX = x;
            x = -y;
            y = oldX;
        }

        public void move(Pos guard) {
            guard.x += x;
            guard.y += y;
        }

        public void moveBack(Pos guard) {
            guard.x -= x;
            guard.y -= y;
        }

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }
}
