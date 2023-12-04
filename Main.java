import controller.MemoryManager;

public class Main {
    public static void main(String[] args) {
        MemoryManager memoryManager = new MemoryManager(100);

        System.out.println(memoryManager.allocateMemory("P1", "var_x", 1000, false));
        System.out.println(memoryManager.allocateMemory("P1", "var_x", 10, false));
        System.out.println(memoryManager.allocateMemory("P2", "var_y", 25, true));

        System.out.println(memoryManager.freeMemory("P1", "var_x"));
        System.out.println(memoryManager.killProcess("P2"));

        System.out.println(memoryManager.allocateMemory("P1", "var_z", 10, true));
        System.out.println(memoryManager.allocateMemory("P4", "var_x", 5, true));
        System.out.println(memoryManager.allocateMemory("P1", "var_w", 5, true));

        System.out.println(memoryManager.freeMemory("P4", "var_x"));
        System.out.println(memoryManager.allocateMemory("P1", "var_y", 6, false));

        System.out.println(memoryManager.inspectProcess("P1"));
    }
}