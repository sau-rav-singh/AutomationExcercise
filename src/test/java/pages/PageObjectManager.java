package pages;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class PageObjectManager {
    private final WebDriver driver;
    private final Map<Class<?>, Object> pageCache = new HashMap<>();

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    @SuppressWarnings("unchecked")
    public <T> T getPage(Class<T> pageClass) {
        return (T) pageCache.computeIfAbsent(pageClass, clazz -> {
            if (!clazz.isAnnotationPresent(Page.class)) {
                throw new IllegalArgumentException("Class " + clazz.getName() + " is not annotated with @Page");
            }
            try {
                return clazz.getDeclaredConstructor(WebDriver.class).newInstance(driver);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
            }
        });
    }
}
