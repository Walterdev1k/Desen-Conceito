package avancado;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Versão avançada do sistema com:
 * - Separação de responsabilidades
 * - Configuração centralizada
 * - Handlers de entrada e saída
 * - Validações robustas
 * - Uso de Virtual Threads (Java 21)
 */
public class AdvancedWelcomeSystem {
    public static void main(String[] args) {
        try (var executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                var app = new WelcomeApplication(
                    new ConsoleInputHandler(),
                    new ConsoleOutputHandler(),
                    new UserDataValidator()
                );
                app.run();
            });
        }
    }
}

// Interface para ler entradas
@FunctionalInterface
interface InputHandler {
    String read(String prompt);
    default int readInt(String prompt) {
        return Integer.parseInt(read(prompt));
    }
}

// Interface para escrever saídas
@FunctionalInterface
interface OutputHandler {
    void write(String message);
    default void writeFormatted(String format, Object... args) {
        write(format.formatted(args));
    }
}

// Configuração geral do sistema
record AppConfig(
    int minNameLength,
    int minAge,
    int maxAge,
    String welcomeMessage,
    String agePrompt,
    String greetingFormat,
    String invalidInputMessage
) {}

// Estrutura para guardar dados do usuário
record UserData(String name, int age) {
    public UserData {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa");
        }
    }
}

// Classe principal da lógica
final class WelcomeApplication {
    private static final AppConfig CONFIG = new AppConfig(
        3, 1, 120,
        """
        *********************************
        Olá, informe o seu nome (mínimo %d caracteres):
        *********************************""",
        """
        *********************************
        Informe sua idade (%d a %d):
        *********************************""",
        """
        *********************************
        Olá %s, sua idade é %d
        *********************************
        """,
        "Entrada inválida. Por favor, tente novamente."
    );

    private final InputHandler input;
    private final OutputHandler output;
    private final UserDataValidator validator;

    public WelcomeApplication(InputHandler input, OutputHandler output,
                           UserDataValidator validator) {
        this.input = input;
        this.output = output;
        this.validator = validator;
    }

    public void run() {
        try {
            var userData = collectUserData();
            displayUserInfo(userData);
        } catch (Exception e) {
            output.write("Erro: " + e.getMessage());
        }
    }

    // Coleta dados com validação genérica
    private UserData collectUserData() {
        return new UserData(
            promptForValidInput(
                CONFIG.welcomeMessage().formatted(CONFIG.minNameLength()),
                input::read,
                validator::isValidName,
                CONFIG.invalidInputMessage()),
            promptForValidInput(
                CONFIG.agePrompt().formatted(CONFIG.minAge(), CONFIG.maxAge()),
                s -> input.readInt(s),
                validator::isValidAge,
                CONFIG.invalidInputMessage())
        );
    }

    // Método genérico de leitura e validação
    private <T> T promptForValidInput(
        String prompt,
        Function<String, T> inputParser,
        Predicate<T> validator,
        String errorMessage
    ) {
        while (true) {
            output.write(prompt);
            try {
                T value = inputParser.apply(prompt);
                if (validator.test(value)) {
                    return value;
                }
            } catch (Exception e) {
                // Ignora erros e repete
            }
            output.write(errorMessage);
        }
    }

    private void displayUserInfo(UserData userData) {
        output.writeFormatted(CONFIG.greetingFormat(), userData.name(), userData.age());
    }
}

// Implementação de leitura no console
final class ConsoleInputHandler implements InputHandler {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String read(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

// Implementação de escrita no console
final class ConsoleOutputHandler implements OutputHandler {
    @Override
    public void write(String message) {
        System.out.println(message);
    }
}

// Classe de validação dos dados
final class UserDataValidator {
    public boolean isValidName(String name) {
        return name != null && name.length() >= 3;
    }
    public boolean isValidAge(int age) {
        return age >= 1 && age <= 120;
    }
}
