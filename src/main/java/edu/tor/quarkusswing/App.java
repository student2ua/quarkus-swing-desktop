package edu.tor.quarkusswing;

import io.quarkus.runtime.Quarkus;

public class App {
    public static void main(String[] args) {
            // Блокирует основной поток до остановки приложения.
            // Swing-окна запустятся из @Observes StartupEvent.
            Quarkus.run(args);
    }
}
