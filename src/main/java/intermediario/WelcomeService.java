package intermediario;

import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Versão intermediária com validação de entrada
 * e uso de recursos modernos do Java (records e sealed interfaces).
 */
public class WelcomeService {
    // Constantes de configuração
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 120;

    private static final String WELCOME_MESSAGE = """
            *********************************
            Olá, informe o seu nome (mínimo %d caracteres):
            *********************************""".formatted(MIN_NAME_LENGTH);

    private static final String AGE_PROMPT = """
            *********************************
            Informe sua idade (%d a %d):
            *********************************""".formatted(MIN_AGE, MAX_AGE);

    private static final String GREETING_FORMAT = """
            *********************************
            Olá %s, sua idade é %d
            *********************************
            %n""";

    // Registro com validação no construtor
    private record UserData(String name, int age) {
        public UserData {
            if (!isValidName(name)) {
                throw new IllegalArgumentException("Nome inválido");
            }
            if (!isValidAge(age)) {
                throw new IllegalArgumentException("Idade inválida");
            }
        }
        private static boolean isValidName(String name) {
            return name != null && name.length() >= MIN_NAME_LENGTH;
        }
        private static boolean isValidAge(int age) {
            return age >= MIN_AGE && age <= MAX_AGE;
        }
    }

    // Interface selada para representar resultado da validação
    private sealed interface ValidationResult permits Success, Failure {
        default boolean isSuccess() {
            return this instanceof Success;
        }
    }
    private record Success() implements ValidationResult {}
    private record Failure(String message) implements ValidationResult {}

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            var userData = collectUserData(scanner);
            displayUserInfo(userData);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    // Coleta os dados com validação
    private static UserData collectUserData(Scanner scanner) {
        return new UserData(
            promptForValidInput(scanner, WELCOME_MESSAGE,
                input -> input.length() >= MIN_NAME_LENGTH,
                "Nome deve ter pelo menos %d caracteres".formatted(MIN_NAME_LENGTH)),
            Integer.parseInt(promptForValidInput(scanner, AGE_PROMPT,
                input -> {
                    try {
                        int age = Integer.parseInt(input);
                        return age >= MIN_AGE && age <= MAX_AGE;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "Idade deve ser entre %d e %d".formatted(MIN_AGE, MAX_AGE)))
        );
    }

    // Solicita e valida entrada do usuário
    private static String promptForValidInput(Scanner scanner, String prompt,
            Predicate<String> validator, String errorMessage) {
        while (true) {
            System.out.println(prompt);
            var input = scanner.nextLine();
            var result = validateInput(input, validator, errorMessage);
            switch (result) {
                case Success s -> { return input; }
                case Failure f -> System.err.println(f.message());
            }
        }
    }

    // Executa a validação
    private static ValidationResult validateInput(String input, Predicate<String> validator, String errorMessage) {
        return validator.test(input) ? new Success() : new Failure(errorMessage);
    }

    // Mostra resultado final
    private static void displayUserInfo(UserData userData) {
        System.out.printf(GREETING_FORMAT, userData.name(), userData.age());
    }
}
