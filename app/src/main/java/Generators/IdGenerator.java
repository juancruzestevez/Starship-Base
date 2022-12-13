package Generators;

import Entities.EntityType;

public class IdGenerator {
    public static int currentId = 0;

    public static String generateId(EntityType entityType) {
        return entityType+ "-" + String.valueOf(currentId++);
    }
}
