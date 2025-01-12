package codes.matheus.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public final class ReflectionUtils {
    private ReflectionUtils() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    public static @Nullable <T> T getFieldValue(@NotNull Object object, @NotNull String fieldName) {
        try {
            @Nullable Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing the field " + e);
        }
    }

    public static void setFieldValue(@NotNull Object object, @NotNull String fieldName, @Nullable Object value) {
        try {
            @Nullable Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Erro ao definir o campo: " + fieldName, e);
        }
    }
}
